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
public class AwardDAO implements InjectableDAO {

    private static Logger log = LogManager.getLogger(AwardDAO.class.getName());
    private Connection conn;

    /**
     *
     * @param conn
     */
    @Override
    public void setConnection(Connection conn) {
	this.conn = conn;
    }

    public AwardDAO() {
    }

    /**
     * Devuelve el registro de aciertos seg�n el id
     *
     * @param id	Identifier
     *
     * @return Award si lo encuentra o null si no
     */
    public Award load(int id) {
	return load(conn, id);
    }

    protected Award load(Connection conn, int id) {
	Award award = null;
	try {
	    String query = "SELECT * FROM awards WHERE awa_id = ?";
	    PreparedStatement ps = conn.prepareStatement(query);
	    ps.setInt(1, id);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    award = populateAwardFromResultSet(rs);
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return award;
    }

    /**
     * Guardar un nuevo Apuesta.
     *
     * @param award Apuesta que se va a guardar.
     * @return id del apuesta creado. 0 si no consigue salvarlo en la BD.
     */
    public int save(Award award) {
	try {
	    String sql;
	    sql = "INSERT INTO awards (awa_season,awa_order_number,awa_description, "
		    + "awa_bet_price , awa_14_hits_amount, awa_13_hits_amount, awa_12_hits_amount,"
		    + " awa_11_hits_amount, awa_10_hits_amount,) "
		    + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	    int identifierGenerated;
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setInt(1, award.getAwaSeason());
		ps.setInt(2, award.getAwaOrderNumber());
		ps.setString(3, award.getAwaDescription());
		ps.setDouble(4, award.getAwaBetPrice());
		ps.setDouble(5, award.getAwa14HitsAmount());
		ps.setDouble(6, award.getAwa13HitsAmount());
		ps.setDouble(7, award.getAwa12HitsAmount());
		ps.setDouble(8, award.getAwa11HitsAmount());
		ps.setDouble(9, award.getAwa10HitsAmount());
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
     * Update award
     *
     * @param award Award to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean update(Award award) {
	try {
	    String sql;
	    sql = "UPDATE awards SET awa_season = ?, awa_order_number = ?, awa_description = ?,  "
		    + "awa_bet_price  = ?, awa_14_hits_amount  = ?, awa_13_hits_amount  = ?, "
		    + "awa_12_hits_amount  = ?, awa_11_hits_amount  = ?, awa_10_hits_amount  = ? "
		    + " WHERE awa_id = ? ";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, award.getAwaSeason());
		ps.setInt(2, award.getAwaOrderNumber());
		ps.setString(3, award.getAwaDescription());
		ps.setDouble(4, award.getAwaBetPrice());
		ps.setDouble(5, award.getAwa14HitsAmount());
		ps.setDouble(6, award.getAwa13HitsAmount());
		ps.setDouble(7, award.getAwa12HitsAmount());
		ps.setDouble(8, award.getAwa11HitsAmount());
		ps.setDouble(9, award.getAwa10HitsAmount());
		log.debug("update: " + ps.toString());
		ps.executeUpdate();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return true;
    }

    /**
     * Delete award
     *
     * @param award Award to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean delete(Award award) {
	try {
	    String sql = "DELETE FROM awards WHERE awa_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, award.getAwaId());
		log.debug("delete: " + ps.toString());
		ps.executeUpdate();
	    }
	    return true;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }


    public Award loadAwardOnSeasonOrderNumberAndDescription(int season, int orderNumber, String description) {
	Award award = null;
	try {
	    String query = "SELECT * "
		    + " FROM awards "
		    + " WHERE awa_season = ? "
		    + " AND awa_order_number = ? "
		    + " AND awa_description = ? ";

	    PreparedStatement ps = conn.prepareStatement(query);
	    ps.setInt(1, season);
	    ps.setInt(2, orderNumber);
	    ps.setString(3, description);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    award = populateAwardFromResultSet(rs);
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return award;
    }

    public Award populateAwardFromResultSet(ResultSet rs) {
	Award award = new Award();
	try {
	    award.setAwaId(rs.getInt("awa_id"));
	    award.setAwaSeason(rs.getInt("awa_bet_season"));
	    award.setAwaOrderNumber(rs.getInt("awa_bet_order_number"));
	    award.setAwaDescription(rs.getString("awa_bet_description"));
	    award.setAwaBetPrice(rs.getDouble("awa_bet_price"));
	    award.setAwa14HitsAmount(rs.getDouble("awa_14_hits_amount"));
	    award.setAwa13HitsAmount(rs.getDouble("awa_13_hits_amount"));
	    award.setAwa12HitsAmount(rs.getDouble("awa_12_hits_amount"));
	    award.setAwa11HitsAmount(rs.getDouble("awa_11_hits_amount"));
	    award.setAwa10HitsAmount(rs.getDouble("awa_10_hits_amount"));
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return award;
    }

    public List<Award> loadConditionalAwardList(Integer season, Integer order_number, String description) {
	List<Award> awardList = new ArrayList<>();
	try {
	    String sql =
		    "SELECT * "
		    + " FROM awards "
		    + ((season != null && season != 0) || (order_number != null && order_number != 0)
		    || (description != null && !description.equals("")) ? " WHERE " : "")
		    + (season != null && season != 0 ? " awa_season = " + season : "")
		    + ((order_number != null && order_number != 0) || (description != null && !description.equals("")) ? " AND " : "")
		    + (order_number != null && order_number != 0 ? " awa_order_number = " + order_number : "")
		    + ((description != null && !description.equals("")) ? " AND " : "")
		    + (description != null && !description.equals("") ? " awa_description LIKE '%" + description + "%' " : "")
		    + " ORDER BY awa_season DESC, awa_order_number, awa_description";

	    PreparedStatement ps = conn.prepareStatement(sql);
	    log.debug("loadConditionalAwardList: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		while (rs.next()) {
		    awardList.add(populateAwardFromResultSet(rs));
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return awardList;
    }

    public Award loadConditionalAward(Integer season, Integer orderNumber, String description) {
	Award award = null;
	try {
	    String sql =
		    "SELECT * "
		    + " FROM awards "
		    + " WHERE  awa_season = ? "
		    + " AND awa_order_number = ? "
		    + " AND awa_description = ? "
		    + " ORDER BY awa_season DESC, awa_order_number DESC";

	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, season);
	    ps.setInt(2, orderNumber);
	    ps.setString(3, description);
	    log.debug("loadConditionalAward: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    award = populateAwardFromResultSet(rs);
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return award;
    }

}
