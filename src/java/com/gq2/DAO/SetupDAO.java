package com.gq2.DAO;

import com.gq2.domain.Setup;
import java.sql.*;
import java.util.*;
import javax.faces.model.SelectItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * DAO de los Setups
 */
public class SetupDAO implements InjectableDAO {

    private static Logger log = LogManager.getLogger(SetupDAO.class.getName());
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
     * Devuelve el Setup seg�n el id
     *
     * @param id	Identifier
     *
     * @return Setup si lo encuentra o null si no
     */
    public Setup load(int id) {
	try {
	    return load(conn, id);
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    protected Setup load(Connection conn, int id) throws SQLException {
	Setup setup = null;
	String query = "SELECT * FROM setup WHERE stp_id = ?";
	try (PreparedStatement ps = conn.prepareStatement(query)) {
	    ps.setInt(1, id);
	    log.debug("load: " + ps.toString());
	    try (ResultSet rs = ps.executeQuery()) {
		if (rs.next()) {
		    setup = populateSetupFromResultSet(rs);
		}
	    }
	}
	return setup;
    }

    /**
     * Guardar un nuevo Setup.
     *
     * @param setup Setup que se va a guardar.
     * @return id del setup creado. 0 si no consigue salvarlo en la BD.
     */
    public int save(Setup setup) {
	try {
	    String sql;
	    sql = "INSERT INTO setup (stp_section, stp_parameter, stp_value) "
		    + "VALUES (?,?,?)";
	    int identifierGenerated;
	    try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
		ps.setString(1, setup.getStpSection());
		ps.setString(2, setup.getStpParameter());
		ps.setString(3, setup.getStpValue());
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
     * Update setup
     *
     * @param setup Setup to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean update(Setup setup) {
	try {
	    String sql = "UPDATE setup SET "
		    + "stp_section = ?, stp_parameter = ?, stp_value = ? " + "WHERE stp_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setString(1, setup.getStpSection());
		ps.setString(2, setup.getStpParameter());
		ps.setString(3, setup.getStpValue());
		ps.setInt(4, setup.getStpId());
		log.debug("update: " + ps.toString());
		ps.executeUpdate();
		return true;
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    /**
     * Delete setup
     *
     * @param setup Setup to be deleted
     * @return true si se realiza un borrado exitoso
     */
    public boolean delete(Setup setup) {
	try {
	    String sql = "DELETE FROM setup WHERE stp_id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		ps.setInt(1, setup.getStpId());
		log.debug("delete: " + ps.toString());
		ps.executeUpdate();
	    }
	    return true;
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    public List<Setup> loadAllSetups() {
	List<Setup> setupList = new ArrayList<>();
	try {
	    String sql = "SELECT * "
		    + "FROM setup ORDER BY stp_section, stp_parameter";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		log.debug("loadAll: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			setupList.add(populateSetupFromResultSet(rs));
		    }
		}
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return setupList;
    }

    public Map<String, String> loadPrinterParams(String printerName) {
	Map<String, String> printerParamsMap = new HashMap<>();
	try {
	    String sql = "SELECT * "
		    + "FROM setup WHERE stp_section = ?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		    ps.setString(1, printerName);
		log.debug("loadPrinterParams: " + ps.toString());
		try (ResultSet rs = ps.executeQuery()) {
		    while (rs.next()) {
			printerParamsMap.put(rs.getString("stp_parameter"), rs.getString("stp_value"));
		    }
		}
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

	return printerParamsMap;
    }

    public void saveOrUpdatePrinterParams(String printerName, Map<String, String> printerParamsMap) {
	String sql;
	for (String paramName : printerParamsMap.keySet()) {
	    try {
		sql = "INSERT INTO setup (stp_section, stp_parameter, stp_value) "
			+ " VALUES (?,?,?)"
			+ " ON DUPLICATE KEY UPDATE stp_value = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
		    ps.setString(1, printerName);
		    ps.setString(2, paramName);
		    ps.setString(3, printerParamsMap.get(paramName));
		    ps.setString(4, printerParamsMap.get(paramName));
		    log.debug("savePrinterParams: " + ps.toString());
		    ps.executeUpdate();
		}
	    } catch (SQLException ex) {
		throw new RuntimeException(ex);
	    }
	}
    }

    /**
     * Lee los datos del Setup de un resultset y devuelve un objeto Setup
     *
     * @param rs resultSet
     * @return setup le�do
     */
    protected Setup populateSetupFromResultSet(ResultSet rs) throws SQLException {
	Setup setup = new Setup();
	setup.setStpId(rs.getInt("stp_id"));
	setup.setStpSection(rs.getString("stp_section"));
	setup.setStpParameter(rs.getString("stp_parameter"));
	setup.setStpValue(rs.getString("stp_value"));
	return setup;
    }

    public List<SelectItem> loadAllSetupSectionsItemList() {
	List<SelectItem> setupItemList = new ArrayList<>();
	for (Setup c : loadAllSetups()) {
	    setupItemList.add(new SelectItem(c.getStpId(), c.getStpSection()));
	}
	return setupItemList;
    }
}
