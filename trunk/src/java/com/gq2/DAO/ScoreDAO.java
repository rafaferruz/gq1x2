package com.gq2.DAO;

import com.gq2.beans.ScoreBean;
import com.gq2.domain.Score;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DAO de los Scores (Resultados)
 *
 */
public class ScoreDAO implements InjectableDAO {

    transient private final Logger log = LogManager.getLogger(this.getClass());
    private Connection conn;

    /**
     *
     * @param conn
     */
    @Override
    public void setConnection(Connection conn) {
	this.conn = conn;
    }

    /**
     * Devuelve el Score seg�n el id
     *
     * @param id	Identifier
     *
     * @return Score si lo encuentra o null si no
     */
    public Score load(int id) {
	try {
	    return load(conn, id);
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    protected Score load(Connection conn, int id) throws SQLException {
	Score score = null;
	String query = "SELECT * FROM scores WHERE sco_id = ?";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, id);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    score = populateScoreFromResultSet(rs);
		}
	    }
	}
	return score;
    }

    /**
     * Guardar un nuevo Score (Equipo).
     *
     * @param score Equipo que se va a guardar.
     * @return id del equipo creado. 0 si no consigue salvarlo en la BD.
     */
    public int save(Score score) {
	try {
	    String sql = "INSERT INTO scores (sco_cha_id, sco_round, "
		    + "sco_date, sco_team1_id, sco_team2_id, sco_score1, sco_score2 ) "
		    + "VALUES (?,?,?,?,?,?,?)";
	    int identifierGenerated;
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setInt(1, score.getScoChaId());
		ps.setInt(2, score.getScoRound());
		ps.setDate(3, new java.sql.Date(score.getScoDate().getTime()));
		ps.setInt(4, score.getScoTeam1Id());
		ps.setInt(5, score.getScoTeam2Id());
		ps.setInt(6, score.getScoScore1());
		ps.setInt(7, score.getScoScore2());
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
     * Update score
     *
     * @param score Score to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean update(Score score) {
	try {
	    String sql = "UPDATE scores SET "
		    + "sco_cha_id = ?, sco_round = ?, "
		    + "sco_date = ?, sco_team1_id = ?, sco_team2_id = ?, "
		    + "sco_score1 = ?, sco_score2 = ? " + " WHERE sco_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, score.getScoChaId());
		ps.setInt(2, score.getScoRound());
		ps.setDate(3, (java.sql.Date) score.getScoDate());
		ps.setInt(4, score.getScoTeam1Id());
		ps.setInt(5, score.getScoTeam2Id());
		ps.setInt(6, score.getScoScore1());
		ps.setInt(7, score.getScoScore2());
		ps.setInt(8, score.getScoId());
		log.debug("update: " + ps.toString());
		ps.executeUpdate();
		return true;
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
    public void updateScoreList(List<ScoreBean> scoreList) {
	for (Score score:scoreList){
	    update(score);
	}
    }

    /**
     * Delete score
     *
     * @param score Score to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean delete(Score score) {
	try {
	    String sql = "DELETE FROM scores WHERE sco_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, score.getScoId());
		log.debug("delete: " + ps.toString());
		ps.executeUpdate();
	    }
	    return true;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    //TODO Pendiente de programacion los tres siguientes metodos    
    /**
     * ------------------------------------------------------------- Returns all
     * Results in the Result model
     */
    public void readAllResults() throws SQLException {
    }

    public void readAllResults(String sqlWhereClause) throws SQLException {
	Connection connection = null;
	//** crear la frase SELECT SQL
	String request = "SELECT * FROM RESULTADOS " + sqlWhereClause;

    }

    public Integer[] readRoundsOn(String sqlWhereClause) throws SQLException {
	Connection connection = null;
	//** crear la frase SELECT SQL
	String request = sqlWhereClause;
	Integer[] all = null;
	return all;
    }

    public List<Integer> loadChampionshipRounds(int id) {
	List<Integer> roundList = new ArrayList<>();
	String query = "SELECT DISTINCT(sco_round) as round FROM scores WHERE sco_cha_id = ? ORDER BY sco_round";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, id);
	    log.debug("loadChampionshipRounds: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		for (; rs.next() == true;) {
		    roundList.add(rs.getInt("round"));
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return roundList;
    }

    public List<ScoreBean> loadChampionshipRoundScores(int chaId, int round) {
	List<ScoreBean> scoreBeanList = new ArrayList<>();
	String query = "SELECT team1.tea_name AS name1, "
		+ " team2.tea_name AS name2, sco.* "
		+ " FROM scores AS sco, teams AS team1, teams AS team2 "
		+ " WHERE sco.sco_cha_id = ? "
		+ " AND sco.sco_round = ? "
		+ " AND team1.tea_id = sco.sco_team1_id "
		+ " AND team2.tea_id = sco.sco_team2_id "
		+ " ORDER BY sco.sco_id ";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, chaId);
	    ps.setInt(2, round);
	    log.debug("loadChampionshipRoundScores: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		while ( rs.next()) {
		    scoreBeanList.add(populateScoreBeanFromResultSet(rs));
		}
	    }
	} catch (SQLException ex) {
	    java.util.logging.Logger.getLogger(ScoreDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	return scoreBeanList;
    }

    /**
     * Actualiza la fecha de los resultados de una determinada ronda
     *
     * @param score Score to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean updateScoresDate(int chaId, int round, Date date) {
	try {
	    String sql = "UPDATE scores SET "
		    + " sco_date = ?"
		    + " WHERE sco_cha_id = ? AND sco_round = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setDate(1, new java.sql.Date(date.getTime()));
		ps.setInt(2, chaId);
		ps.setInt(3, round);
		log.debug("updateScoreDate: " + ps.toString());
		ps.executeUpdate();
		return true;
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    /**
     * Lee los datos del Score (Equipo) de un resultset
     *
     * @param rs resultSet
     * @return campeonato le�do
     */
    protected Score populateScoreFromResultSet(ResultSet rs) throws SQLException {
	Score score = new Score();
	score.setScoId(rs.getInt("sco_id"));
	score.setScoChaId(rs.getInt("sco_cha_id"));
	score.setScoRound(rs.getInt("sco_round"));
	score.setScoDate(rs.getDate("sco_date"));
	score.setScoTeam1Id(rs.getInt("sco_team1_id"));
	score.setScoTeam2Id(rs.getInt("sco_team2_id"));
	score.setScoScore1(rs.getInt("sco_score1"));
	score.setScoScore2(rs.getInt("sco_score2"));
	return score;
    }

    /**
     * Lee los datos del ScoreBean de un resultset de una consulta
     *
     * @param rs resultSet
     * @return objeto ScoreBean le�do
     */
    protected ScoreBean populateScoreBeanFromResultSet(ResultSet rs) throws SQLException {
	ScoreBean scoreBean = new ScoreBean();
	scoreBean.setScoId(rs.getInt("sco_id"));
	scoreBean.setScoChaId(rs.getInt("sco_cha_id"));
	scoreBean.setScoRound(rs.getInt("sco_round"));
	scoreBean.setScoDate(rs.getDate("sco_date"));
	scoreBean.setScoTeam1Id(rs.getInt("sco_team1_id"));
	scoreBean.setScoTeam2Id(rs.getInt("sco_team2_id"));
	scoreBean.setScoScore1(rs.getInt("sco_score1"));
	scoreBean.setScoScore2(rs.getInt("sco_score2"));
	scoreBean.setScoTeamName1(rs.getString("name1"));
	scoreBean.setScoTeamName2(rs.getString("name2"));
	return scoreBean;
    }

}
