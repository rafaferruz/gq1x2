/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gq2.DAO;

/**
 *
 * @author ESTHER
 */
import java.io.Serializable;
import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import javax.servlet.http.HttpServlet;

public class JDBCHelper extends HttpServlet implements Serializable {

    //jndiName is the JNDI name of the data source to use to get a connection.
    private String jndiName = "GRANQ";
    private static int counterConnection;
    private static boolean showCounterBdConnections;

    // Creates new JDBCHelper
    public JDBCHelper() {
	if (this.getInitParameter("SHOW_COUNTER_BD_CONNECTIONS") == null) {
	    showCounterBdConnections = false;
	} else {
	    showCounterBdConnections = Boolean.parseBoolean(this.getInitParameter("SHOW_COUNTER_BD_CONNECTIONS"));
	}
    }

    public JDBCHelper(String jndiName) {
	this.jndiName = jndiName;
    }

    // @param jndiName JNDI name of the data source to use to 
    //  get a connection
    public Connection getConnection() throws SQLException, NamingException {
	Context initCtx = null;
	try {
	    // Obtain the initial JNDI context
	    initCtx = new InitialContext();

	    // Perform JNDI lookup to obtain resource manager connection factory
	    DataSource ds = (javax.sql.DataSource) initCtx.lookup("java:comp/env/jdbc/" + jndiName);

	    counterConnection++;
	    if (showCounterBdConnections) {
		System.out.println("Inicia conexión. Total de conexiones abiertas: " + counterConnection);
	    }
	    // Invoke factory to obtain a connection.
	    return ds.getConnection();
	} finally {
	    // Don't forget to close the naming context
	    if (initCtx != null) {
		initCtx.close();
	    }
	}
    }

    // Always cleans up, even if it encounters a SQL exception
    public void cleanup(Connection databaseConnection,
	    Statement statement1,
	    Statement statement2) throws SQLException {
	try {
	    // Close the database connection and statement
	    if (statement1 != null) {
		statement1.close();
	    }
	    if (statement2 != null) {
		statement2.close();
	    }
	} finally {
	    // Make sure we always try to close the connection, even
	    // if something went wrong trying to close a statement
	    if (databaseConnection != null) {
		databaseConnection.close();
		counterConnection--;
		if (showCounterBdConnections) {
		    System.out.println("Cierra conexión. Total de conexiones abiertas: " + counterConnection);
		}
	    }
	}
    }
}
