package com.gq2.DAO;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Enhancer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Proxy que se usa para injectar una conexión en una DAO, gestionando la
 * obtención y devolución a la pool de conexiones, para evitar tener el mismo
 * código en cada DAO. Nota de implementación: usamos el Proxy de CGLib en vez
 * del de java.lang.reflect porque permite hacer proxies de clases en vez de
 * interfaces.
 */
public class DAOProxy implements InvocationHandler {

    private static Logger log = LogManager.getLogger("DAOProxy");
    private InjectableDAO dao;

    public static Object newInstance(InjectableDAO dao) {
	Enhancer enhancer = new Enhancer();
	enhancer.setClassLoader(Thread.currentThread().getContextClassLoader());
	enhancer.setSuperclass(dao.getClass());
	enhancer.setCallback(new DAOProxy(dao));
	return enhancer.create();
    }

    private DAOProxy(InjectableDAO dao) {
	this.dao = dao;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
	Object result = null;
	Connection conn = null;
	try {
	    int modifiers = method.getModifiers();
	    if (Modifier.isStatic(modifiers) || !Modifier.isPublic(modifiers)) {
		// No inyectamos la conexión a los métodos no públicos o estáticos.
		// Así evitamos un error cuando la JVM invoca a finalize().
		result = method.invoke(this.dao, args);
	    } else {
		conn = DB.getConnection();
		this.dao.setConnection(conn);
		result = method.invoke(this.dao, args);
		conn.commit();
	    }
	} catch (IllegalAccessException ex) {
	    throw new RuntimeException(ex); // Esto no debería ocurrir nunca
	} catch (IllegalArgumentException ex) {
	    throw new RuntimeException(ex); // Esto no debería ocurrir nunca
	} catch (SQLException ex) {
	    // SQLException lanzada en conn.commit()
	    throw new RuntimeException(ex);
	} catch (Throwable ex) {
	    // Aquí están incluidas las SQLException o UserException que pueda lanzar la DAO
	    try {
		if (conn != null) {
		    conn.rollback();
		}
	    } catch (SQLException sex) {
		log.error("Error SQL: Imposible deshacer. " + sex.toString());
	    }
/*	    if (ex instanceof UserException) {
		log.info("Error de acceso a datos en " + dao.getClass().getName() + "." + method.getName() + "()");
	    } else {
		log.error("Error de acceso a datos en " + dao.getClass().getName() + "." + method.getName() + "()");
	    }  */
	    throw ex.getCause();
	} finally {
	    // Devolvemos la conexión a la pool
	    DB.putConnection(conn);
	}
	return result;
    }
}
