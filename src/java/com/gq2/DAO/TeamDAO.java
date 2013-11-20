package com.gq2.DAO;

import com.gq2.domain.Team;
import java.sql.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DAO de los Teams (Equipos)
 *
 */
public class TeamDAO implements InjectableDAO {

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
     * Devuelve el Team seg�n el id
     *
     * @param id	Identifier
     *
     * @return Team si lo encuentra o null si no
     */
    public Team load(int id) {
	try {
	    return load(conn, id);
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    protected Team load(Connection conn, int id) throws SQLException {
	Team team = null;
	String query = "SELECT * FROM teams WHERE tea_id = ?";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, id);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    team = populateTeamFromResultSet(rs);
		}
	    }
	}
	return team;
    }

    /**
     * Guardar un nuevo Team (Equipo).
     *
     * @param team Equipo que se va a guardar.
     * @return id del equipo creado. 0 si no consigue salvarlo en la BD.
     */
    public int save(Team team) {
	try {
	    String sql = "INSERT INTO teams (tea_code, tea_name, "
		    + "tea_status, tea_rating) "
		    + "VALUES (?,?,?,?)";
	    int identifierGenerated;
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setString(1, team.getTeaCode());
		ps.setString(2, team.getTeaName());
		ps.setInt(3, team.getTeaStatus());
		ps.setInt(4, team.getTeaRating());
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
     * Update team
     *
     * @param team Team to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean update(Team team) {
	try {
	    String sql = "UPDATE teams SET "
		    + "tea_code = ?, tea_name = ?, "
		    + "tea_status = ?, tea_rating = ? " + "WHERE tea_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setString(1, team.getTeaCode());
		ps.setString(2, team.getTeaName());
		ps.setInt(3, team.getTeaStatus());
		ps.setInt(4, team.getTeaRating());
		ps.setInt(5, team.getTeaId());
		log.debug("update: " + ps.toString());
		ps.executeUpdate();
		return true;
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    /**
     * Delete team
     *
     * @param team Team to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean delete(Team team) {
	try {
	    String sql = "DELETE FROM teams WHERE tea_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, team.getTeaId());
		log.debug("delete: " + ps.toString());
		ps.executeUpdate();
	    }
	    return true;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    //TODO Pendiente de programacion los dos siguientes metodos    
    /**
     * ------------------------------------------------------------- Returns all
     * Teams in the Team model
     */
    public List<Team> loadAllTeams() {
	List<Team> teamList = new ArrayList<>();
	try {
	    String sql = "SELECT * FROM teams ORDER BY tea_name";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		log.debug("loadAll: " + ps.toString());
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
		    teamList.add(populateTeamFromResultSet(rs));
		}
		rs.close();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return teamList;

    }

    public List<Team> loadTeamsInChampionship(Integer chaId) {
	List<Team> teamList = new ArrayList<>();
	String sql = "SELECT * "
		+ " FROM enrolledTeams, teams "
		+ " WHERE ent_cha_id = ? "
		+ " AND tea_id = ent_tea_id "
		+ " ORDER BY tea_name";
	try {
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, chaId);
	    log.debug("loadTeamsInChampionship: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		while (rs.next()) {
		    teamList.add(populateTeamFromResultSet(rs));
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return teamList;
    }

    /**
     *
     * @param sqlWhereClause
     * @return
     * @throws SQLException
     */
    public List<Team> readAllTeams(String sqlWhereClause) throws SQLException {
	Connection connection = null;
	//** crear la frase SELECT SQL
	String request = "SELECT * FROM EQUIPOS " + sqlWhereClause;
	List<Team> aList = new ArrayList<>();
	return null;
    }

    /**
     * Lee los datos del Team (Equipo) de un resultset
     *
     * @param rs resultSet
     * @return campeonato le�do
     */
    protected Team populateTeamFromResultSet(ResultSet rs) throws SQLException {
	Team team = new Team();
	team.setTeaId(rs.getInt("tea_id"));
	team.setTeaCode(rs.getString("tea_code"));
	team.setTeaStatus(rs.getInt("tea_status"));
	team.setTeaName(rs.getString("tea_name"));
	team.setTeaRating(rs.getInt("tea_rating"));
	return team;
    }
}