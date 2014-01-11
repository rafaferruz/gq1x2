package com.gq2.DAO;

import com.gq2.domain.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author RAFAEL FERRUZ
 */
public class BetDAO implements InjectableDAO {

    private static Logger log = LogManager.getLogger(BetDAO.class.getName());
    private Connection conn;

    /**
     *
     * @param conn
     */
    @Override
    public void setConnection(Connection conn) {
	this.conn = conn;
    }

    public BetDAO() {
    }

    /**
     * Devuelve la Apuesta seg�n el id
     *
     * @param id	Identifier
     *
     * @return Bet si lo encuentra o null si no
     */
    public Bet load(int id) {
	return load(conn, id);
    }

    protected Bet load(Connection conn, int id) {
	Bet bet = null;
	try {
	    String query = "SELECT * FROM bets WHERE bet_id = ?";
	    PreparedStatement ps = conn.prepareStatement(query);
	    ps.setInt(1, id);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    bet = populateBetFromResultSet(rs);
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return bet;
    }

    /**
     * Guardar un nuevo Apuesta.
     *
     * @param bet Apuesta que se va a guardar.
     * @return id del apuesta creado. 0 si no consigue salvarlo en la BD.
     */
    public int save(Bet bet) {
	try {
	    String sql;
	    sql = "INSERT INTO bets (bet_season,bet_order_number,bet_description ,bet_base ,"
		    + "bet_group_1 ,bet_group1_values_1 ,bet_group1_values_X ,bet_group1_values_2 ,"
		    + "bet_group1_errors_1,bet_group1_errors_X,bet_group1_errors_2,"
		    + "bet_group_2 ,bet_group2_values_1 ,bet_group2_values_X ,bet_group2_values_2 ,"
		    + "bet_group2_errors_1,bet_group2_errors_X,bet_group2_errors_2,"
		    + "bet_group_3 ,bet_group3_values_1 ,bet_group3_values_X ,bet_group3_values_2 ,"
		    + "bet_group3_errors_1,bet_group3_errors_X,bet_group3_errors_2,"
		    + "bet_group_4 ,bet_group4_values_1 ,bet_group4_values_X ,bet_group4_values_2 ,"
		    + "bet_group4_errors_1,bet_group4_errors_X,bet_group4_errors_2,"
		    + "bet_group_5 ,bet_group5_values_1 ,bet_group5_values_X ,bet_group5_values_2, "
		    + "bet_group5_errors_1,bet_group5_errors_X,bet_group5_errors_2) "
		    + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
		    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
		    + " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
		    + " ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    int identifierGenerated;
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setInt(1, bet.getBetSeason());
		ps.setInt(2, bet.getBetOrderNumber());
		ps.setString(3, bet.getBetDescription());
		ps.setString(4, bet.getBetBase());
		ps.setString(5, bet.getBetGroup1());
		ps.setString(6, bet.getBetGroup1Values1());
		ps.setString(7, bet.getBetGroup1ValuesX());
		ps.setString(8, bet.getBetGroup1Values2());
		ps.setInt(9, bet.getBetGroup1Errors1());
		ps.setInt(10, bet.getBetGroup1ErrorsX());
		ps.setInt(11, bet.getBetGroup1Errors2());
		ps.setString(12, bet.getBetGroup2());
		ps.setString(13, bet.getBetGroup2Values1());
		ps.setString(14, bet.getBetGroup2ValuesX());
		ps.setString(15, bet.getBetGroup2Values2());
		ps.setInt(16, bet.getBetGroup2Errors1());
		ps.setInt(17, bet.getBetGroup2ErrorsX());
		ps.setInt(18, bet.getBetGroup2Errors2());
		ps.setString(19, bet.getBetGroup3());
		ps.setString(20, bet.getBetGroup3Values1());
		ps.setString(21, bet.getBetGroup3ValuesX());
		ps.setString(22, bet.getBetGroup3Values2());
		ps.setInt(23, bet.getBetGroup3Errors1());
		ps.setInt(24, bet.getBetGroup3ErrorsX());
		ps.setInt(25, bet.getBetGroup3Errors2());
		ps.setString(26, bet.getBetGroup4());
		ps.setString(27, bet.getBetGroup4Values1());
		ps.setString(28, bet.getBetGroup4ValuesX());
		ps.setString(29, bet.getBetGroup4Values2());
		ps.setInt(30, bet.getBetGroup4Errors1());
		ps.setInt(31, bet.getBetGroup4ErrorsX());
		ps.setInt(32, bet.getBetGroup4Errors2());
		ps.setString(33, bet.getBetGroup5());
		ps.setString(34, bet.getBetGroup5Values1());
		ps.setString(35, bet.getBetGroup5ValuesX());
		ps.setString(36, bet.getBetGroup5Values2());
		ps.setInt(37, bet.getBetGroup5Errors1());
		ps.setInt(38, bet.getBetGroup5ErrorsX());
		ps.setInt(39, bet.getBetGroup5Errors2());
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
     * Update bet
     *
     * @param bet Bet to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean update(Bet bet) {
	try {
	    String sql;
	    sql = "UPDATE bets SET bet_season = ?, bet_order_number = ?, bet_description = ?, bet_base = ?, "
		    + "bet_group_1 = ?,bet_group1_values_1 = ?,bet_group1_values_X = ?,bet_group1_values_2 = ?,"
		    + "bet_group1_errors_1 = ?,bet_group1_errors_X = ?,bet_group1_errors_2 = ?,"
		    + "bet_group_2 = ?,bet_group2_values_1 = ?,bet_group2_values_X = ?,bet_group2_values_2 = ?,"
		    + "bet_group2_errors_1 = ?,bet_group2_errors_X = ?,bet_group2_errors_2 = ?,"
		    + "bet_group_3 = ?,bet_group3_values_1 = ?,bet_group3_values_X = ?,bet_group3_values_2 = ?,"
		    + "bet_group3_errors_1 = ?,bet_group3_errors_X = ?,bet_group3_errors_2 = ?,"
		    + "bet_group_4 = ?,bet_group4_values_1 = ?,bet_group4_values_X = ?,bet_group4_values_2 = ?,"
		    + "bet_group4_errors_1 = ?,bet_group4_errors_X = ?,bet_group4_errors_2 = ?,"
		    + "bet_group_5 = ?,bet_group5_values_1 = ?,bet_group5_values_X = ?,bet_group5_values_2 = ?,"
		    + "bet_group5_errors_1 = ?,bet_group5_errors_X = ?,bet_group5_errors_2 = ?"
		    + " WHERE bet_id = ? ";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, bet.getBetSeason());
		ps.setInt(2, bet.getBetOrderNumber());
		ps.setString(3, bet.getBetDescription());
		ps.setString(4, bet.getBetBase());
		ps.setString(5, bet.getBetGroup1());
		ps.setString(6, bet.getBetGroup1Values1());
		ps.setString(7, bet.getBetGroup1ValuesX());
		ps.setString(8, bet.getBetGroup1Values2());
		ps.setInt(9, bet.getBetGroup1Errors1());
		ps.setInt(10, bet.getBetGroup1ErrorsX());
		ps.setInt(11, bet.getBetGroup1Errors2());
		ps.setString(12, bet.getBetGroup2());
		ps.setString(13, bet.getBetGroup2Values1());
		ps.setString(14, bet.getBetGroup2ValuesX());
		ps.setString(15, bet.getBetGroup2Values2());
		ps.setInt(16, bet.getBetGroup2Errors1());
		ps.setInt(17, bet.getBetGroup2ErrorsX());
		ps.setInt(18, bet.getBetGroup2Errors2());
		ps.setString(19, bet.getBetGroup3());
		ps.setString(20, bet.getBetGroup3Values1());
		ps.setString(21, bet.getBetGroup3ValuesX());
		ps.setString(22, bet.getBetGroup3Values2());
		ps.setInt(23, bet.getBetGroup3Errors1());
		ps.setInt(24, bet.getBetGroup3ErrorsX());
		ps.setInt(25, bet.getBetGroup3Errors2());
		ps.setString(26, bet.getBetGroup4());
		ps.setString(27, bet.getBetGroup4Values1());
		ps.setString(28, bet.getBetGroup4ValuesX());
		ps.setString(29, bet.getBetGroup4Values2());
		ps.setInt(30, bet.getBetGroup4Errors1());
		ps.setInt(31, bet.getBetGroup4ErrorsX());
		ps.setInt(32, bet.getBetGroup4Errors2());
		ps.setString(33, bet.getBetGroup5());
		ps.setString(34, bet.getBetGroup5Values1());
		ps.setString(35, bet.getBetGroup5ValuesX());
		ps.setString(36, bet.getBetGroup5Values2());
		ps.setInt(37, bet.getBetGroup5Errors1());
		ps.setInt(38, bet.getBetGroup5ErrorsX());
		ps.setInt(39, bet.getBetGroup5Errors2());
		ps.setInt(40, bet.getBetId());
		log.debug("update: " + ps.toString());
		ps.executeUpdate();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return true;
    }

    /**
     * Delete bet
     *
     * @param bet Bet to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean delete(Bet bet) {
	try {
	    String sql = "DELETE FROM bets WHERE bet_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, bet.getBetId());
		log.debug("delete: " + ps.toString());
		ps.executeUpdate();
	    }
	    return true;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
    /**
     * Elimina de la base de datos todos los registros de objetos Bet que
     * pertenezcan a una ronda de un campeonato
     *
     * @param season	Temporada
     * @param round	Id de la ronda a eliminar
     * @return	Numero de filas eliminadas
     */
    public int deleteAuthomaticRoundBet(int chaId, int round, String description) {
	try {
	    //** crear la frase DELETE SQL de tabla1
	    String sql = "DELETE FROM bets WHERE bet_season = ? AND bet_order_number = ?"
		    + " AND bet_description = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, chaId);
	    ps.setInt(2, round);
	    ps.setString(3, description);
	    log.debug("deleteAuthomaticRoundBet: " + ps.toString());
	    return ps.executeUpdate();
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return 0;
    }

    public Bet loadBetOnSeasonOrderNumberAndDescription(int season, int orderNumber, String description) {
	Bet bet = null;
	try {
	    String query = "SELECT * "
		    + " FROM bets "
		    + " WHERE bet_season = ? "
		    + " AND bet_order_number = ? "
		    + " AND bet_description = ? ";

	    PreparedStatement ps = conn.prepareStatement(query);
	    ps.setInt(1, season);
	    ps.setInt(2, orderNumber);
	    ps.setString(3, description);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    bet = populateBetFromResultSet(rs);
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return bet;
    }

    public Bet populateBetFromResultSet(ResultSet rs) {
	Bet bet = new Bet();
	try {
	    bet.setBetId(rs.getInt("bet_id"));
	    bet.setBetSeason(rs.getInt("bet_season"));
	    bet.setBetOrderNumber(rs.getInt("bet_order_number"));
	    bet.setBetDescription(rs.getString("bet_description"));
	    bet.setBetBase(rs.getString("bet_base"));
	    bet.setBetGroup1(rs.getString("bet_group_1"));
	    bet.setBetGroup1Values1(rs.getString("bet_group1_values_1"));
	    bet.setBetGroup1ValuesX(rs.getString("bet_group1_values_X"));
	    bet.setBetGroup1Values2(rs.getString("bet_group1_values_2"));
	    bet.setBetGroup1Errors1(rs.getInt("bet_group1_errors_1"));
	    bet.setBetGroup1ErrorsX(rs.getInt("bet_group1_errors_X"));
	    bet.setBetGroup1Errors2(rs.getInt("bet_group1_errors_2"));
	    bet.setBetGroup2(rs.getString("bet_group_2"));
	    bet.setBetGroup2Values1(rs.getString("bet_group2_values_1"));
	    bet.setBetGroup2ValuesX(rs.getString("bet_group2_values_X"));
	    bet.setBetGroup2Values2(rs.getString("bet_group2_values_2"));
	    bet.setBetGroup2Errors1(rs.getInt("bet_group2_errors_1"));
	    bet.setBetGroup2ErrorsX(rs.getInt("bet_group2_errors_X"));
	    bet.setBetGroup2Errors2(rs.getInt("bet_group2_errors_2"));
	    bet.setBetGroup3(rs.getString("bet_group_3"));
	    bet.setBetGroup3Values1(rs.getString("bet_group3_values_1"));
	    bet.setBetGroup3ValuesX(rs.getString("bet_group3_values_X"));
	    bet.setBetGroup3Values2(rs.getString("bet_group3_values_2"));
	    bet.setBetGroup3Errors1(rs.getInt("bet_group3_errors_1"));
	    bet.setBetGroup3ErrorsX(rs.getInt("bet_group3_errors_X"));
	    bet.setBetGroup3Errors2(rs.getInt("bet_group3_errors_2"));
	    bet.setBetGroup4(rs.getString("bet_group_4"));
	    bet.setBetGroup4Values1(rs.getString("bet_group4_values_1"));
	    bet.setBetGroup4ValuesX(rs.getString("bet_group4_values_X"));
	    bet.setBetGroup4Values2(rs.getString("bet_group4_values_2"));
	    bet.setBetGroup4Errors1(rs.getInt("bet_group4_errors_1"));
	    bet.setBetGroup4ErrorsX(rs.getInt("bet_group4_errors_X"));
	    bet.setBetGroup4Errors2(rs.getInt("bet_group4_errors_2"));
	    bet.setBetGroup5(rs.getString("bet_group_5"));
	    bet.setBetGroup5Values1(rs.getString("bet_group5_values_1"));
	    bet.setBetGroup5ValuesX(rs.getString("bet_group5_values_X"));
	    bet.setBetGroup5Values2(rs.getString("bet_group5_values_2"));
	    bet.setBetGroup5Errors1(rs.getInt("bet_group5_errors_1"));
	    bet.setBetGroup5ErrorsX(rs.getInt("bet_group5_errors_X"));
	    bet.setBetGroup5Errors2(rs.getInt("bet_group5_errors_2"));
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return bet;
    }

    public List<Bet> loadConditionalBetList(Integer season, Integer order_number, String description) {
	List<Bet> betList=new ArrayList<>();
	try {
	    String sql =
		    "SELECT * "
		    + " FROM bets "
		    + ((season != null && season != 0) || (order_number != null && order_number != 0)
		    || (description != null && !description.equals("")) ? " WHERE " : "")
		    + (season != null && season != 0 ? " bet_season = " + season : "")
		    + ((season != null && season != 0) && (order_number != null && order_number != 0) ? " AND " : "")
		    + (order_number != null && order_number != 0 ? " bet_order_number = " + order_number : "")
		    + (((season != null && season != 0)||(order_number != null && order_number != 0)) && (description != null && !description.equals("")) ? " AND " : "")
		    + (description != null && !description.equals("") ? " bet_description LIKE '%" + description + "%' " : "")
		    + " ORDER BY bet_season DESC, bet_order_number, bet_description";

	    PreparedStatement ps = conn.prepareStatement(sql);
	    log.debug("loadConditionalBetList: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		while (rs.next()) {
		    betList.add(populateBetFromResultSet(rs));
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return betList;
    }
    public Bet loadConditionalBet(Integer season, Integer orderNumber, String description) {
	Bet bet=null;
	try {
	    String sql =
		    "SELECT * "
		    + " FROM bets "
		    + " WHERE  bet_season = ? "
		    + " AND bet_order_number = ? "
		    + " AND bet_description = ? "
		    + " ORDER BY bet_season DESC, bet_order_number DESC";

	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, season);
	    ps.setInt(2, orderNumber);
	    ps.setString(3, description);
	    log.debug("loadConditionalBet: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    bet=populateBetFromResultSet(rs);
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return bet;
    }

}
