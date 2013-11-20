package com.gq2.DAO;

import com.gq2.beans.ClassificationBean;
import com.gq2.domain.Classification;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ClassificationDAO implements InjectableDAO {

    private static Logger log = LogManager.getLogger(ClassificationDAO.class.getName());
    private Connection conn;

    public ClassificationDAO() {
    }

    /**
     *
     * @param conn
     */
    @Override
    public void setConnection(Connection conn) {
	this.conn = conn;
    }

    public Classification load(int id) {
	try {
	    return load(conn, id);
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    protected Classification load(Connection conn, int id) throws SQLException {
	Classification classification = null;
	String query = "SELECT * FROM classifications WHERE cla_id = ?";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, id);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    classification = populateClassificationFromResultSet(rs);
		}
	    }
	}
	return classification;
    }

    /**
     * Adds the Classification to the Classification model
     */
    public int save(Classification classification) {
	try {
	    int identifierGenerated;
	    //** crear la frase INSERT SQL
	    String sql = "INSERT INTO classifications "
		    + " (cla_cha_id, cla_round, cla_date, cla_position, cla_tea_id, cla_points,"
		    + " cla_total_played_games, cla_total_won_games, cla_total_drawn_games,"
		    + " cla_total_lost_games, cla_total_own_goals, cla_total_against_goals,"
		    + " cla_home_played_games, cla_home_won_games, cla_home_drawn_games,"
		    + " cla_home_lost_games, cla_home_own_goals, cla_home_against_goals,"
		    + " cla_out_played_games, cla_out_won_games, cla_out_drawn_games,"
		    + " cla_out_lost_games, cla_out_own_goals, cla_out_against_goals,"
		    + " cla_rating) "
		    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?)";
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setInt(1, classification.getClaChaId());
		ps.setInt(2, classification.getClaRound());
		ps.setDate(3, new java.sql.Date(classification.getClaDate().getTime()));
		ps.setInt(4, classification.getClaPosition());
		ps.setInt(5, classification.getClaTeaId());
		ps.setInt(6, classification.getClaPoints());
		ps.setInt(7, classification.getClaTotalPlayedGames());
		ps.setInt(8, classification.getClaTotalWonGames());
		ps.setInt(9, classification.getClaTotalDrawnGames());
		ps.setInt(10, classification.getClaTotalLostGames());
		ps.setInt(11, classification.getClaTotalOwnGoals());
		ps.setInt(12, classification.getClaTotalAgainstGoals());
		ps.setInt(13, classification.getClaHomePlayedGames());
		ps.setInt(14, classification.getClaHomeWonGames());
		ps.setInt(15, classification.getClaHomeDrawnGames());
		ps.setInt(16, classification.getClaHomeLostGames());
		ps.setInt(17, classification.getClaHomeOwnGoals());
		ps.setInt(18, classification.getClaHomeAgainstGoals());
		ps.setInt(19, classification.getClaOutPlayedGames());
		ps.setInt(20, classification.getClaOutWonGames());
		ps.setInt(21, classification.getClaOutDrawnGames());
		ps.setInt(22, classification.getClaOutLostGames());
		ps.setInt(23, classification.getClaOutOwnGoals());
		ps.setInt(24, classification.getClaOutAgainstGoals());
		ps.setInt(25, classification.getClaRating());
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

    public int insertClassificationList(List<Classification> classificationList) {
	int insertedRows = 0;
	for (Classification classification : classificationList) {
	    if (save(classification) > 0) {
		insertedRows++;
	    }
	}
	return insertedRows;
    }

    /**
     * ------------------------------------------------------------- deletes the
     * Classification from the Classification model
     */
    public int delete(Classification rec) {
	try {
	    String sql = "DELETE FROM classifications WHERE cla_id = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, rec.getClaId());
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
    public int deleteRoundClassification(Integer chaId, Integer round) {
	try {
	    //** crear la frase DELETE SQL de tabla1
	    String sql = "DELETE FROM classifications WHERE cla_cha_id = ? AND cla_round = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, chaId);
	    ps.setInt(2, round);
	    log.debug("deleteRoundClassification: " + ps.toString());
	    return ps.executeUpdate();
	} catch (SQLException ex) {
	    java.util.logging.Logger.getLogger(ClassificationDAO.class.getName()).log(Level.SEVERE, null, ex);
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
    public boolean update(Classification classification) {
	try {
	    String sql = "UPDATE classifications SET "
		    + "cla_cha_id, cla_round,cla_date,cla_position,cla_tea_id,cla_points,"
		    + " cla_total_played_games,cla_total_won_games,cla_total_drawn_games = "
		    + ",cla_total_lost_games,cla_total_own_goals,cla_total_against_goals = "
		    + ",cla_home_played_games,cla_home_won_games,cla_home_drawn_games = "
		    + ",cla_cla_home_lost_games,cla_home_own_goals,cla_home_against_goals = "
		    + ",cla_out_played_games,cla_out_won_games,cla_out_drawn_games = "
		    + ",cla_out_lost_games,cla_out_own_goals,cla_out_against_goals = "
		    + ",cla_rating "
		    + " WHERE cla_id = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, classification.getClaChaId());
	    ps.setInt(2, classification.getClaRound());
	    ps.setDate(3, new java.sql.Date(classification.getClaDate().getTime()));
	    ps.setInt(4, classification.getClaPosition());
	    ps.setInt(5, classification.getClaTeaId());
	    ps.setInt(6, classification.getClaPoints());
	    ps.setInt(7, classification.getClaTotalPlayedGames());
	    ps.setInt(8, classification.getClaTotalWonGames());
	    ps.setInt(9, classification.getClaTotalDrawnGames());
	    ps.setInt(10, classification.getClaTotalLostGames());
	    ps.setInt(11, classification.getClaTotalOwnGoals());
	    ps.setInt(12, classification.getClaTotalAgainstGoals());
	    ps.setInt(13, classification.getClaHomePlayedGames());
	    ps.setInt(14, classification.getClaHomeWonGames());
	    ps.setInt(15, classification.getClaHomeDrawnGames());
	    ps.setInt(16, classification.getClaHomeLostGames());
	    ps.setInt(17, classification.getClaHomeOwnGoals());
	    ps.setInt(18, classification.getClaHomeAgainstGoals());
	    ps.setInt(19, classification.getClaOutPlayedGames());
	    ps.setInt(20, classification.getClaOutWonGames());
	    ps.setInt(21, classification.getClaOutDrawnGames());
	    ps.setInt(22, classification.getClaOutLostGames());
	    ps.setInt(23, classification.getClaOutOwnGoals());
	    ps.setInt(24, classification.getClaOutAgainstGoals());
	    ps.setInt(25, classification.getClaRating());
	    ps.setInt(26, classification.getClaId());
	    log.debug("updateClassification: " + ps.toString());
	    ps.executeUpdate();
	    return true;
	} catch (SQLException ex) {
	    java.util.logging.Logger.getLogger(ClassificationDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	return false;
    }

    public List<Classification> loadRoundClassificationList(Integer chaId, Integer round) {
	List<Classification> classificationList = new ArrayList<>();
	try {
	    String sql = "SELECT * "
		    + "FROM classifications WHERE cla_cha_id = ? AND cla_round = ? "
		    + "ORDER BY cla_position";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, chaId);
		ps.setInt(2, round);
		log.debug("loadRoundClassificationList: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			classificationList.add(populateClassificationFromResultSet(rs));
		    }
		}
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return classificationList;
    }

    public List<ClassificationBean> loadRoundClassificationBeanList(Integer chaId, Integer round) {
	List<ClassificationBean> classificationBeanList = new ArrayList<>();
	try {
	    String sql = "SELECT tea.tea_name, cla.* "
		    + "FROM classifications AS cla, teams AS tea "
		    + "WHERE cla_cha_id = ? AND cla_round = ? "
		    + "AND tea.tea_id = cla.cla_tea_id "
		    + "ORDER BY cla.cla_position";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, chaId);
		ps.setInt(2, round);
		log.debug("loadRoundClassificationBeanList: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			classificationBeanList.add(populateClassificationBeanFromResultSet(rs));
		    }
		}
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return classificationBeanList;
    }

    private Classification populateClassificationFromResultSet(ResultSet rs) {
	Classification cla = new Classification();
	try {
	    cla.setClaId(rs.getInt("cla_id"));
	    cla.setClaChaId(rs.getInt("cla_cha_id"));
	    cla.setClaRound(rs.getInt("cla_round"));
	    cla.setClaDate(rs.getDate("cla_date"));
	    cla.setClaPosition(rs.getInt("cla_position"));
	    cla.setClaTeaId(rs.getInt("cla_tea_id"));
	    cla.setClaPoints(rs.getInt("cla_points"));
	    cla.setClaTotalPlayedGames(rs.getInt("cla_total_played_games"));
	    cla.setClaTotalWonGames(rs.getInt("cla_total_won_games"));
	    cla.setClaTotalDrawnGames(rs.getInt("cla_total_drawn_games"));
	    cla.setClaTotalLostGames(rs.getInt("cla_total_lost_games"));
	    cla.setClaTotalOwnGoals(rs.getInt("cla_total_own_goals"));
	    cla.setClaTotalAgainstGoals(rs.getInt("cla_total_against_goals"));
	    cla.setClaHomePlayedGames(rs.getInt("cla_home_played_games"));
	    cla.setClaHomeWonGames(rs.getInt("cla_home_won_games"));
	    cla.setClaHomeDrawnGames(rs.getInt("cla_home_drawn_games"));
	    cla.setClaHomeLostGames(rs.getInt("cla_home_lost_games"));
	    cla.setClaHomeOwnGoals(rs.getInt("cla_home_own_goals"));
	    cla.setClaHomeAgainstGoals(rs.getInt("cla_home_against_goals"));
	    cla.setClaOutPlayedGames(rs.getInt("cla_out_played_games"));
	    cla.setClaOutWonGames(rs.getInt("cla_out_won_games"));
	    cla.setClaOutDrawnGames(rs.getInt("cla_out_drawn_games"));
	    cla.setClaOutLostGames(rs.getInt("cla_out_lost_games"));
	    cla.setClaOutOwnGoals(rs.getInt("cla_out_own_goals"));
	    cla.setClaOutAgainstGoals(rs.getInt("cla_out_against_goals"));
	    cla.setClaRating(rs.getInt("cla_rating"));
	} catch (SQLException ex) {
	    java.util.logging.Logger.getLogger(ClassificationDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	return cla;
    }

    private ClassificationBean populateClassificationBeanFromResultSet(ResultSet rs) {
	ClassificationBean claBean = new ClassificationBean();
	try {
	    claBean.setClaId(rs.getInt("cla_id"));
	    claBean.setClaChaId(rs.getInt("cla_cha_id"));
	    claBean.setClaRound(rs.getInt("cla_round"));
	    claBean.setClaDate(rs.getDate("cla_date"));
	    claBean.setClaPosition(rs.getInt("cla_position"));
	    claBean.setClaTeaId(rs.getInt("cla_tea_id"));
	    claBean.setClaPoints(rs.getInt("cla_points"));
	    claBean.setClaTotalPlayedGames(rs.getInt("cla_total_played_games"));
	    claBean.setClaTotalWonGames(rs.getInt("cla_total_won_games"));
	    claBean.setClaTotalDrawnGames(rs.getInt("cla_total_drawn_games"));
	    claBean.setClaTotalLostGames(rs.getInt("cla_total_lost_games"));
	    claBean.setClaTotalOwnGoals(rs.getInt("cla_total_own_goals"));
	    claBean.setClaTotalAgainstGoals(rs.getInt("cla_total_against_goals"));
	    claBean.setClaHomePlayedGames(rs.getInt("cla_home_played_games"));
	    claBean.setClaHomeWonGames(rs.getInt("cla_home_won_games"));
	    claBean.setClaHomeDrawnGames(rs.getInt("cla_home_drawn_games"));
	    claBean.setClaHomeLostGames(rs.getInt("cla_home_lost_games"));
	    claBean.setClaHomeOwnGoals(rs.getInt("cla_home_own_goals"));
	    claBean.setClaHomeAgainstGoals(rs.getInt("cla_home_against_goals"));
	    claBean.setClaOutPlayedGames(rs.getInt("cla_out_played_games"));
	    claBean.setClaOutWonGames(rs.getInt("cla_out_won_games"));
	    claBean.setClaOutDrawnGames(rs.getInt("cla_out_drawn_games"));
	    claBean.setClaOutLostGames(rs.getInt("cla_out_lost_games"));
	    claBean.setClaOutOwnGoals(rs.getInt("cla_out_own_goals"));
	    claBean.setClaOutAgainstGoals(rs.getInt("cla_out_against_goals"));
	    claBean.setClaRating(rs.getInt("cla_rating"));

	    claBean.setTeamName(rs.getString("tea_name"));
	} catch (SQLException ex) {
	    java.util.logging.Logger.getLogger(ClassificationDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	return claBean;
    }
}
