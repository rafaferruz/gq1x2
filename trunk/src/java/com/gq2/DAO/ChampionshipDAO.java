package com.gq2.DAO;

import com.gq2.domain.Championship;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DAO de los Campeonatos
 */
public class ChampionshipDAO implements InjectableDAO {

    private static Logger log = LogManager.getLogger(ChampionshipDAO.class.getName());
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
     * Devuelve el Campeonato seg�n el id
     *
     * @param id	Identifier
     *
     * @return Championship si lo encuentra o null si no
     */
    public Championship load(int id) {
	try {
	    return load(conn, id);
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    protected Championship load(Connection conn, int id) throws SQLException {
	Championship championship = null;
	String query = "SELECT * FROM championships WHERE cha_id = ?";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, id);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    championship = populateChampionshipFromResultSet(rs);
		}
	    }
	}
	return championship;
    }

    /**
     * Guardar un nuevo Campeonato.
     *
     * @param championship Campeonato que se va a guardar.
     * @return id del campeonato creado. 0 si no consigue salvarlo en la BD.
     */
    public int save(Championship championship) {
	try {
	    String sql;
	    sql = "INSERT INTO championships (cha_code, cha_description, "
		    + "cha_status, cha_season, cha_start_date, cha_points_win, "
		    + "cha_points_draw, cha_points_lose, cha_max_teams) "
		    + "VALUES (?,?,?,?,?,?,?,?,?)";
	    int identifierGenerated;
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setString(1, championship.getChaCode());
		ps.setString(2, championship.getChaDescription());
		ps.setInt(3, championship.getChaStatus());
		ps.setString(4, championship.getChaSeason());
		ps.setDate(5, new java.sql.Date(championship.getChaStartDate().getTime()));
		ps.setInt(6, championship.getChaPointsWin());
		ps.setInt(7, championship.getChaPointsDraw());
		ps.setInt(8, championship.getChaPointsLose());
		ps.setInt(9, championship.getChaMaxTeams());
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
     * Update championship
     *
     * @param championship Championship to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean update(Championship championship) {
	try {
	    String sql = "UPDATE championships SET "
		    + "cha_code = ?, cha_description = ?, "
		    + "cha_status = ?, cha_season = ?, cha_start_date = ?, cha_points_win = ?, "
		    + "cha_points_draw ? ?, cha_points_lose = ?, cha_max_teams = ? " + "WHERE cha_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setString(1, championship.getChaCode());
		ps.setString(2, championship.getChaDescription());
		ps.setInt(3, championship.getChaStatus());
		ps.setString(4, championship.getChaSeason());
		ps.setDate(5, new java.sql.Date(championship.getChaStartDate().getTime()));
		ps.setInt(6, championship.getChaPointsWin());
		ps.setInt(7, championship.getChaPointsDraw());
		ps.setInt(8, championship.getChaPointsLose());
		ps.setInt(9, championship.getChaMaxTeams());
		ps.setInt(10, championship.getChaId());
		log.debug("update: " + ps.toString());
		ps.executeUpdate();
		return true;
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    /**
     * Delete championship
     *
     * @param championship Championship to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean delete(Championship championship) {
	try {
	    String sql = "DELETE FROM championships WHERE cha_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, championship.getChaId());
		log.debug("delete: " + ps.toString());
		ps.executeUpdate();
	    }
	    return true;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    public List<Championship> loadAllChampionships() {
	List<Championship> championshipList = new ArrayList<>();
	try {
	    String sql = "SELECT * "
		    + "FROM championships ORDER BY cha_start_date DESC, cha_code";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		log.debug("loadAll: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			championshipList.add(populateChampionshipFromResultSet(rs));
		    }
		}
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return championshipList;
    }

    /**
     * Lee los datos del Campeonato de un resultset y devuelve un objeto
     * Championship
     *
     * @param rs resultSet
     * @return campeonato le�do
     */
    protected Championship populateChampionshipFromResultSet(ResultSet rs) throws SQLException {
	Championship championship = new Championship();
	championship.setChaId(rs.getInt("cha_id"));
	championship.setChaCode(rs.getString("cha_code"));
	championship.setChaDescription(rs.getString("cha_description"));
	championship.setChaStatus(rs.getInt("cha_status"));
	championship.setChaSeason(rs.getString("cha_season"));
	championship.setChaStartDate(rs.getDate("cha_start_date"));
	championship.setChaPointsWin(rs.getInt("cha_points_win"));
	championship.setChaPointsDraw(rs.getInt("cha_points_draw"));
	championship.setChaPointsLose(rs.getInt("cha_points_lose"));
	championship.setChaMaxTeams(rs.getInt("cha_max_teams"));
	return championship;
    }

    public List<SelectItem> loadAllChampionshipsItemList() {
	List<SelectItem> championshipItemList = new ArrayList<>();
	for (Championship c : loadAllChampionships()) {
	    championshipItemList.add(new SelectItem(c.getChaId(), c.getChaCode()));
	}
	return championshipItemList;
    }

    public int getChaIdOfSeason(int season, String division) {
	try {
	    int seasonone = season + 1;
	    String query = "SELECT cha_id "
		    + " FROM championships "
		    + " WHERE cha_description = 'FUT"
		    + String.valueOf(season).substring(2)
		    + "-" + String.valueOf(seasonone).substring(2) + division + "'";
	    PreparedStatement ps = conn.prepareStatement(query);
	    log.debug("getChaIdOfSeason: " + ps.toString());
	    ResultSet rs = ps.executeQuery();
	    if (rs.next()) {
		return rs.getInt("cha_id");
	    }
	} catch (SQLException ex) {
	    java.util.logging.Logger.getLogger(ChampionshipDAO.class.getName()).log(Level.SEVERE, null, ex);
	}
	return 0;
    }
}
