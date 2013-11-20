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
public class jugsBean implements Serializable {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "GRANQ";
    private Integer jug_id;
    private Integer jug_estado = 0;
    private String jug_codigo;
    private String jug_nombre;
    private Integer jug_rating;
    private String ordenejecutar;
    private boolean registered;
    private JDBCHelper jdbcHelper;

    public jugsBean() {
	jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public void setJug_id(Integer value) {
	this.jug_id = value;
    }

    public Integer getJug_id() {
	return jug_id;
    }

    public void setJug_estado(Integer value) {
	this.jug_estado = value;
    }

    public Integer getJug_estado() {
	return jug_estado;
    }

    public void setJug_codigo(String value) {
	this.jug_codigo = value;
    }

    public String getJug_codigo() {
	return jug_codigo;
    }

    public void setJug_nombre(String value) {
	this.jug_nombre = value;
    }

    public String getJug_nombre() {
	return jug_nombre;
    }

    public void setJug_rating(Integer value) {
	this.jug_rating = value;
    }

    public Integer getJug_rating() {
	return jug_rating;
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
		"INSERT INTO JUGADORES VALUES( 0, ?, ?, ?, ?)";
	String selectCustomerStr =
		"SELECT MAX(JUG_ID) "
		+ " FROM JUGADORES ";

	PreparedStatement insertStatement = null;
	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    insertStatement = connection.prepareStatement(insertStatementStr);

//            insertStatement.setInt(1, jug_id);
	    insertStatement.setString(1, jug_codigo);
	    insertStatement.setInt(2, jug_estado);
	    insertStatement.setString(3, jug_nombre);
	    insertStatement.setInt(4, jug_rating);

	    insertStatement.executeUpdate();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
//            selectStatement.setInt(1, jug_id);

	    ResultSet rs = selectStatement.executeQuery();

	    if (rs.next()) {
		setJug_id(rs.getInt(1));
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
		+ " FROM JUGADORES "
		+ "WHERE JUG_ID = ?";

	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
	    selectStatement.setInt(1, jug_id);

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
		+ " FROM JUGADORES "
		+ "ORDER BY JUG_ID DESC LIMIT 1";

	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
//            selectStatement.setInt(1, jug_id);

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
		"DELETE FROM JUGADORES "
		+ "WHERE JUG_ID = ?";

	PreparedStatement deleteStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    // Now verify if the customer is registered or not.
	    deleteStatement = connection.prepareStatement(deleteCustomerStr);
	    deleteStatement.setInt(1, jug_id);

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
		+ " FROM JUGADORES "
		+ "WHERE JUG_ID < ? ORDER BY JUG_ID DESC LIMIT 1";

	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
	    selectStatement.setInt(1, jug_id);

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
		+ " FROM JUGADORES "
		+ "WHERE JUG_ID > ? ORDER BY JUG_ID LIMIT 1";

	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
	    selectStatement.setInt(1, jug_id);

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
		"UPDATE JUGADORES SET "
		+ "JUG_ESTADO = ?,"
		+ "JUG_CODIGO = ?, "
		+ "JUG_NOMBRE = ?,"
		+ "JUG_RATING = ? "
		+ "WHERE JUG_id = ?";
	String selectCustomerStr =
		"SELECT * "
		+ " FROM JUGADORES "
		+ "WHERE JUG_ID = ?";

	PreparedStatement insertStatement = null;
	PreparedStatement selectStatement = null;

	try {
	    connection = jdbcHelper.getConnection();

	    insertStatement = connection.prepareStatement(insertStatementStr);

//            insertStatement.setInt(1, jug_id);

	    insertStatement.setInt(1, jug_estado);
	    insertStatement.setString(2, jug_codigo);
	    insertStatement.setString(3, jug_nombre);
	    insertStatement.setInt(4, jug_rating);
	    insertStatement.setInt(5, jug_id);
	    insertStatement.executeUpdate();

	    // Now verify if the customer is registered or not.
	    selectStatement = connection.prepareStatement(selectCustomerStr);
//            selectStatement.setInt(1, jug_id);
	    selectStatement.setInt(1, jug_id);
	    ResultSet rs = selectStatement.executeQuery();

	    if (rs.next()) {
		setJug_id(rs.getInt(1));
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
	setJug_id(rs.getInt("JUG_ID"));
	setJug_estado(rs.getInt("JUG_ESTADO"));
	setJug_codigo(rs.getString("JUG_CODIGO"));
	setJug_nombre(rs.getString("JUG_NOMBRE"));
	setJug_rating(rs.getInt("JUG_RATING"));

    }
}
