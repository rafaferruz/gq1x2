package com.gq2.DAO;

import com.gq2.beans.EnrolledTeamBean;
import com.gq2.domain.EnrolledTeam;
import com.gq2.domain.EnrolledTeam;
import java.util.Date;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DAO de los EnrolledTeams (Resultados)
 *
 */
public class EnrolledTeamDAO implements InjectableDAO {

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
     * Devuelve el EnrolledTeam seg�n el id
     *
     * @param id	Identifier
     *
     * @return EnrolledTeam si lo encuentra o null si no
     */
    public EnrolledTeam load(int id) {
	try {
	    return load(conn, id);
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    protected EnrolledTeam load(Connection conn, int id) throws SQLException {
	EnrolledTeam enrolledTeam = null;
	String query = "SELECT * FROM enrolledTeams WHERE ent_id = ?";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, id);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    enrolledTeam = populateEnrolledTeamFromResultSet(rs);
		}
	    }
	}
	return enrolledTeam;
    }

    /**
     * Guardar un nuevo EnrolledTeam (Equipo).
     *
     * @param enrolledTeam Equipo que se va a guardar.
     * @return id del equipo creado. 0 si no consigue salvarlo en la BD.
     */
    public int save(EnrolledTeam enrolledTeam) {
	try {
	    String sql = "INSERT INTO enrolledTeams (ent_cha_id, ent_tea_id, ent_number ) "
		    + "VALUES (?,?,?)";
	    int identifierGenerated;
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setInt(1, enrolledTeam.getEntChaId());
		ps.setInt(2, enrolledTeam.getEntTeaId());
		ps.setInt(3, enrolledTeam.getEntNumber());
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
     * Update enrolledTeam
     *
     * @param enrolledTeam EnrolledTeam to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean updateEnrolledTeam(EnrolledTeam enrolledTeam) {
	try {
	    String sql = "UPDATE enrolledTeams SET "
		    + "ent_cha_id = ?, ent_tea_id = ?, "
		    + "ent_number = ? " + " WHERE ent_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, enrolledTeam.getEntChaId());
		ps.setInt(2, enrolledTeam.getEntTeaId());
		ps.setInt(3, enrolledTeam.getEntNumber());
		ps.setInt(4, enrolledTeam.getEntId());
		log.debug("update: " + ps.toString());
		ps.executeUpdate();
		return true;
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
    public void saveEnrolledTeamList(List<EnrolledTeam> enrolledTeamList) {
	for (EnrolledTeam enrolledTeam:enrolledTeamList){
	    save(enrolledTeam);
	}
    }

    /**
     * Delete enrolledTeam
     *
     * @param enrolledTeam EnrolledTeam to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean delete(EnrolledTeam enrolledTeam) {
	try {
	    String sql = "DELETE FROM enrolledTeams WHERE id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, enrolledTeam.getEntId());
		log.debug("delete: " + ps.toString());
		ps.executeUpdate();
	    }
	    return true;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }
    public boolean delete(int entChaId, Integer entTeaId) {
	try {
	    String sql = "DELETE FROM enrolledTeams WHERE ent_cha_id = ? AND ent_tea_id = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, entChaId);
		ps.setInt(2, entTeaId);
		log.debug("delete: " + ps.toString());
		ps.executeUpdate();
	    }
	    return true;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    public List<EnrolledTeamBean> loadChampionshipEnrolledTeams(int chaId) {
	List<EnrolledTeamBean> enrolledTeamBeanList = new ArrayList<>();
	String query = "SELECT team.tea_name AS team_name, ent.* "
		+ " FROM enrolledTeams AS ent, teams AS team "
		+ " WHERE ent.ent_cha_id = ? "
		+ " AND team.tea_id = ent.ent_tea_id "
		+ " ORDER BY team.tea_name ";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, chaId);
	    log.debug("loadChampionshipEnrolledTeams: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		while ( rs.next()) {
		    enrolledTeamBeanList.add(populateEnrolledTeamBeanFromResultSet(rs));
		}
	    }
	} catch (SQLException ex) {
	    log.error(ex);
	}
	return enrolledTeamBeanList;
    }

    /**
     * Actualiza la fecha de los resultados de una determinada ronda
     *
     * @param enrolledTeam EnrolledTeam to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean updateEnrolledTeamsDate(int chaId, int round, Date date) {
	try {
	    String sql = "UPDATE enrolledTeams SET "
		    + " ent_date = ?"
		    + " WHERE ent_chaId = ? AND ent_round = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setDate(1, new java.sql.Date(date.getTime()));
		ps.setInt(2, chaId);
		ps.setInt(3, round);
		log.debug("updateEnrolledTeamDate: " + ps.toString());
		ps.executeUpdate();
		return true;
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    /**
     * Lee los datos del EnrolledTeam (Equipo Inscrito) de un resultset
     *
     * @param rs resultSet
     * @return campeonato le�do
     */
    protected EnrolledTeam populateEnrolledTeamFromResultSet(ResultSet rs) throws SQLException {
	EnrolledTeam enrolledTeam = new EnrolledTeam();
	enrolledTeam.setEntId(rs.getInt("ent_id"));
	enrolledTeam.setEntChaId(rs.getInt("ent_cha_id"));
	enrolledTeam.setEntTeaId(rs.getInt("ent_tea_id"));
	enrolledTeam.setEntNumber(rs.getInt("ent_number"));
	return enrolledTeam;
    }
    protected EnrolledTeamBean populateEnrolledTeamBeanFromResultSet(ResultSet rs) throws SQLException {
	EnrolledTeamBean enrolledTeamBean = new EnrolledTeamBean();
	enrolledTeamBean.setEntId(rs.getInt("ent_id"));
	enrolledTeamBean.setEntChaId(rs.getInt("ent_cha_id"));
	enrolledTeamBean.setEntTeaId(rs.getInt("ent_tea_id"));
	enrolledTeamBean.setEntNumber(rs.getInt("ent_number"));
	enrolledTeamBean.setEntTeamName(rs.getString("team_name"));
	return enrolledTeamBean;
    }
}
