/*
 * Rating4DAO.java
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.gq2.DAO;

import com.gq2.domain.Rating4;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class Rating4DAO implements InjectableDAO {

    transient private final Logger log = LogManager.getLogger(this.getClass());
    private Connection conn;

    /**
     * Creates a new instance of Rating4DAO
     *
     */
    public Rating4DAO() {
    }

    /**
     *
     * @param conn
     */
    @Override
    public void setConnection(Connection conn) {
	this.conn = conn;
    }

    public int save(Rating4 rating4) {
	try {
	    String sql;
	    sql = "INSERT INTO ratings4 "
		    + "(rat4_id, rat4_cha_id, rat4_round, rat4_date, "
		    + "rat4_team1_id ,rat4_team1_previous, rat4_team2_id, rat4_team2_previous, "
		    + "rat4_previous_diference, rat4_probability_win, rat4_probability_draw, "
		    + "rat4_probability_lose, rat4_score_sign, rat4_team1_post, rat4_team2_post) "
		    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    int identifierGenerated;
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setInt(1, rating4.getRat4Id());
		ps.setInt(2, rating4.getRat4ChaId());
		ps.setInt(3, rating4.getRat4Round());
		ps.setDate(4, new java.sql.Date(rating4.getRat4Date().getTime()));
		ps.setInt(5, rating4.getRat4Team1Id());
		ps.setInt(6, rating4.getRat4Team1Previous());
		ps.setInt(7, rating4.getRat4Team2Id());
		ps.setInt(8,rating4.getRat4Team2Previous());
		ps.setInt(9,rating4.getRat4PreviousDiference());
		ps.setInt(10,rating4.getRat4ProbabilityWin());
		ps.setInt(11,rating4.getRat4ProbabilityDraw());
		ps.setInt(12,rating4.getRat4ProbabilityLose());
		ps.setString(13,rating4.getRat4ScoreSign());
		ps.setInt(14,rating4.getRat4Team1Post());
		ps.setInt(15,rating4.getRat4Team2Post());
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

    /**
     * -------------------------------------------------------------------
     *
     * @param chaId Id del championship del que se desean eliminar los registros
     * de rat4
     * @param round Id de la round de la que se desean eliminar los registros de
     * rat4
     *
     * @return El n�mero de filas eliminadas
     */
    public Integer deleteRating4Round(Integer chaId, Integer round) {
	try {
	    String sql = "DELETE FROM ratings4 WHERE rat4_cha_id = ? AND rat4_round = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, chaId);
		ps.setInt(2, round);
		log.debug("deleteRating4Round: " + ps.toString());
		return ps.executeUpdate();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    public List<Rating4> readLatestBeforeDateRating4Team(Integer teamId, Date date, int previousRoundsNumber) {
	List<Rating4> rating4List = new ArrayList<>();
	try {
	    String sql = "SELECT * FROM ratings4  WHERE "
		    + " rat4_team1_id = ? "
		    + " AND rat4_date < ? "
		    + " ORDER BY rat4_date DESC, rat4_id DESC "
		    + " LIMIT ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, teamId);
		ps.setDate(2, new java.sql.Date(date.getTime()));
		ps.setInt(3, previousRoundsNumber);
		log.debug("read4LastRating4Team: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			rating4List.add(populateRating4FromResultSet(rs));
		    }
		}
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return rating4List;

    }

    public List<Rating4> loadAllRating4Round(Integer chaId, Integer round) {
	List<Rating4> rating4List = new ArrayList<>();
	try {
	    String sql = "SELECT * "
		    + "FROM ratings4 WHERE rat4_cha_id = ? AND rat4_round = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, chaId);
		ps.setInt(2, round);
		log.debug("loadAllRating4Round: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			rating4List.add(populateRating4FromResultSet(rs));
		    }
		}
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return rating4List;
    }

    public int insertRating4CurrentRoundList(List<Rating4> rating4CurrentRoundList) {
	int rowsCounter = 0;
	for (Rating4 rating4 : rating4CurrentRoundList) {
	    if (save(rating4)>0) {
		rowsCounter++;
	    }
	}
	return rowsCounter;
    }

    public Rating4 populateRating4FromResultSet(ResultSet rs) {
	Rating4 rating4 = new Rating4();
	try {
	    rating4.setRat4Id(rs.getInt("rat4_id"));
	    rating4.setRat4ChaId(rs.getInt("rat4_cha_id"));
	    rating4.setRat4Round(rs.getInt("rat4_round"));
	    rating4.setRat4Date(rs.getDate("rat4_date"));
	    rating4.setRat4Team1Id(rs.getInt("rat4_team1_id"));
	    rating4.setRat4Team1Previous(rs.getInt("rat4_team1_previous"));
	    rating4.setRat4Team2Id(rs.getInt("rat4_team2_id"));
	    rating4.setRat4Team2Previous(rs.getInt("rat4_team2_previous"));
	    rating4.setRat4PreviousDiference(rs.getInt("rat4_previous_diference"));
	    rating4.setRat4ProbabilityWin(rs.getInt("rat4_probability_win"));
	    rating4.setRat4ProbabilityDraw(rs.getInt("rat4_probability_draw"));
	    rating4.setRat4ProbabilityLose(rs.getInt("rat4_probability_lose"));
	    rating4.setRat4ScoreSign(rs.getString("rat4_score_sign"));
	    rating4.setRat4Team1Post(rs.getInt("rat4_team1_post"));
	    rating4.setRat4Team2Post(rs.getInt("rat4_team2_post"));
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return rating4;
    }
}
