/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gq2.beans;

import com.gq2.DAO.JDBCHelper;
import java.io.Serializable;
import java.sql.*;

/**
 * @author ESTHER
 */
public class inesBean implements Serializable {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "GRANQ";
    private Integer ine_id;
    private Integer ine_cto_id;
    private Integer ine_equ_id;
    private Integer ine_dorsal;
    private String ordenejecutar;
    private boolean registered;
    private JDBCHelper jdbcHelper;

    public inesBean() {
	jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public void setIne_id(Integer value) {
	this.ine_id = value;
    }

    public Integer getIne_id() {
	return ine_id;
    }

    public void setIne_cto_id(Integer value) {
	this.ine_cto_id = value;
    }

    public Integer getIne_cto_id() {
	return ine_cto_id;
    }

    public void setIne_equ_id(Integer value) {
	this.ine_equ_id = value;
    }

    public Integer getIne_equ_id() {
	return ine_equ_id;
    }

    public void setIne_dorsal(Integer value) {
	this.ine_dorsal = value;
    }

    public Integer getIne_dorsal() {
	return ine_dorsal;
    }

    public void setOrdenejecutar(String value) {
	this.ordenejecutar = value;
    }

    public String getOrdenejecutar() {
	return ordenejecutar;
    }

    public boolean isRegistered() {
	return registered;
    }

    public void registerContenido() throws SQLException,
	    javax.naming.NamingException {

	Connection connection = null;
	String insertStatementStr =
		"INSERT INTO INSCRITOSEQUIPOS VALUES( 0, ?, ?, ?)";
	String selectCustomerStr =
		"SELECT MAX(INE_ID) "
		+ " FROM INSCRITOSEQUIPOS ";

	PreparedStatement insertStatement = null;
	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    insertStatement = connection.prepareStatement(insertStatementStr);

//            insertStatement.setInt(1, ine_id);
	    insertStatement.setInt(1, ine_cto_id);
	    insertStatement.setInt(2, ine_equ_id);
	    insertStatement.setInt(3, ine_dorsal);

	    insertStatement.executeUpdate();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
//            selectStatement.setInt(1, ine_id);

	    ResultSet rs = selectStatement.executeQuery();

	    if (rs.next()) {
		setIne_id(rs.getInt(1));
		// El contenido se ha registrado correctamente
		registered = true;
	    } else {
		registered = false;
	    }
	    rs.close();
	} finally {
	    jdbcHelper.cleanup(connection, selectStatement, insertStatement);
	}
    }

