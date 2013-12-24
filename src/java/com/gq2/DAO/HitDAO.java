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
public class HitDAO implements InjectableDAO {

    private static Logger log = LogManager.getLogger(HitDAO.class.getName());
    private Connection conn;

    /**
     *
     * @param conn
     */
    @Override
    public void setConnection(Connection conn) {
	this.conn = conn;
    }

    public HitDAO() {
    }

    /**
     * Devuelve el registro de aciertos seg�n el id
     *
     * @param id	Identifier
     *
     * @return Hit si lo encuentra o null si no
     */
    public Hit load(int id) {
	return load(conn, id);
    }

    protected Hit load(Connection conn, int id) {
	Hit hit = null;
	try {
	    String query = "SELECT * FROM hits WHERE hit_id = ?";
	    PreparedStatement ps = conn.prepareStatement(query);
	    ps.setInt(1, id);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    hit = populateHitFromResultSet(rs);
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return hit;
    }

    /**
     * Guardar un nuevo Apuesta.
     *
     * @param hit Apuesta que se va a guardar.
     * @return id del apuesta creado. 0 si no consigue salvarlo en la BD.
     */
    public int save(Hit hit) {
	try {
	    String sql;
	    sql = "INSERT INTO hits (hit_bet_id, hit_bet_season,hit_bet_order_number,hit_bet_description, "
		    + "hit_reduction_name ,hit_total_columns ,hit_hits_number, hit_columns_number) "
		    + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?)";
	    int identifierGenerated;
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setInt(1, hit.getHitBetId());
		ps.setInt(2, hit.getHitBetSeason());
		ps.setInt(3, hit.getHitBetOrderNumber());
		ps.setString(4, hit.getHitBetDescription());
		ps.setString(5, hit.getHitReductionName());
		ps.setInt(6, hit.getHitTotalColumns());
		ps.setInt(7, hit.getHitHitsNumber());
		ps.setInt(8, hit.getHitColumnsNumber());
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
     * Update hit
     *
     * @param hit Hit to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean update(Hit hit) {
	try {
	    String sql;
	    sql = "UPDATE hits SET hit_bet_id = ?,  hit_bet_season = ?, "
		    + "hit_bet_order_number = ?, hit_bet_description = ?,  "
		    + "hit_reduction_name  = ?, hit_total_columns  = ?, "
		    + "hit_hits_number = ?,  hit_columns_number = ?"
		    + " WHERE hit_id = ? ";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, hit.getHitBetId());
		ps.setInt(2, hit.getHitBetSeason());
		ps.setInt(3, hit.getHitBetOrderNumber());
		ps.setString(4, hit.getHitBetDescription());
		ps.setString(5, hit.getHitReductionName());
		ps.setInt(6, hit.getHitTotalColumns());
		ps.setInt(7, hit.getHitHitsNumber());
		ps.setInt(8, hit.getHitColumnsNumber());
		ps.setInt(9, hit.getHitId());
		log.debug("update: " + ps.toString());
		ps.executeUpdate();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return true;
    }

    /**
     * Delete hit
     *
     * @param hit Hit to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean delete(Hit hit) {
	try {
	    String sql = "DELETE FROM hits WHERE hit_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, hit.getHitId());
		log.debug("delete: " + ps.toString());
		ps.executeUpdate();
	    }
	    return true;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    /**
     * Elimina de la base de datos todos los registros de objetos Hit que
     * pertenezcan a una ronda de un campeonato
     *
     * @param season	Temporada
     * @param round	Id de la ronda a eliminar
     * @return	Numero de filas eliminadas
     */
    public int deleteAuthomaticRoundHit(int chaId, int round) {
	try {
	    //** crear la frase DELETE SQL de tabla1
	    String sql = "DELETE FROM hits WHERE hit_season = ? AND hit_order_number = ?"
		    + " AND hit_description = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, chaId);
	    ps.setInt(2, round);
	    ps.setString(3, "Generated Authomatically");
	    log.debug("deleteAuthomaticRoundHit: " + ps.toString());
	    return ps.executeUpdate();
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return 0;
    }

    public Hit loadHitOnSeasonOrderNumberAndDescription(int season, int orderNumber, String description) {
	Hit hit = null;
	try {
	    String query = "SELECT * "
		    + " FROM hits "
		    + " WHERE hit_season = ? "
		    + " AND hit_order_number = ? "
		    + " AND hit_description = ? ";

	    PreparedStatement ps = conn.prepareStatement(query);
	    ps.setInt(1, season);
	    ps.setInt(2, orderNumber);
	    ps.setString(3, description);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    hit = populateHitFromResultSet(rs);
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return hit;
    }

    public Hit populateHitFromResultSet(ResultSet rs) {
	Hit hit = new Hit();
	try {
	    hit.setHitId(rs.getInt("hit_id"));
	    hit.setHitBetId(rs.getInt("hit_bet_id"));
	    hit.setHitBetSeason(rs.getInt("hit_bet_season"));
	    hit.setHitBetOrderNumber(rs.getInt("hit_bet_order_number"));
	    hit.setHitBetDescription(rs.getString("hit_bet_description"));
	    hit.setHitReductionName(rs.getString("hit_reduction_name"));
	    hit.setHitTotalColumns(rs.getInt("hit_total_columns"));
	    hit.setHitHitsNumber(rs.getInt("hit_nits_number"));
	    hit.setHitColumnsNumber(rs.getInt("hit_columns_number"));
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return hit;
    }

    public List<Hit> loadConditionalHitList(Integer season, Integer order_number, String description) {
	List<Hit> hitList = new ArrayList<>();
	try {
	    String sql =
		    "SELECT * "
		    + " FROM hits "
		    + ((season != null && season != 0) || (order_number != null && order_number != 0)
		    || (description != null && !description.equals("")) ? " WHERE " : "")
		    + (season != null && season != 0 ? " hit_season = " + season : "")
		    + ((order_number != null && order_number != 0) || (description != null && !description.equals("")) ? " AND " : "")
		    + (order_number != null && order_number != 0 ? " hit_order_number = " + order_number : "")
		    + ((description != null && !description.equals("")) ? " AND " : "")
		    + (description != null && !description.equals("") ? " hit_description LIKE '%" + description + "%' " : "")
		    + " ORDER BY hit_season DESC, hit_order_number, hit_description";

	    PreparedStatement ps = conn.prepareStatement(sql);
	    log.debug("loadConditionalHitList: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		while (rs.next()) {
		    hitList.add(populateHitFromResultSet(rs));
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return hitList;
    }

    public Hit loadConditionalHit(Integer season, Integer orderNumber, String description) {
	Hit hit = null;
	try {
	    String sql =
		    "SELECT * "
		    + " FROM hits "
		    + " WHERE  hit_season = ? "
		    + " AND hit_order_number = ? "
		    + " AND hit_description = ? "
		    + " ORDER BY hit_season DESC, hit_order_number DESC";

	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, season);
	    ps.setInt(2, orderNumber);
	    ps.setString(3, description);
	    log.debug("loadConditionalHit: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    hit = populateHitFromResultSet(rs);
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return hit;
    }

    /**
     * Elimina de la base de datos todos los registros de objetos Hit que
     * pertenezcan a una reduccion
     *
     * @param betSeason	Temporada
     * @param betOrderNumber	Jornada
     * @param betDescription	Nombre de la apuesta
     * @param saveReduction	Nombre de la reduccion
     * @return	Numero de filas eliminadas
     */
    public int deleteReductionHits(Integer betSeason, Integer betOrderNumber, String betDescription, String saveReduction) {
	try {
	    //** crear la frase DELETE SQL de tabla1
	    String sql = "DELETE FROM hits WHERE hit_bet_season = ? AND hit_bet_order_number = ?"
		    + " AND hit_bet_description = ? AND hit_reduction_name = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, betSeason);
	    ps.setInt(2, betOrderNumber);
	    ps.setString(3, betDescription);
	    ps.setString(4, saveReduction);
	    log.debug("deleteReductionHits: " + ps.toString());
	    return ps.executeUpdate();
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return 0;
    }
}
