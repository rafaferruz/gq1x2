package com.gq2.DAO;

import com.gq2.domain.ClassificationWonDrawnLost;
import java.sql.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ClassificationWonDrawnLostDAO implements InjectableDAO {

    private static Logger log = LogManager.getLogger(ClassificationWonDrawnLostDAO.class.getName());
    private Connection conn;

    public ClassificationWonDrawnLostDAO() {
    }

    /**
     *
     * @param conn
     */
    @Override
    public void setConnection(Connection conn) {
	this.conn = conn;
    }

    public ClassificationWonDrawnLost load(int id) {
	try {
	    return load(conn, id);
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    protected ClassificationWonDrawnLost load(Connection conn, int id) throws SQLException {
	ClassificationWonDrawnLost classificationWDL = null;
	String query = "SELECT * FROM classificationsWonDrawnLost WHERE wdl_id = ?";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, id);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    classificationWDL = populateClassificationWDLFromResultSet(rs);
		}
	    }
	}
	return classificationWDL;
    }

    /**
     * ---------------------------------------------------------- Adds the
     * Classification to the Classification model
     */
    public int save(ClassificationWonDrawnLost classificationWDL) {
	try {
	    int identifierGenerated;

	    //** crear la frase INSERT SQL
	    String sql = "INSERT INTO classificationsWonDrawnLost "
		    + " (wdl_cha_id, wdl_round, wdl_date, wdl_position, wdl_tea_id,"
		    + " wdl_PSG, wdl_PSE, wdl_PSP,"
		    + " wdl_PSNG, wdl_PSNE, wdl_PSNP ) "
		    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setInt(1, classificationWDL.getWdlChaId());
		ps.setInt(2, classificationWDL.getWdlRound());
		ps.setDate(3, new java.sql.Date(classificationWDL.getWdlDate().getTime()));
		ps.setInt(4, classificationWDL.getWdlPosition());
		ps.setInt(5, classificationWDL.getWdlTeaId());
		ps.setInt(6, classificationWDL.getWdlPSG());
		ps.setInt(7, classificationWDL.getWdlPSE());
		ps.setInt(8, classificationWDL.getWdlPSP());
		ps.setInt(9, classificationWDL.getWdlPSNG());
		ps.setInt(10, classificationWDL.getWdlPSNE());
		ps.setInt(11, classificationWDL.getWdlPSNP());
		log.debug("save: " + ps.toString());
		ps.executeUpdate();
		try (ResultSet rs = ps.getGeneratedKeys()) {
		    rs.next();
		    identifierGenerated = rs.getInt(1);
		}
	    }
	    return identifierGenerated;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    public int insertClassificationList(List<ClassificationWonDrawnLost> classificationWDLList) {
	int insertedRows = 0;
	for (ClassificationWonDrawnLost classificationWDL : classificationWDLList) {
	    if (save(classificationWDL) > 0) {
		insertedRows++;
	    }
	}
	return insertedRows;
    }

    /**
     * ------------------------------------------------------------- deletes the
     * Classification from the Classification model
     */
    public int delete(ClassificationWonDrawnLost classificationWDL) {
	try {
	    String sql = "DELETE FROM classificationsWonDrawnLost WHERE wdl_id = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, classificationWDL.getWdlId());
		log.debug("delete: " + ps.toString());
		return ps.executeUpdate();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    /**
     * Elimina de la base de datos todos los registros de clasificacion que
     * pertenezcan a una ronda de un campeonato
     *
     * @param chaId	Id del campeonato
     * @param round	Id de la ronda a eliminar
     * @return	Numero de filas eliminadas
     */
    public int deleteRoundClassificationWDL(Integer chaId, Integer round) {
	try {
	    //** crear la frase DELETE SQL de tabla1
	    String sql = "DELETE FROM classificationsWonDrawnLost WHERE wdl_cha_id = ? AND wdl_round = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, chaId);
	    ps.setInt(2, round);
	    log.debug("deleteRoundClassificationWDL: " + ps.toString());
	    return ps.executeUpdate();
	} catch (SQLException ex) {
//	    log.getLogger(ClassificationWonDrawnLostDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	return 0;
    }

    /**
     * Updates the Classification in the Classification table
     *
     * @param classification	Objeto Classification a actualizar en la base de
     * datos
     * @return	boolean 'true' si consigue actualizar el objeto correctamente. De
     * otro modo, devuelve 'false'.
     */
    public boolean updateClassificationWDL(ClassificationWonDrawnLost classificationWDL) {
	try {
	    String sql = "UPDATE classificationsWonDrawnLost SET "
		    + " wdl_cha_id, wdl_round, wdl_date, wdl_position, wdl_tea_id,"
		    + " wdl_PSG, wdl_PSE, wdl_PSP,"
		    + " wdl_PSNG, wdl_PSNE, wdl_PSNP"
		    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?"
		    + " WHERE wdl_id = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, classificationWDL.getWdlChaId());
	    ps.setInt(2, classificationWDL.getWdlRound());
	    ps.setDate(3, new java.sql.Date(classificationWDL.getWdlDate().getTime()));
	    ps.setInt(4, classificationWDL.getWdlPosition());
	    ps.setInt(5, classificationWDL.getWdlTeaId());
	    ps.setInt(6, classificationWDL.getWdlPSG());
	    ps.setInt(7, classificationWDL.getWdlPSE());
	    ps.setInt(8, classificationWDL.getWdlPSP());
	    ps.setInt(9, classificationWDL.getWdlPSNG());
	    ps.setInt(10, classificationWDL.getWdlPSNE());
	    ps.setInt(11, classificationWDL.getWdlPSNP());
	    ps.setInt(12, classificationWDL.getWdlId());
	    log.debug("updateClassificationWDL: " + ps.toString());
	    ps.executeUpdate();
	    return true;
	} catch (SQLException ex) {
//	    java.util.logging.Logger.getLogger(ClassificationWonDrawnLostDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	return false;
    }

    public List<ClassificationWonDrawnLost> loadRoundClassificationWDLList(Integer chaId, Integer round) {
	List<ClassificationWonDrawnLost> classificationWDLList = new ArrayList<>();
	try {
	    String sql = "SELECT * "
		    + "FROM classificationsWonDrawnLost WHERE wdl_cha_id = ? AND wdl_round = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, chaId);
		ps.setInt(2, round);
		log.debug("loadRoundClassificationWDLList: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			classificationWDLList.add(populateClassificationWDLFromResultSet(rs));
		    }
		}
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return classificationWDLList;
    }

    public ClassificationWonDrawnLost populateClassificationWDLFromResultSet(ResultSet rs) {
	ClassificationWonDrawnLost classificationWDL = new ClassificationWonDrawnLost();
	try {
	    classificationWDL.setWdlId(rs.getInt("wdl_id"));
	    classificationWDL.setWdlChaId(rs.getInt("wdl_cha_id"));
	    classificationWDL.setWdlRound(rs.getInt("wdl_round"));
	    classificationWDL.setWdlDate(rs.getDate("wdl_date"));
	    classificationWDL.setWdlPosition(rs.getInt("wdl_position"));
	    classificationWDL.setWdlTeaId(rs.getInt("wdl_tea_id"));
	    classificationWDL.setWdlPSG(rs.getInt("wdl_PSG"));
	    classificationWDL.setWdlPSE(rs.getInt("wdl_PSE"));
	    classificationWDL.setWdlPSP(rs.getInt("wdl_PSP"));
	    classificationWDL.setWdlPSNG(rs.getInt("wdl_PSNG"));
	    classificationWDL.setWdlPSNE(rs.getInt("wdl_PSNE"));
	    classificationWDL.setWdlPSNP(rs.getInt("wdl_PSNP"));
	} catch (SQLException ex) {
	    log.error(ex);
//	    java.util.logging.Logger.getLogger(ClassificationWonDrawnLostDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	return classificationWDL;
    }
}