    public void retrieveContenido() throws SQLException,
	    javax.naming.NamingException {
	Connection connection = null;

	String selectCustomerStr =
		"SELECT * "
		+ " FROM INSCRITOSEQUIPOS "
		+ "WHERE INE_ID = ?";

	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
	    selectStatement.setInt(1, ine_id);

	    ResultSet rs = selectStatement.executeQuery();

	    if (rs.next()) {
		setPropiedades(rs);

		// The customer was registered - we can go straight to the
		// response page
		registered = true;
	    } else {
		registered = false;
	    }
	    rs.close();
	} finally {
	    jdbcHelper.cleanup(connection, selectStatement, null);
	}
    }

    public void retrieveLastContenido() throws SQLException,
	    javax.naming.NamingException {
	Connection connection = null;

	String selectCustomerStr =
		"SELECT * "
		+ " FROM INSCRITOSEQUIPOS "
		+ "ORDER BY INE_ID DESC LIMIT 1";

	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
//            selectStatement.setInt(1, ine_id);

	    ResultSet rs = selectStatement.executeQuery();

	    if (rs.next()) {
		setPropiedades(rs);

		// The customer was registered - we can go straight to the
		// response page
		registered = true;
	    } else {
		registered = false;
	    }
	    rs.close();
	} finally {
	    jdbcHelper.cleanup(connection, selectStatement, null);
	}
    }

    public void deleteContenido() throws SQLException,
	    javax.naming.NamingException {
	Connection connection = null;

	String deleteCustomerStr =
		"DELETE FROM INSCRITOSEQUIPOS "
		+ "WHERE INE_ID = ?";

	PreparedStatement deleteStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    // Now verify if the customer is registered or not.
	    deleteStatement = connection.prepareStatement(deleteCustomerStr);
	    deleteStatement.setInt(1, ine_id);

	    deleteStatement.executeUpdate();
	    retrieveContenido();
	} finally {
	    jdbcHelper.cleanup(connection, deleteStatement, null);
	}
    }

    public void retrievePreviousContenido() throws SQLException,
	    javax.naming.NamingException {
	Connection connection = null;

	String selectCustomerStr =
		"SELECT * "
		+ " FROM INSCRITOSEQUIPOS "
		+ "WHERE INE_ID < ? ORDER BY INE_ID DESC LIMIT 1";

	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
	    selectStatement.setInt(1, ine_id);

	    ResultSet rs = selectStatement.executeQuery();

	    if (rs.next()) {
		setPropiedades(rs);

		// The customer was registered - we can go straight to the
		// response page
		registered = true;
	    } else {
		registered = false;
	    }
	    rs.close();
	} finally {
	    jdbcHelper.cleanup(connection, selectStatement, null);
	}
    }

    public void retrieveNextContenido() throws SQLException,
	    javax.naming.NamingException {
	Connection connection = null;

	String selectCustomerStr =
		"SELECT * "
		+ " FROM INSCRITOSEQUIPOS "
		+ "WHERE INE_ID > ? ORDER BY INE_ID LIMIT 1";

	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
	    selectStatement.setInt(1, ine_id);

	    ResultSet rs = selectStatement.executeQuery();

	    if (rs.next()) {
		setPropiedades(rs);

		// The customer was registered - we can go straight to the
		// response page
		registered = true;
	    } else {
		registered = false;
	    }
	    rs.close();
	} finally {
	    jdbcHelper.cleanup(connection, selectStatement, null);
	}
    }

    public void updateContenido() throws SQLException,
	    javax.naming.NamingException {

	Connection connection = null;
	String insertStatementStr =
		"UPDATE INSCRITOSEQUIPOS SET "
		+ "INE_CTO_ID = ?, "
		+ "INE_EQU_ID = ?,"
		+ "INE_DORSAL = ? "
		+ "WHERE INE_ID = ?";
	String selectCustomerStr =
		"SELECT * "
		+ " FROM INSCRITOSEQUIPOS "
		+ "WHERE INE_ID = ?";

	PreparedStatement insertStatement = null;
	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    insertStatement = connection.prepareStatement(insertStatementStr);

//            insertStatement.setInt(1, ine_id);

	    insertStatement.setInt(1, ine_cto_id);
	    insertStatement.setInt(2, ine_equ_id);
	    insertStatement.setInt(3, ine_dorsal);
	    insertStatement.setInt(4, ine_id);
	    insertStatement.executeUpdate();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
//            selectStatement.setInt(1, ine_id);
	    selectStatement.setInt(1, ine_id);
	    ResultSet rs = selectStatement.executeQuery();

	    if (rs.next()) {
		setIne_id(rs.getInt(1));
		// El contenido se ha registrado correctamente
		registered = true;
	    } else {
		registered = false;
	    }
	    rs.close();
	} finally {
	    jdbcHelper.cleanup(connection, selectStatement, insertStatement);
	}
    }

    public void setPropiedades(ResultSet rs) throws SQLException {
	setIne_id(rs.getInt("INE_ID"));
	setIne_cto_id(rs.getInt("INE_CTO_ID"));
	setIne_equ_id(rs.getInt("INE_EQU_ID"));
	setIne_dorsal(rs.getInt("INE_DORSAL"));

    }
}
