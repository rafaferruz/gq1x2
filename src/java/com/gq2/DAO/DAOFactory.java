package com.gq2.DAO;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Factoría para manejar los DAO de manera transaccional.
 *
 * Ejemplo de uso no transaccional (sin reutilizar la conexión entre varias
 * DAO):
 *
 * new DAOFactory().getFiscalPeriodDAO().update(fiscalPeriod, userId);
 *
 * Ejemplo de uso transaccional (reutilizando la misma conexión entre las DAO):
 *
 * DAOFactory df = new DAOFactory(); df.startTransaction(); try {
 * df.getFiscalPeriodDAO().update(fiscalPeriod, userId);
 * df.getUserDAO().save(user, userId); df.commit(); } catch (Throwable ex) {
 * df.rollback(ex); }
 */
public class DAOFactory {

    private static Logger log = LogManager.getLogger("DAOFactory");
    Connection conn;

    /**
     * Inicia una transacción.
     */
    public void startTransaction() {
	this.conn = DB.getConnection();
    }

    /**
     * Termina una transacción haciendo commit.
     */
    public void commit() {
	try {
	    conn.commit();
	} catch (SQLException ex) {
	    try {
		if (conn != null) {
		    log.error("Ejecutando rollback.");
		    conn.rollback();
		}
	    } catch (SQLException sex) {
		log.error("Error SQL: Imposible deshacer. " + sex.toString());
	    }
	    log.error("Error de acceso a datos en TransactionalDAOFactory");
	    throw new RuntimeException(ex.getCause() != null ? ex.getCause() : ex);
	} finally {
	    DB.putConnection(conn);
	}
    }

    /**
     * Termina una transacción haciendo rollback.
     */
    public void rollback(Throwable ex) {
	try {
	    if (conn != null) {
		log.error("Ejecutando rollback.");
		conn.rollback();
	    }
	} catch (SQLException sex) {
	    log.error("Error SQL: Imposible deshacer. " + sex.toString());
	} finally {
	    DB.putConnection(conn);
	}
	throw new RuntimeException(ex.getCause() != null ? ex.getCause() : ex);
    }

    private Object getDAO(Class clazz) {
	InjectableDAO dao;
	try {
	    Constructor constructor = clazz.getConstructor();
	    dao = (InjectableDAO) constructor.newInstance();
	} catch (InstantiationException ex) {
	    throw new RuntimeException(ex); // Esto no debería ocurrir nunca
	} catch (IllegalAccessException ex) {
	    throw new RuntimeException(ex); // Esto no debería ocurrir nunca
	} catch (IllegalArgumentException ex) {
	    throw new RuntimeException(ex); // Esto no debería ocurrir nunca
	} catch (InvocationTargetException ex) {
	    throw new RuntimeException(ex); // Esto no debería ocurrir nunca
	} catch (NoSuchMethodException ex) {
	    throw new RuntimeException(ex); // Esto no debería ocurrir nunca
	} catch (SecurityException ex) {
	    throw new RuntimeException(ex); // Esto no debería ocurrir nunca
	}
	if (conn != null) {
	    // Si estamos en modo transaccional (reutilizamos la conexión entre todas las DAO)
	    dao.setConnection(conn);
	    return dao;
	} else {
	    // Si estamos en modo no transaccional
	    return DAOProxy.newInstance(dao);
	}
    }

    public ChampionshipDAO getChampionshipDAO() {
	return (ChampionshipDAO) this.getDAO(ChampionshipDAO.class);
    }
    public TeamDAO getTeamDAO() {
	return (TeamDAO) this.getDAO(TeamDAO.class);
    }
    public EnrolledTeamDAO getEnrolledTeamDAO() {
	return (EnrolledTeamDAO) this.getDAO(EnrolledTeamDAO.class);
    }
    public ScoreDAO getScoreDAO() {
	return (ScoreDAO) this.getDAO(ScoreDAO.class);
    }
    public ClassificationDAO getClassificationDAO() {
	return (ClassificationDAO) this.getDAO(ClassificationDAO.class);
    }
    public ClassificationWonDrawnLostDAO getClassificationWonDrawnLostDAO() {
	return (ClassificationWonDrawnLostDAO) this.getDAO(ClassificationWonDrawnLostDAO.class);
    }
    public Rating4DAO getRating4DAO() {
	return (Rating4DAO) this.getDAO(Rating4DAO.class);
    }
    public SuperTableDAO getSuperTableDAO() {
	return (SuperTableDAO) this.getDAO(SuperTableDAO.class);
    }
    public PrognosticDAO getPrognosticDAO() {
	return (PrognosticDAO) this.getDAO(PrognosticDAO.class);
    }
    public PrePoolDAO getPrePoolDAO() {
	return (PrePoolDAO) this.getDAO(PrePoolDAO.class);
    }
    public BetDAO getBetDAO() {
	return (BetDAO) this.getDAO(BetDAO.class);
    }
    public SetupDAO getSetupDAO() {
	return (SetupDAO) this.getDAO(SetupDAO.class);
    }
}
