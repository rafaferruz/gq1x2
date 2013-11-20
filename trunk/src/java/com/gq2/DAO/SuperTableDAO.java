package com.gq2.DAO;

import com.gq2.domain.SuperTable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class SuperTableDAO implements InjectableDAO {

    transient private final Logger log = LogManager.getLogger(this.getClass());
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

    public SuperTableDAO() {
	fieldNamesList = "stab_cha_id,"
		+ "stab_round,"
		+ "stab_date,"
		+ "stab_sco_id,"
		+ "stab_sco_team1_id,"
		+ "stab_sco_team2_id,"
		+ "stab_sco_score1,"
		+ "stab_sco_score2,"
		+ "stab_cla_id,"
		+ "stab_cla_tea_id,"
		+ "stab_cla_position,"
		+ "stab_cla_points,"
		+ "stab_cla_total_played_games,"
		+ "stab_cla_total_won_games,"
		+ "stab_cla_total_drawn_games,"
		+ "stab_cla_total_lost_games,"
		+ "stab_cla_total_own_goals,"
		+ "stab_cla_total_against_goals,"
		+ "stab_cla_home_played_games,"
		+ "stab_cla_home_won_games,"
		+ "stab_cla_home_drawn_games,"
		+ "stab_cla_home_lost_games,"
		+ "stab_cla_home_own_goals,"
		+ "stab_cla_home_against_goals,"
		+ "stab_cla_out_played_games,"
		+ "stab_cla_out_won_games,"
		+ "stab_cla_out_drawn_games,"
		+ "stab_cla_out_lost_games,"
		+ "stab_cla_out_own_goals,"
		+ "stab_cla_out_against_goals,"
		+ "stab_cla_rating,"
		+ "stab_cla_previous_id,"
		+ "stab_cla_previous_tea_id,"
		+ "stab_cla_previous_position,"
		+ "stab_cla_previous_points,"
		+ "stab_cla_previous_total_played_games,"
		+ "stab_cla_previous_total_won_games,"
		+ "stab_cla_previous_total_drawn_games,"
		+ "stab_cla_previous_total_lost_games,"
		+ "stab_cla_previous_total_own_goals,"
		+ "stab_cla_previous_total_against_goals,"
		+ "stab_cla_previous_home_played_games,"
		+ "stab_cla_previous_home_won_games,"
		+ "stab_cla_previous_home_drawn_games,"
		+ "stab_cla_previous_home_lost_games,"
		+ "stab_cla_previous_home_own_goals,"
		+ "stab_cla_previous_home_against_goals,"
		+ "stab_cla_previous_out_played_games,"
		+ "stab_cla_previous_out_won_games,"
		+ "stab_cla_previous_out_drawn_games,"
		+ "stab_cla_previous_out_lost_games,"
		+ "stab_cla_previous_out_own_goals,"
		+ "stab_cla_previous_out_against_goals,"
		+ "stab_cla_previous_rating,"
		+ "stab_wdl_id,"
		+ "stab_wdl_position,"
		+ "stab_wdl_tea_id,"
		+ "stab_wdl_PSG,"
		+ "stab_wdl_PSE,"
		+ "stab_wdl_PSP,"
		+ "stab_wdl_PSNG,"
		+ "stab_wdl_PSNE,"
		+ "stab_wdl_PSNP,"
		+ "stab_wdl_previous_id,"
		+ "stab_wdl_previous_position,"
		+ "stab_wdl_previous_tea_id,"
		+ "stab_wdl_previous_PSG,"
		+ "stab_wdl_previous_PSE,"
		+ "stab_wdl_previous_PSP,"
		+ "stab_wdl_previous_PSNG,"
		+ "stab_wdl_previous_PSNE,"
		+ "stab_wdl_previous_PSNP,"
		+ "stab_rat4_id,"
		+ "stab_rat4_team1_id,"
		+ "stab_rat4_team1_previous,"
		+ "stab_rat4_team2_id,"
		+ "stab_rat4_team2_previous,"
		+ "stab_rat4_previous_diference,"
		+ "stab_rat4_probability_win,"
		+ "stab_rat4_probability_draw,"
		+ "stab_rat4_probability_lose,"
		+ "stab_rat4_score_sign,"
		+ "stab_rat4_team1_post,"
		+ "stab_rat4_team2_post,"
		+ "stab_id";
    }

    public int save(SuperTable superTable) {
	try {
	    int identifierGenerated;
	    //** crear la frase INSERT SQL
	    String sql = "INSERT INTO superTable ("
		    + fieldNamesList
		    + ") "
		    + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
		    + "?, ?, ?, ?, ?)";
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setInt(1, superTable.getStab_cha_id());
		ps.setInt(2, superTable.getStab_round());
		ps.setDate(3, new java.sql.Date(superTable.getStab_date().getTime()));
		ps.setInt(4, superTable.getStab_sco_id());
		ps.setInt(5, superTable.getStab_sco_team1_id());
		ps.setInt(6, superTable.getStab_sco_team2_id());
		ps.setInt(7, superTable.getStab_sco_score1());
		ps.setInt(8, superTable.getStab_sco_score2());
		ps.setInt(9, superTable.getStab_cla_id());
		ps.setInt(10, superTable.getStab_cla_position());
		ps.setInt(11, superTable.getStab_cla_tea_id());
		ps.setInt(12, superTable.getStab_cla_points());
		ps.setInt(13, superTable.getStab_cla_total_played_games());
		ps.setInt(14, superTable.getStab_cla_total_won_games());
		ps.setInt(15, superTable.getStab_cla_total_drawn_games());
		ps.setInt(16, superTable.getStab_cla_total_lost_games());
		ps.setInt(17, superTable.getStab_cla_total_own_goals());
		ps.setInt(18, superTable.getStab_cla_total_against_goals());
		ps.setInt(19, superTable.getStab_cla_home_played_games());
		ps.setInt(20, superTable.getStab_cla_home_won_games());
		ps.setInt(21, superTable.getStab_cla_home_drawn_games());
		ps.setInt(22, superTable.getStab_cla_home_lost_games());
		ps.setInt(23, superTable.getStab_cla_home_own_goals());
		ps.setInt(24, superTable.getStab_cla_home_against_goals());
		ps.setInt(25, superTable.getStab_cla_out_played_games());
		ps.setInt(26, superTable.getStab_cla_out_won_games());
		ps.setInt(27, superTable.getStab_cla_out_drawn_games());
		ps.setInt(28, superTable.getStab_cla_out_lost_games());
		ps.setInt(29, superTable.getStab_cla_out_own_goals());
		ps.setInt(30, superTable.getStab_cla_out_against_goals());
		ps.setInt(31, superTable.getStab_cla_rating());
		ps.setInt(32, superTable.getStab_cla_previous_id());
		ps.setInt(33, superTable.getStab_cla_previous_position());
		ps.setInt(34, superTable.getStab_cla_previous_tea_id());
		ps.setInt(35, superTable.getStab_cla_previous_points());
		ps.setInt(36, superTable.getStab_cla_previous_total_played_games());
		ps.setInt(37, superTable.getStab_cla_previous_total_won_games());
		ps.setInt(38, superTable.getStab_cla_previous_total_drawn_games());
		ps.setInt(39, superTable.getStab_cla_previous_total_lost_games());
		ps.setInt(40, superTable.getStab_cla_previous_total_own_goals());
		ps.setInt(41, superTable.getStab_cla_previous_total_against_goals());
		ps.setInt(42, superTable.getStab_cla_previous_home_played_games());
		ps.setInt(43, superTable.getStab_cla_previous_home_won_games());
		ps.setInt(44, superTable.getStab_cla_previous_home_drawn_games());
		ps.setInt(45, superTable.getStab_cla_previous_home_lost_games());
		ps.setInt(46, superTable.getStab_cla_previous_home_own_goals());
		ps.setInt(47, superTable.getStab_cla_previous_home_against_goals());
		ps.setInt(48, superTable.getStab_cla_previous_out_played_games());
		ps.setInt(49, superTable.getStab_cla_previous_out_won_games());
		ps.setInt(50, superTable.getStab_cla_previous_out_drawn_games());
		ps.setInt(51, superTable.getStab_cla_previous_out_lost_games());
		ps.setInt(52, superTable.getStab_cla_previous_out_own_goals());
		ps.setInt(53, superTable.getStab_cla_previous_out_against_goals());
		ps.setInt(54, superTable.getStab_cla_previous_rating());
		ps.setInt(55, superTable.getStab_wdl_id());
		ps.setInt(56, superTable.getStab_wdl_position());
		ps.setInt(57, superTable.getStab_wdl_tea_id());
		ps.setInt(58, superTable.getStab_wdl_PSG());
		ps.setInt(59, superTable.getStab_wdl_PSE());
		ps.setInt(60, superTable.getStab_wdl_PSP());
		ps.setInt(61, superTable.getStab_wdl_PSNG());
		ps.setInt(62, superTable.getStab_wdl_PSNE());
		ps.setInt(63, superTable.getStab_wdl_PSNP());
		ps.setInt(64, superTable.getStab_wdl_previous_id());
		ps.setInt(65, superTable.getStab_wdl_previous_position());
		ps.setInt(66, superTable.getStab_wdl_previous_tea_id());
		ps.setInt(67, superTable.getStab_wdl_previous_PSG());
		ps.setInt(68, superTable.getStab_wdl_previous_PSE());
		ps.setInt(69, superTable.getStab_wdl_previous_PSP());
		ps.setInt(70, superTable.getStab_wdl_previous_PSNG());
		ps.setInt(71, superTable.getStab_wdl_previous_PSNE());
		ps.setInt(72, superTable.getStab_wdl_previous_PSNP());
		ps.setInt(73, superTable.getStab_rat4_id());
		ps.setInt(74, superTable.getStab_rat4_team1_id());
		ps.setInt(75, superTable.getStab_rat4_team1_previous());
		ps.setInt(76, superTable.getStab_rat4_team2_id());
		ps.setInt(77, superTable.getStab_rat4_team2_previous());
		ps.setInt(78, superTable.getStab_rat4_previous_diference());
		ps.setInt(79, superTable.getStab_rat4_probability_win());
		ps.setInt(80, superTable.getStab_rat4_probability_draw());
		ps.setInt(81, superTable.getStab_rat4_probability_lose());
		ps.setString(82, superTable.getStab_rat4_score_sign());
		ps.setInt(83, superTable.getStab_rat4_team1_post());
		ps.setInt(84, superTable.getStab_rat4_team2_post());
		ps.setInt(85, superTable.getStab_id());

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
    public int deleteRoundSuperTable(int chaId, int round) {
	try {
	    //** crear la frase DELETE SQL de tabla1
	    String sql = "DELETE FROM superTable WHERE stab_cha_id = ? AND stab_round = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, chaId);
	    ps.setInt(2, round);
	    log.debug("deleteRoundSuperTable: " + ps.toString());
	    return ps.executeUpdate();
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return 0;
    }
}
