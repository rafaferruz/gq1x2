package com.gq2.DAO;

import com.gq2.domain.PrePool;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class PrePoolDAO implements InjectableDAO {

    private static Logger log = LogManager.getLogger(PrePoolDAO.class.getName());
    private Connection conn;

    public PrePoolDAO() {
    }

    /**
     *
     * @param conn
     */
    @Override
    public void setConnection(Connection conn) {
	this.conn = conn;
    }

    public PrePool load(int id) {
	try {
	    return load(conn, id);
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    protected PrePool load(Connection conn, int id) throws SQLException {
	PrePool prePool = null;
	String query = "SELECT * FROM prePools WHERE pre_id = ?";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, id);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    prePool = populatePrePoolFromResultSet(rs);
		}
	    }
	}
	return prePool;
    }

    /**
     * Adds the PrePool to the PrePool model
     */
    public int save(PrePool prePool) {
	try {
	    int identifierGenerated;
	    //** crear la frase INSERT SQL
	    String sql = "INSERT INTO prePools "
		    + " (pre_season, pre_order_number, pre_cha_id, pre_round, "
		    + "pre_date, pre_sco_id, pre_sco_team1_id, pre_tea_name1, pre_sco_team2_id, "
		    + "pre_tea_name2, pre_sco_score1, pre_sco_score2, pre_rat_points, pre_rat4_previous_diference, "
		    + "pre_percent_win, pre_percent_draw, pre_percent_lose, pre_prognostic, "
		    + "pre_rat4_score_sign, pre_failed_prognostic, pre_id) "
		    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setInt(1, prePool.getPreSeason());
		ps.setInt(2, prePool.getPreOrderNumber());
		ps.setInt(3, prePool.getPreChaId());
		ps.setInt(4, prePool.getPreRound());
		ps.setDate(5, new java.sql.Date(prePool.getPreDate().getTime()));
		ps.setInt(6, prePool.getPreScoId());
		ps.setInt(7, prePool.getPreScoTeam1Id());
		ps.setString(8, prePool.getPreTeaName1());
		ps.setInt(9, prePool.getPreScoTeam2Id());
		ps.setString(10, prePool.getPreTeaName2());
		ps.setInt(11, prePool.getPreScoScore1());
		ps.setInt(12, prePool.getPreScoScore2());
		ps.setInt(13, prePool.getPreRatPoints());
		ps.setInt(14, prePool.getPreRat4PreviousDiference());
		ps.setInt(15, prePool.getPrePercentWin());
		ps.setInt(16, prePool.getPrePercentDraw());
		ps.setInt(17, prePool.getPrePercentLose());
		ps.setString(18, prePool.getPrePrognostic());
		ps.setString(19, prePool.getPreRat4ScoreSign());
		ps.setInt(20, prePool.getPreFailedPrognostic());
		ps.setInt(21, prePool.getPreId());
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

    public int insertPrePoolList(List<PrePool> prePoolList) {
	int insertedRows = 0;
	for (PrePool prePool : prePoolList) {
	    if (save(prePool) > 0) {
		insertedRows++;
	    }
	}
	return insertedRows;
    }

    /**
     * ------------------------------------------------------------- deletes the
     * PrePool from the PrePool model
     */
    public int delete(PrePool rec) {
	try {
	    String sql = "DELETE FROM prePools WHERE pre_id = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, rec.getPreId());
		log.debug("delete: " + ps.toString());
		return ps.executeUpdate();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    /**
     * Elimina de la base de datos todos los registros de PrePool que
     * pertenezcan a una ronda de un campeonato
     *
     * @param chaId	Id del campeonato
     * @param round	Id de la ronda a eliminar
     * @return	Numero de filas eliminadas
     */
    public int deleteRoundPrePool(int chaId, int round) {
	try {
	    //** crear la frase DELETE SQL de tabla1
	    String sql = "DELETE FROM prePools WHERE pre_cha_id = ? AND pre_round = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, chaId);
	    ps.setInt(2, round);
	    log.debug("deleteRoundPrePool: " + ps.toString());
	    return ps.executeUpdate();
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return 0;
    }

    /**
     * Updates the PrePool in the PrePool table
     *
     * @param prePool	Objeto PrePool a actualizar en la base de datos
     * @return	boolean 'true' si consigue actualizar el objeto correctamente. De
     * otro modo, devuelve 'false'.
     */
    public boolean update(PrePool prePool) {
	try {
	    String sql = "UPDATE prePools SET "
		    + "(pre_season, pre_order_number, pre_cha_id, pre_round, "
		    + "pre_date, pre_sco_id, pre_sco_team1_id, pre_tea_name1, pre_sco_team2_id, "
		    + "pre_tea_name2, pre_sco_score1, pre_sco_score2, pre_rat_points, pre_rat4_previous_diference, "
		    + "pre_percent_win, pre_percent_draw, pre_percent_lose, pre_prognostic, pre_rat4_score_sign, pre_failed_prognostic ) "
		    + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ? )"
		    + " WHERE pre_id = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, prePool.getPreSeason());
	    ps.setInt(2, prePool.getPreOrderNumber());
	    ps.setInt(3, prePool.getPreChaId());
	    ps.setInt(4, prePool.getPreRound());
	    ps.setDate(5, new java.sql.Date(prePool.getPreDate().getTime()));
	    ps.setInt(6, prePool.getPreScoId());
	    ps.setInt(7, prePool.getPreScoTeam1Id());
	    ps.setString(8, prePool.getPreTeaName1());
	    ps.setInt(9, prePool.getPreScoTeam2Id());
	    ps.setString(10, prePool.getPreTeaName2());
	    ps.setInt(11, prePool.getPreScoScore1());
	    ps.setInt(12, prePool.getPreScoScore2());
	    ps.setInt(13, prePool.getPreRatPoints());
	    ps.setInt(14, prePool.getPreRat4PreviousDiference());
	    ps.setInt(15, prePool.getPrePercentWin());
	    ps.setInt(16, prePool.getPrePercentDraw());
	    ps.setInt(17, prePool.getPrePercentLose());
	    ps.setString(18, prePool.getPrePrognostic());
	    ps.setString(19, prePool.getPreRat4ScoreSign());
	    ps.setInt(20, prePool.getPreFailedPrognostic());
	    ps.setInt(21, prePool.getPreId());
	    log.debug("updatePrePool: " + ps.toString());
	    ps.executeUpdate();
	    return true;
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return false;
    }

    public List<PrePool> loadRoundPrePoolList(Integer chaId, Integer round) {
	List<PrePool> prePoolList = new ArrayList<>();
	try {
	    String sql = "SELECT * "
		    + "FROM prePools WHERE pre_cha_id = ? AND pre_round = ? "
		    + "ORDER BY pre_order_number";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, chaId);
		ps.setInt(2, round);
		log.debug("loadRoundPrePoolList: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			prePoolList.add(populatePrePoolFromResultSet(rs));
		    }
		}
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return prePoolList;
    }

    public List<PrePool> loadSeasonPrePoolListOrderByRating(int season, int order_number) {
	List<PrePool> prePoolList = new ArrayList<>();
	try {
	    String sql = "SELECT * "
		    + " FROM prePools "
		    + " WHERE pre_season = ? "
		    + " AND pre_order_number = ? "
		    + " ORDER BY pre_rat4_previous_diference, pre_id";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, season);
		ps.setInt(2, order_number);
		log.debug("loadSeasonPrePoolListOrderByRating: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			prePoolList.add(populatePrePoolFromResultSet(rs));
		    }
		}
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return prePoolList;
    }

    public List<PrePool> loadSeasonPrePoolListOrderById(int season, int order_number) {
	List<PrePool> prePoolList = new ArrayList<>();
	try {
	    String sql = "SELECT * "
		    + " FROM prePools "
		    + " WHERE pre_season = ? "
		    + " AND pre_order_number = ? "
		    + " ORDER BY pre_id";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, season);
		ps.setInt(2, order_number);
		log.debug("loadSeasonPrePoolListOrderById: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			prePoolList.add(populatePrePoolFromResultSet(rs));
		    }
		}
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return prePoolList;
    }

    public int deleteMatch(PrePool prePool) {
	try {
	    String sql = "DELETE FROM prePools "
		    + "WHERE pre_cha_id = ? AND pre_round = ? "
		    + " AND pre_sco_team1_id = ? AND  pre_sco_team2_id = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, prePool.getPreChaId());
		ps.setInt(2, prePool.getPreRound());
		ps.setInt(3, prePool.getPreScoTeam1Id());
		ps.setInt(4, prePool.getPreScoTeam2Id());
		log.debug("deleteMatch: " + ps.toString());
		return ps.executeUpdate();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

    }

    /*
     public List<PrePoolBean> loadRoundPrePoolBeanList(Integer chaId, Integer round) {
     List<PrePoolBean> prePoolBeanList = new ArrayList<>();
     try {
     String sql = "SELECT tea.tea_name, cla.* "
     + "FROM prePools AS cla, teams AS tea "
     + "WHERE cla_cha_id = ? AND cla_round = ? "
     + "AND tea.tea_id = cla.cla_tea_id "
     + "ORDER BY cla.cla_position";
     try (PreparedStatement ps = conn.prepareStatement(sql)) {
     ps.setInt(1, chaId);
     ps.setInt(2, round);
     log.debug("loadRoundPrePoolBeanList: " + ps.toString());
     try (ResultSet rs = ps.executeQuery()) {
     while (rs.next()) {
     prePoolBeanList.add(populatePrePoolBeanFromResultSet(rs));
     }
     }
     }
     } catch (SQLException ex) {
     throw new RuntimeException(ex);
     }

     return prePoolBeanList;
     }
     */
    private PrePool populatePrePoolFromResultSet(ResultSet rs) {
	PrePool pre = new PrePool();
	try {
	    pre.setPreId(rs.getInt("pre_id"));
	    pre.setPreSeason(rs.getInt("pre_season"));
	    pre.setPreOrderNumber(rs.getInt("pre_order_number"));
	    pre.setPreChaId(rs.getInt("pre_cha_id"));
	    pre.setPreRound(rs.getInt("pre_round"));
	    pre.setPreDate(rs.getDate("pre_date"));
	    pre.setPreScoId(rs.getInt("pre_sco_id"));
	    pre.setPreScoTeam1Id(rs.getInt("pre_sco_team1_id"));
	    pre.setPreTeaName1(rs.getString("pre_tea_name1"));
	    pre.setPreScoTeam2Id(rs.getInt("pre_sco_team2_id"));
	    pre.setPreTeaName2(rs.getString("pre_tea_name2"));
	    pre.setPreScoScore1(rs.getInt("pre_sco_score1"));
	    pre.setPreScoScore2(rs.getInt("pre_sco_score2"));
	    pre.setPreRatPoints(rs.getInt("pre_rat_points"));
	    pre.setPreRat4PreviousDiference(rs.getInt("pre_rat4_previous_diference"));
	    pre.setPrePercentWin(rs.getInt("pre_percent_win"));
	    pre.setPrePercentDraw(rs.getInt("pre_percent_draw"));
	    pre.setPrePercentLose(rs.getInt("pre_percent_lose"));
	    pre.setPrePrognostic(rs.getString("pre_prognostic"));
	    pre.setPreRat4ScoreSign(rs.getString("pre_rat4_score_sign"));
	    pre.setPreFailedPrognostic(rs.getInt("pre_failed_prognostic"));

	} catch (SQLException ex) {
	    log.error(ex);
	}
	return pre;
    }
    /*
     private PrePoolBean populatePrePoolBeanFromResultSet(ResultSet rs) {
     PrePoolBean preBean = new PrePoolBean();
     try {
     preBean.setPreId(rs.getInt("pre_id"));
     preBean.setPreChaId(rs.getInt("pre_cha_id"));
     preBean.setPreRound(rs.getInt("pre_round"));
     preBean.setPreDate(rs.getDate("pre_date"));
     preBean.setPrePosition(rs.getInt("pre_position"));
     preBean.setPreTeaId(rs.getInt("pre_tea_id"));
     preBean.setPrePoints(rs.getInt("pre_points"));
     claBean.setPreRating(rs.getInt("cla_rating"));

     claBean.setTeamName(rs.getString("tea_name"));
     } catch (SQLException ex) {
     java.util.logging.Logger.getLogger(PrePoolDAO.class.getName()).log(Level.SEVERE, null, ex);
     }
     return claBean;
     }
     */

    public int deleteSeasons(int first_season, int last_season) {
	try {
	    String sql = "DELETE FROM prePools "
		    + "WHERE pre_season >= ? AND pre_season <= ? ";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, first_season);
		ps.setInt(2, last_season);
		log.debug("deleteSeasons: " + ps.toString());
		return ps.executeUpdate();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    public List<Integer> loadPrePoolOrderNumberList(Integer season) {
	List<Integer> preOrderNumberList = new ArrayList<>();
	try {
	    String sql = "SELECT DISTINCT pre_order_number "
		    + " FROM prePools "
		    + " WHERE pre_season = ? "
		    + " ORDER BY pre_order_number";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, season);
	    log.debug("loadPrePoolOrderNumberList: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		while (rs.next()) {
		    preOrderNumberList.add(rs.getInt("pre_order_number"));
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return preOrderNumberList;
    }

    public List<PrePool> loadNlastPreTool(Integer season, Integer orderNumber, Integer orderNumber4) {
	    List<PrePool> preOrderNumberList = new ArrayList<>();
	    try {
		String sql = "SELECT * FROM prePools "
			+ " WHERE pre_season = ? "
		    + " AND pre_order_number <= ? "
		    + " AND pre_order_number >= ? "
			+ " ORDER BY pre_order_number DESC, pre_id";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, season);
		ps.setInt(2, orderNumber);
		ps.setInt(3, orderNumber4);
		log.debug("loadNlastPreTool: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			preOrderNumberList.add(populatePrePoolFromResultSet(rs));
		    }
		}
	} catch (SQLException ex) {
	    java.util.logging.Logger.getLogger(PrePoolDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	    return preOrderNumberList;
    }
}
