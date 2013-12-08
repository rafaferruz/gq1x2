package com.gq2.DAO;

import com.gq2.domain.Prognostic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class PrognosticDAO implements InjectableDAO {

    transient private final Logger log = LogManager.getLogger(PrognosticDAO.class.getName());
    private Connection conn;
    private String fieldNamesList;

    /**
     *
     * @param conn
     */
    @Override
    public void setConnection(Connection conn) {
	this.conn = conn;
    }

    public PrognosticDAO() {
	fieldNamesList = "pro_cha_id,"
		+ "pro_round,"
		+ "pro_date,"
		+ "pro_sco_id,"
		+ "pro_sco_team1_id,"
		+ "pro_sco_team2_id,"
		+ "pro_sco_score1,"
		+ "pro_sco_score2,"
		+ "pro_cla_previous1_id,"
		+ "pro_cla_previous1_total_played_games,"
		+ "pro_cla_previous1_total_won_games,"
		+ "pro_cla_previous1_total_drawn_games,"
		+ "pro_cla_previous1_total_lost_games,"
		+ "pro_cla_previous1_total_own_goals,"
		+ "pro_cla_previous1_total_against_goals,"
		+ "pro_cla_previous1_home_played_games,"
		+ "pro_cla_previous1_home_won_games,"
		+ "pro_cla_previous1_home_drawn_games,"
		+ "pro_cla_previous1_home_lost_games,"
		+ "pro_cla_previous1_home_own_goals,"
		+ "pro_cla_previous1_home_against_goals,"
		+ "pro_cla_previous2_id,"
		+ "pro_cla_previous2_total_played_games,"
		+ "pro_cla_previous2_total_won_games,"
		+ "pro_cla_previous2_total_drawn_games,"
		+ "pro_cla_previous2_total_lost_games,"
		+ "pro_cla_previous2_total_own_goals,"
		+ "pro_cla_previous2_total_against_goals,"
		+ "pro_cla_previous2_out_played_games,"
		+ "pro_cla_previous2_out_won_games,"
		+ "pro_cla_previous2_out_drawn_games,"
		+ "pro_cla_previous2_out_lost_games,"
		+ "pro_cla_previous2_out_own_goals,"
		+ "pro_cla_previous2_out_against_goals,"
		+ "pro_wdl_previous1_id,"
		+ "pro_wdl_previous1_PSG,"
		+ "pro_wdl_previous1_PSE,"
		+ "pro_wdl_previous1_PSP,"
		+ "pro_wdl_previous1_PSNG,"
		+ "pro_wdl_previous1_PSNE,"
		+ "pro_wdl_previous1_PSNP,"
		+ "pro_wdl_previous2_id,"
		+ "pro_wdl_previous2_PSG,"
		+ "pro_wdl_previous2_PSE,"
		+ "pro_wdl_previous2_PSP,"
		+ "pro_wdl_previous2_PSNG,"
		+ "pro_wdl_previous2_PSNE,"
		+ "pro_wdl_previous2_PSNP,"
		+ "pro_rat4_id,"
		+ "pro_rat4_team1_id,"
		+ "pro_rat4_team1_previous,"
		+ "pro_rat4_team2_id,"
		+ "pro_rat4_team2_previous,"
		+ "pro_rat4_previous_diference,"
		+ "pro_rat4_probability_win,"
		+ "pro_rat4_probability_draw,"
		+ "pro_rat4_probability_lose,"
		+ "pro_rat4_score_sign,"
		+ "pro_rat4_team1_post,"
		+ "pro_rat4_team2_post,"
		+ "pro_id";
    }

    public int save(Prognostic prognostic) {
	try {
	    int identifierGenerated;
	    //** crear la frase INSERT SQL
	    String sql = "INSERT INTO prognostics ("
		    + fieldNamesList
		    + ") "
		    + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "? )";
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setInt(1, prognostic.getPro_cha_id());
		ps.setInt(2, prognostic.getPro_round());
		ps.setDate(3, new java.sql.Date(prognostic.getPro_date().getTime()));
		ps.setInt(4, prognostic.getPro_sco_id());
		ps.setInt(5, prognostic.getPro_sco_team1_id());
		ps.setInt(6, prognostic.getPro_sco_team2_id());
		ps.setInt(7, prognostic.getPro_sco_score1());
		ps.setInt(8, prognostic.getPro_sco_score2());
		ps.setInt(9, prognostic.getPro_cla_previous1_id());
		ps.setInt(10, prognostic.getPro_cla_previous1_total_played_games());
		ps.setInt(11, prognostic.getPro_cla_previous1_total_won_games());
		ps.setInt(12, prognostic.getPro_cla_previous1_total_drawn_games());
		ps.setInt(13, prognostic.getPro_cla_previous1_total_lost_games());
		ps.setInt(14, prognostic.getPro_cla_previous1_total_own_goals());
		ps.setInt(15, prognostic.getPro_cla_previous1_total_against_goals());
		ps.setInt(16, prognostic.getPro_cla_previous1_home_played_games());
		ps.setInt(17, prognostic.getPro_cla_previous1_home_won_games());
		ps.setInt(18, prognostic.getPro_cla_previous1_home_drawn_games());
		ps.setInt(19, prognostic.getPro_cla_previous1_home_lost_games());
		ps.setInt(20, prognostic.getPro_cla_previous1_home_own_goals());
		ps.setInt(21, prognostic.getPro_cla_previous1_home_against_goals());
		ps.setInt(22, prognostic.getPro_cla_previous2_id());
		ps.setInt(23, prognostic.getPro_cla_previous2_total_played_games());
		ps.setInt(24, prognostic.getPro_cla_previous2_total_won_games());
		ps.setInt(25, prognostic.getPro_cla_previous2_total_drawn_games());
		ps.setInt(26, prognostic.getPro_cla_previous2_total_lost_games());
		ps.setInt(27, prognostic.getPro_cla_previous2_total_own_goals());
		ps.setInt(28, prognostic.getPro_cla_previous2_total_against_goals());
		ps.setInt(29, prognostic.getPro_cla_previous2_out_played_games());
		ps.setInt(30, prognostic.getPro_cla_previous2_out_won_games());
		ps.setInt(31, prognostic.getPro_cla_previous2_out_drawn_games());
		ps.setInt(32, prognostic.getPro_cla_previous2_out_lost_games());
		ps.setInt(33, prognostic.getPro_cla_previous2_out_own_goals());
		ps.setInt(34, prognostic.getPro_cla_previous2_out_against_goals());
		ps.setInt(35, prognostic.getPro_wdl_previous1_id());
		ps.setInt(36, prognostic.getPro_wdl_previous1_PSG());
		ps.setInt(37, prognostic.getPro_wdl_previous1_PSE());
		ps.setInt(38, prognostic.getPro_wdl_previous1_PSP());
		ps.setInt(39, prognostic.getPro_wdl_previous1_PSNG());
		ps.setInt(40, prognostic.getPro_wdl_previous1_PSNE());
		ps.setInt(41, prognostic.getPro_wdl_previous1_PSNP());
		ps.setInt(42, prognostic.getPro_wdl_previous2_id());
		ps.setInt(43, prognostic.getPro_wdl_previous2_PSG());
		ps.setInt(44, prognostic.getPro_wdl_previous2_PSE());
		ps.setInt(45, prognostic.getPro_wdl_previous2_PSP());
		ps.setInt(46, prognostic.getPro_wdl_previous2_PSNG());
		ps.setInt(47, prognostic.getPro_wdl_previous2_PSNE());
		ps.setInt(48, prognostic.getPro_wdl_previous2_PSNP());
		ps.setInt(49, prognostic.getPro_rat4_id());
		ps.setInt(50, prognostic.getPro_rat4_team1_id());
		ps.setInt(51, prognostic.getPro_rat4_team1_previous());
		ps.setInt(52, prognostic.getPro_rat4_team2_id());
		ps.setInt(53, prognostic.getPro_rat4_team2_previous());
		ps.setInt(54, prognostic.getPro_rat4_previous_diference());
		ps.setInt(55, prognostic.getPro_rat4_probability_win());
		ps.setInt(56, prognostic.getPro_rat4_probability_draw());
		ps.setInt(57, prognostic.getPro_rat4_probability_lose());
		ps.setString(58, prognostic.getPro_rat4_score_sign());
		ps.setInt(59, prognostic.getPro_rat4_team1_post());
		ps.setInt(60, prognostic.getPro_rat4_team2_post());
		ps.setInt(61, prognostic.getPro_id());

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
     * Elimina de la base de datos todos los registros de clasificacion que
     * pertenezcan a una ronda de un campeonato
     *
     * @param chaId	Id del campeonato
     * @param round	Id de la ronda a eliminar
     * @return	Numero de filas eliminadas
     */
    public int deleteRoundPrognostic(int chaId, int round) {
	try {
	    //** crear la frase DELETE SQL de tabla1
	    String sql = "DELETE FROM prognostics WHERE pro_cha_id = ? AND pro_round = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, chaId);
	    ps.setInt(2, round);
	    log.debug("deleteRoundPrognostic: " + ps.toString());
	    return ps.executeUpdate();
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return 0;
    }

    public List<Integer> loadPrognosticRounds(int chaId) {
	List<Integer> roundList = new ArrayList<>();
	String query = "SELECT DISTINCT(pro_round) as round FROM prognostics WHERE pro_cha_id = ? ORDER BY pro_round";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, chaId);
	    log.debug("loadPrognosticRounds: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		while (rs.next()) {
		    roundList.add(rs.getInt("round"));
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return roundList;
    }

    public ResultSet loadPrognosticRoundListWithTeamNames(Integer chaId, Integer round) {
	ResultSet rs;
	try {
	    String sql = "SELECT pro.*, tea1.tea_name AS tea_name1,"
		    + " tea2.tea_name AS tea_name2"
		    + " FROM prognostics AS pro, teams AS tea1, teams AS tea2 "
		    + " WHERE pro_cha_id = ? "
		    + " AND pro_round = ? "
		    + " AND tea1.tea_id = pro_sco_team1_id "
		    + " AND tea2.tea_id = pro_sco_team2_id "
		    + " ORDER BY pro_round";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, chaId);
		ps.setInt(2, round);
		log.debug("loadRoundPronosticListWithTeamNames: " + ps.toString());
		rs = ps.executeQuery();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return rs;
    }

    public List<Prognostic> loadPrognosticRoundList(Integer chaId, Integer round) {
	List<Prognostic> prognosticList = new ArrayList<>();
	try {
	    String sql = "SELECT * FROM prognostics "
		    + " WHERE pro_cha_id = ? "
		    + " AND pro_round = ? ";

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, chaId);
		ps.setInt(2, round);
		log.debug("loadRoundPronosticListWithTeamNames: " + ps.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
		    prognosticList.add(populatePrognosticFromResultSet(rs));
		}

	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return prognosticList;
    }

    private Prognostic populatePrognosticFromResultSet(ResultSet rs) {
	Prognostic prognostic = new Prognostic();
	try {
    prognostic.setPro_id(rs.getInt("pro_id"));
    prognostic.setPro_cha_id(rs.getInt("pro_cha_id"));
    prognostic.setPro_round(rs.getInt("pro_round"));
    prognostic.setPro_date(rs.getDate("pro_date"));
    prognostic.setPro_sco_id(rs.getInt("pro_sco_id"));
    prognostic.setPro_sco_team1_id(rs.getInt("pro_sco_team1_id"));
    prognostic.setPro_sco_team2_id(rs.getInt("pro_sco_team2_id"));
    prognostic.setPro_sco_score1(rs.getInt("pro_sco_score1"));
    prognostic.setPro_sco_score2(rs.getInt("pro_sco_score2"));
    prognostic.setPro_cla_previous1_id(rs.getInt("pro_cla_previous1_id"));
    prognostic.setPro_cla_previous1_total_played_games(rs.getInt("pro_cla_previous1_total_played_games"));
    prognostic.setPro_cla_previous1_total_won_games(rs.getInt("pro_cla_previous1_total_won_games"));
    prognostic.setPro_cla_previous1_total_drawn_games(rs.getInt("pro_cla_previous1_total_drawn_games"));
    prognostic.setPro_cla_previous1_total_lost_games(rs.getInt("pro_cla_previous1_total_lost_games"));
    prognostic.setPro_cla_previous1_total_own_goals(rs.getInt("pro_cla_previous1_total_own_goals"));
    prognostic.setPro_cla_previous1_total_against_goals(rs.getInt("pro_cla_previous1_total_against_goals"));
    prognostic.setPro_cla_previous1_home_played_games(rs.getInt("pro_cla_previous1_home_played_games"));
    prognostic.setPro_cla_previous1_home_won_games(rs.getInt("pro_cla_previous1_home_won_games"));
    prognostic.setPro_cla_previous1_home_drawn_games(rs.getInt("pro_cla_previous1_home_drawn_games"));
    prognostic.setPro_cla_previous1_home_lost_games(rs.getInt("pro_cla_previous1_home_lost_games"));
    prognostic.setPro_cla_previous1_home_own_goals(rs.getInt("pro_cla_previous1_home_own_goals"));
    prognostic.setPro_cla_previous1_home_against_goals(rs.getInt("pro_cla_previous1_home_against_goals"));
    prognostic.setPro_cla_previous2_id(rs.getInt("pro_cla_previous2_id"));
    prognostic.setPro_cla_previous2_total_played_games(rs.getInt("pro_cla_previous2_total_played_games"));
    prognostic.setPro_cla_previous2_total_won_games(rs.getInt("pro_cla_previous2_total_won_games"));
    prognostic.setPro_cla_previous2_total_drawn_games(rs.getInt("pro_cla_previous2_total_drawn_games"));
    prognostic.setPro_cla_previous2_total_lost_games(rs.getInt("pro_cla_previous2_total_lost_games"));
    prognostic.setPro_cla_previous2_total_own_goals(rs.getInt("pro_cla_previous2_total_own_goals"));
    prognostic.setPro_cla_previous2_total_against_goals(rs.getInt("pro_cla_previous2_total_against_goals"));
    prognostic.setPro_cla_previous2_out_played_games(rs.getInt("pro_cla_previous2_out_played_games"));
    prognostic.setPro_cla_previous2_out_won_games(rs.getInt("pro_cla_previous2_out_won_games"));
    prognostic.setPro_cla_previous2_out_drawn_games(rs.getInt("pro_cla_previous2_out_drawn_games"));
    prognostic.setPro_cla_previous2_out_lost_games(rs.getInt("pro_cla_previous2_out_lost_games"));
    prognostic.setPro_cla_previous2_out_own_goals(rs.getInt("pro_cla_previous2_out_own_goals"));
    prognostic.setPro_cla_previous2_out_against_goals(rs.getInt("pro_cla_previous2_out_against_goals"));
    prognostic.setPro_wdl_previous1_id(rs.getInt("pro_wdl_previous1_id"));
    prognostic.setPro_wdl_previous1_PSG(rs.getInt("pro_wdl_previous1_PSG"));
    prognostic.setPro_wdl_previous1_PSE(rs.getInt("pro_wdl_previous1_PSE"));
    prognostic.setPro_wdl_previous1_PSP(rs.getInt("pro_wdl_previous1_PSP"));
    prognostic.setPro_wdl_previous1_PSNG(rs.getInt("pro_wdl_previous1_PSNG"));
    prognostic.setPro_wdl_previous1_PSNE(rs.getInt("pro_wdl_previous1_PSNE"));
    prognostic.setPro_wdl_previous1_PSNP(rs.getInt("pro_wdl_previous1_PSNP"));
    prognostic.setPro_wdl_previous2_id(rs.getInt("pro_wdl_previous2_id"));
    prognostic.setPro_wdl_previous2_PSG(rs.getInt("pro_wdl_previous2_PSG"));
    prognostic.setPro_wdl_previous2_PSE(rs.getInt("pro_wdl_previous2_PSE"));
    prognostic.setPro_wdl_previous2_PSP(rs.getInt("pro_wdl_previous2_PSP"));
    prognostic.setPro_wdl_previous2_PSNG(rs.getInt("pro_wdl_previous2_PSNG"));
    prognostic.setPro_wdl_previous2_PSNE(rs.getInt("pro_wdl_previous2_PSNE"));
    prognostic.setPro_wdl_previous2_PSNP(rs.getInt("pro_wdl_previous2_PSNP"));
    prognostic.setPro_rat4_id(rs.getInt("pro_rat4_id"));
    prognostic.setPro_rat4_team1_id(rs.getInt("pro_rat4_team1_id"));
    prognostic.setPro_rat4_team1_previous(rs.getInt("pro_rat4_team1_previous"));
    prognostic.setPro_rat4_team2_id(rs.getInt("pro_rat4_team2_id"));
    prognostic.setPro_rat4_team2_previous(rs.getInt("pro_rat4_team2_previous"));
    prognostic.setPro_rat4_previous_diference(rs.getInt("pro_rat4_previous_diference"));
    prognostic.setPro_rat4_probability_win(rs.getInt("pro_rat4_probability_win"));
    prognostic.setPro_rat4_probability_draw(rs.getInt("pro_rat4_probability_draw"));
    prognostic.setPro_rat4_probability_lose(rs.getInt("pro_rat4_probability_lose"));
    prognostic.setPro_rat4_score_sign(rs.getString("pro_rat4_score_sign"));
    prognostic.setPro_rat4_team1_post(rs.getInt("pro_rat4_team1_post"));
    prognostic.setPro_rat4_team2_post(rs.getInt("pro_rat4_team2_post"));

	} catch (SQLException ex) {
	    log.error(ex);
	}
	return prognostic;
    }
}
