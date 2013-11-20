package com.gq2.DAO;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DB {

    private static Logger log = LogManager.getLogger(DB.class.getName());

    /**
     * Proporciona una conexion a la base de datos de la pool de conexiones. La
     * conexion DEBE SER devuelta con putConnection(conn).
     */
    static Connection getConnection() {
	try {
	    Context initContext = new InitialContext();
	    Context envContext = (Context) initContext.lookup("java:/comp/env");
	    DataSource ds = (DataSource) envContext.lookup("jdbc/gq1x2");
	    Connection conn = ds.getConnection();
	    return conn;
	} catch (NamingException ex) {
	    log.fatal("Problema con fuente de datos " + ex.toString());
	    throw new RuntimeException("Error de conexión a la base de datos.", ex);
	} catch (SQLException ex) {
	    log.fatal("No podemos obtener conexiones " + ex.toString());
	    throw new RuntimeException("Error de conexión a la base de datos.", ex);
	}
    }

    /**
     * Devuelve una conexión a pool de conexiones.
     */
    static void putConnection(Connection conn) {
	try {
	    if (conn != null) {
		conn.close();
	    }
	} catch (SQLException ex) {
	    log.error("Error cerrando conexion " + ex.toString());
	    throw new RuntimeException(ex);
	}
    }
}
