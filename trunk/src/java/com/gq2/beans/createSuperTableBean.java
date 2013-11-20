/*
 * FcreateSuperTableBean.java
 *
 * Created on 15-ago-2009, 23:58:18
 */
package com.gq2.beans;

import com.gq2.DAO.JDBCHelper;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;

/**
 * <p>Application scope data bean for your application. Create properties here
 * to represent cached data that should be made available to all users and pages
 * in the application.</p>
 *
 * <p>An instance of this class will be created for you automatically, the first
 * time your application evaluates a value binding expression or method binding
 * expression that references a managed bean using this class.</p>
 *
 * @author ESTHER
 */
public class createSuperTableBean implements Serializable {

    // JNDI name of the data source this class requires
    private static final String DATA_SOURCE_NAME = "GRANQ";
    private JDBCHelper jdbcHelper;
    private PreparedStatement selectStatement = null;
    private PreparedStatement insertStatement = null;
    private List<Map> results = new LinkedList();
    private Integer ctoId = null;
    private Integer ronda = null;
    private Integer rondaAnterior = null;
    private Integer rondafinal = null;

    public createSuperTableBean() {
	jdbcHelper = new JDBCHelper(DATA_SOURCE_NAME);
    }

    public synchronized void create(Integer paramCtoId, Integer paramRonda, Integer paramRondaFinal, Integer paramModo) throws NamingException, SQLException {
	// Abre la base de datos

	Connection connection = jdbcHelper.getConnection();

	ctoId = paramCtoId;

	if (paramModo == 2 || paramModo == 3) {
// Procesa todos los campeonatos sucesivamente
	    String selectStr =
		    "SELECT CTO_ID, CTO_DESCRIPCION FROM CAMPEONATOS ORDER BY CTO_FECHAINICIO";
	    selectStatement =
		    connection.prepareStatement(selectStr);
	    ResultSet rsCto = selectStatement.executeQuery();

	    if (paramModo == 2) {
// Bucle para avanzar campeonatos sin procesar hasta encontrar el campeonato 'desde'
		while (rsCto.next()) {
		    if (rsCto.getInt("CTO_ID") == ctoId) {
			rsCto.previous();
			break;
		    }
		}
	    }
	    while (rsCto.next()) {

		selectStr =
			"SELECT DISTINCT(RES_RONDA) FROM RESULTADOS "
			+ "WHERE RES_CTO_ID = " + rsCto.getInt("CTO_ID")
			+ " ORDER BY RES_RONDA";
		selectStatement =
			connection.prepareStatement(selectStr);
		ResultSet rsRes = selectStatement.executeQuery();

		while (rsRes.next()) {
		    ctoId = rsCto.getInt("CTO_ID");
		    ronda = rsRes.getInt("RES_RONDA");
		    procesaCampeonato();
		}
		rsRes.close();
		System.out.println("Generación Clasificación finalizada. Campeonato " + rsCto.getString("CTO_DESCRIPCION") + "  Ronda " + ronda.toString());
	    }
	    rsCto.close();

	} else {
// Solamente procesa el campeonato seleccionado en generarClasificaciones.jspf            
	    ctoId = paramCtoId;
	    ronda = paramRonda;
	    rondafinal = paramRondaFinal;
	    for (ronda = ronda + 0; ronda <= rondafinal; ronda++) {
		procesaCampeonato();
	    }
	}
	jdbcHelper.cleanup(connection, selectStatement, null);

    }

    private void procesaCampeonato() throws SQLException, NamingException {


	deleteSuperTableRondaActual();
// Se cargan las variables de colecciones con los datos de Resultados y Clasificaciones            
	iniciarColecciones();

	if (results.size() > 0) {
	    synchronized (this) {
		generarSuperTableRondaActual();
	    }
	} else {
	    // Final por no existir resultados
	}

    }

    private void deleteSuperTableRondaActual() throws SQLException, NamingException {
// Se eliminan los registros que existan de la clasificación de la ronda actual
	String selectStr =
		"DELETE FROM SUPERTABLA WHERE STAB_CTO_ID = "
		+ ctoId.toString() + " AND STAB_RONDA = "
		+ ronda.toString();

	Connection connection = jdbcHelper.getConnection();

	selectStatement =
		connection.prepareStatement(selectStr);
	selectStatement.executeUpdate();
	jdbcHelper.cleanup(connection, selectStatement, null);

    }

    private void generarSuperTableRondaActual() throws SQLException, NamingException {


	for (Map<String, Object> r : results) {

	    String values = "STAB_ID = 0"
		    + ",STAB_CTO_ID = " + ctoId.toString()
		    + ",STAB_RONDA = " + ronda.toString()
		    + ",STAB_FECHA = '" + r.get("RES_FECHA").toString() + "'"
		    + ",STAB_RES_ID = " + r.get("RES_ID").toString()
		    + ",STAB_RES_EQU1_ID = " + r.get("RES_EQU1_ID").toString()
		    + ",STAB_RES_EQU2_ID = " + r.get("RES_EQU2_ID").toString()
		    + ",STAB_RES_RESUL1 = " + r.get("RES_RESUL1").toString()
		    + ",STAB_RES_RESUL2 = " + r.get("RES_RESUL2").toString();

// Bucle de actualización para elEQUIPO1
	    values = values + leerClasifRonda((Integer) r.get("RES_EQU1_ID"));
	    values = values + leerClasifRondaAnterior((Integer) r.get("RES_EQU1_ID"));
	    values = values + leerClasifGpeRonda((Integer) r.get("RES_EQU1_ID"));
	    values = values + leerClasifGpeRondaAnterior((Integer) r.get("RES_EQU1_ID"));
	    values = values + leerRat4Ronda((Integer) r.get("RES_EQU1_ID"));
	    insertSuperTableRondaActual(values);

	    values = "";
	    values = "STAB_ID = 0"
		    + ",STAB_CTO_ID = " + ctoId.toString()
		    + ",STAB_RONDA = " + ronda.toString()
		    + ",STAB_FECHA = '" + r.get("RES_FECHA").toString() + "'"
		    + ",STAB_RES_ID = " + r.get("RES_ID").toString()
		    + ",STAB_RES_EQU1_ID = " + r.get("RES_EQU1_ID").toString()
		    + ",STAB_RES_EQU2_ID = " + r.get("RES_EQU2_ID").toString()
		    + ",STAB_RES_RESUL1 = " + r.get("RES_RESUL1").toString()
		    + ",STAB_RES_RESUL2 = " + r.get("RES_RESUL2").toString();

// Bucle de actualización para elEQUIPO2
	    values = values + leerClasifRonda((Integer) r.get("RES_EQU2_ID"));
	    values = values + leerClasifRondaAnterior((Integer) r.get("RES_EQU2_ID"));
	    values = values + leerClasifGpeRonda((Integer) r.get("RES_EQU2_ID"));
	    values = values + leerClasifGpeRondaAnterior((Integer) r.get("RES_EQU2_ID"));
	    values = values + leerRat4Ronda((Integer) r.get("RES_EQU2_ID"));
	    insertSuperTableRondaActual(values);
	}
// Final de actualizacion de clasificacion 
    }

    private void iniciarColecciones() throws SQLException, NamingException {
	// Leer resultados ronda actual        
	results.clear();
	results.addAll(leerResultadosRonda(ctoId, ronda));
    }

    private void insertSuperTableRondaActual(String values) throws SQLException, NamingException {
// Se insertan los nuevos registros de clasificación a partir de la la nueva lista

	String insertStr =
		"INSERT INTO SUPERTABLA SET " + values;
	Connection connection = jdbcHelper.getConnection();

	insertStatement =
		connection.prepareStatement(insertStr);
	insertStatement.executeUpdate();
	jdbcHelper.cleanup(connection, insertStatement, null);

    }

    private List leerResultadosRonda(Integer campeonato, Integer ronda) throws SQLException, NamingException {

	String selectStr =
		"SELECT * FROM RESULTADOS WHERE RES_CTO_ID = "
		+ campeonato.toString() + " AND RES_RONDA = "
		+ ronda.toString() + " ORDER BY RES_ID";
	Map<String, Object> resultado = new HashMap<String, Object>();
	List<Map> resultados = new LinkedList<Map>();
	resultados.clear();
	Connection connection = jdbcHelper.getConnection();

	selectStatement =
		connection.prepareStatement(selectStr);
	ResultSet rs = selectStatement.executeQuery();
	while (rs.next()) {
	    resultado.clear();
	    resultado.put("RES_ID", rs.getInt("RES_ID"));
	    resultado.put("RES_CTO_ID", rs.getInt("RES_CTO_ID"));
	    resultado.put("RES_RONDA", rs.getInt("RES_RONDA"));
	    resultado.put("RES_FECHA", rs.getDate("RES_FECHA"));
	    resultado.put("RES_EQU1_ID", rs.getInt("RES_EQU1_ID"));
	    resultado.put("RES_EQU2_ID", rs.getInt("RES_EQU2_ID"));
	    resultado.put("RES_RESUL1", rs.getInt("RES_RESUL1"));
	    resultado.put("RES_RESUL2", rs.getInt("RES_RESUL2"));

	    resultados.add(new HashMap(resultado));
	}

	rs.close();
	jdbcHelper.cleanup(connection, selectStatement, null);

	return resultados;
    }

    private String leerClasifRonda(Integer equipo) throws SQLException, NamingException {

	String values = "";
	String selectStr =
		"SELECT * FROM CLASIFICACIONES WHERE CLA_CTO_ID = "
		+ ctoId.toString() + " AND CLA_RONDA = "
		+ ronda.toString() + " AND CLA_EQU_ID = "
		+ equipo.toString();

	Connection connection = jdbcHelper.getConnection();

	selectStatement =
		connection.prepareStatement(selectStr);
	ResultSet rs = selectStatement.executeQuery();

	while (rs.next()) {
	    values = ",STAB_CLA_ID = " + rs.getInt("CLA_ID")
		    + ",STAB_CLA_POSICION = " + rs.getInt("CLA_POSICION")
		    + ",STAB_CLA_EQU_ID = " + rs.getInt("CLA_EQU_ID")
		    + ",STAB_CLA_PUNTOS = " + rs.getInt("CLA_PUNTOS")
		    + ",STAB_CLA_PJT = " + rs.getInt("CLA_PJT")
		    + ",STAB_CLA_PGT = " + rs.getInt("CLA_PGT")
		    + ",STAB_CLA_PET = " + rs.getInt("CLA_PET")
		    + ",STAB_CLA_PPT = " + rs.getInt("CLA_PPT")
		    + ",STAB_CLA_GFT = " + rs.getInt("CLA_GFT")
		    + ",STAB_CLA_GCT = " + rs.getInt("CLA_GCT")
		    + ",STAB_CLA_PJC = " + rs.getInt("CLA_PJC")
		    + ",STAB_CLA_PGC = " + rs.getInt("CLA_PGC")
		    + ",STAB_CLA_PEC = " + rs.getInt("CLA_PEC")
		    + ",STAB_CLA_PPC = " + rs.getInt("CLA_PPC")
		    + ",STAB_CLA_GFC = " + rs.getInt("CLA_GFC")
		    + ",STAB_CLA_GCC = " + rs.getInt("CLA_GCC")
		    + ",STAB_CLA_PJF = " + rs.getInt("CLA_PJF")
		    + ",STAB_CLA_PGF = " + rs.getInt("CLA_PGF")
		    + ",STAB_CLA_PEF = " + rs.getInt("CLA_PEF")
		    + ",STAB_CLA_PPF = " + rs.getInt("CLA_PPF")
		    + ",STAB_CLA_GFF = " + rs.getInt("CLA_GFF")
		    + ",STAB_CLA_GCF = " + rs.getInt("CLA_GCF")
		    + ",STAB_CLA_RATING = " + rs.getInt("CLA_RATING");

	}
	rs.close();
	jdbcHelper.cleanup(connection, selectStatement, null);

	return values;
    }

    private String leerClasifRondaAnterior(Integer equipo) throws SQLException, NamingException {

	String values = "";
	rondaAnterior = ronda - 1;
	String selectStr =
		"SELECT * FROM CLASIFICACIONES WHERE CLA_CTO_ID = "
		+ ctoId.toString() + " AND CLA_RONDA = "
		+ rondaAnterior.toString() + " AND CLA_EQU_ID = "
		+ equipo.toString();

	Connection connection = jdbcHelper.getConnection();

	selectStatement =
		connection.prepareStatement(selectStr);
	ResultSet rs = selectStatement.executeQuery();

	while (rs.next()) {
	    values = ",STAB_CLAPREV_ID = " + rs.getInt("CLA_ID")
		    + ",STAB_CLAPREV_POSICION = " + rs.getInt("CLA_POSICION")
		    + ",STAB_CLAPREV_EQU_ID = " + rs.getInt("CLA_EQU_ID")
		    + ",STAB_CLAPREV_PUNTOS = " + rs.getInt("CLA_PUNTOS")
		    + ",STAB_CLAPREV_PJT = " + rs.getInt("CLA_PJT")
		    + ",STAB_CLAPREV_PGT = " + rs.getInt("CLA_PGT")
		    + ",STAB_CLAPREV_PET = " + rs.getInt("CLA_PET")
		    + ",STAB_CLAPREV_PPT = " + rs.getInt("CLA_PPT")
		    + ",STAB_CLAPREV_GFT = " + rs.getInt("CLA_GFT")
		    + ",STAB_CLAPREV_GCT = " + rs.getInt("CLA_GCT")
		    + ",STAB_CLAPREV_PJC = " + rs.getInt("CLA_PJC")
		    + ",STAB_CLAPREV_PGC = " + rs.getInt("CLA_PGC")
		    + ",STAB_CLAPREV_PEC = " + rs.getInt("CLA_PEC")
		    + ",STAB_CLAPREV_PPC = " + rs.getInt("CLA_PPC")
		    + ",STAB_CLAPREV_GFC = " + rs.getInt("CLA_GFC")
		    + ",STAB_CLAPREV_GCC = " + rs.getInt("CLA_GCC")
		    + ",STAB_CLAPREV_PJF = " + rs.getInt("CLA_PJF")
		    + ",STAB_CLAPREV_PGF = " + rs.getInt("CLA_PGF")
		    + ",STAB_CLAPREV_PEF = " + rs.getInt("CLA_PEF")
		    + ",STAB_CLAPREV_PPF = " + rs.getInt("CLA_PPF")
		    + ",STAB_CLAPREV_GFF = " + rs.getInt("CLA_GFF")
		    + ",STAB_CLAPREV_GCF = " + rs.getInt("CLA_GCF")
		    + ",STAB_CLAPREV_RATING = " + rs.getInt("CLA_RATING");

	}
	rs.close();
	jdbcHelper.cleanup(connection, selectStatement, null);

	return values;
    }

    private String leerClasifGpeRonda(Integer equipo) throws SQLException, NamingException {

	String values = "";
	String selectStr =
		"SELECT * FROM CLASIFGPE WHERE GPE_CTO_ID = "
		+ ctoId.toString() + " AND GPE_RONDA = "
		+ rondaAnterior.toString() + " AND GPE_EQU_ID = "
		+ equipo.toString();

	Connection connection = jdbcHelper.getConnection();

	selectStatement =
		connection.prepareStatement(selectStr);
	ResultSet rs = selectStatement.executeQuery();

	while (rs.next()) {
	    values = ",STAB_GPE_ID = " + rs.getInt("GPE_ID")
		    + ",STAB_GPE_POSICION = " + rs.getInt("GPE_POSICION")
		    + ",STAB_GPE_EQU_ID = " + rs.getInt("GPE_EQU_ID")
		    + ",STAB_GPE_PSG = " + rs.getInt("GPE_PSG")
		    + ",STAB_GPE_PSE = " + rs.getInt("GPE_PSE")
		    + ",STAB_GPE_PSP = " + rs.getInt("GPE_PSP")
		    + ",STAB_GPE_PSNG = " + rs.getInt("GPE_PSNG")
		    + ",STAB_GPE_PSNE = " + rs.getInt("GPE_PSNE")
		    + ",STAB_GPE_PSNP = " + rs.getInt("GPE_PSNP");
	}

	rs.close();
	jdbcHelper.cleanup(connection, selectStatement, null);

	return values;
    }

    private String leerClasifGpeRondaAnterior(Integer equipo) throws SQLException, NamingException {

	String values = "";
	rondaAnterior = ronda - 1;
	String selectStr =
		"SELECT * FROM CLASIFGPE WHERE GPE_CTO_ID = "
		+ ctoId.toString() + " AND GPE_RONDA = "
		+ ronda.toString() + " AND GPE_EQU_ID = "
		+ equipo.toString();

	Connection connection = jdbcHelper.getConnection();

	selectStatement =
		connection.prepareStatement(selectStr);
	ResultSet rs = selectStatement.executeQuery();

	while (rs.next()) {
	    values = ",STAB_GPEPREV_ID = " + rs.getInt("GPE_ID")
		    + ",STAB_GPEPREV_POSICION = " + rs.getInt("GPE_POSICION")
		    + ",STAB_GPEPREV_EQU_ID = " + rs.getInt("GPE_EQU_ID")
		    + ",STAB_GPEPREV_PSG = " + rs.getInt("GPE_PSG")
		    + ",STAB_GPEPREV_PSE = " + rs.getInt("GPE_PSE")
		    + ",STAB_GPEPREV_PSP = " + rs.getInt("GPE_PSP")
		    + ",STAB_GPEPREV_PSNG = " + rs.getInt("GPE_PSNG")
		    + ",STAB_GPEPREV_PSNE = " + rs.getInt("GPE_PSNE")
		    + ",STAB_GPEPREV_PSNP = " + rs.getInt("GPE_PSNP");
	}

	rs.close();
	jdbcHelper.cleanup(connection, selectStatement, null);

	return values;
    }

    private String leerRat4Ronda(Integer equipo) throws SQLException, NamingException {

	String values = "";
	String selectStr =
		"SELECT * FROM RATING4 WHERE RAT4_CTO_ID = "
		+ ctoId.toString() + " AND RAT4_RONDA = "
		+ ronda.toString() + " AND RAT4_EQU1_ID = "
		+ equipo.toString();

	Connection connection = jdbcHelper.getConnection();

	selectStatement =
		connection.prepareStatement(selectStr);
	ResultSet rs = selectStatement.executeQuery();

	while (rs.next()) {
	    values = ",STAB_RAT4_ID = " + rs.getInt("RAT4_ID")
		    + ",STAB_RAT4_EQU1_ID = " + rs.getInt("RAT4_EQU1_ID")
		    + ",STAB_RAT4_EQU1_PREV = " + rs.getInt("RAT4_EQU1_PREV")
		    + ",STAB_RAT4_EQU2_ID = " + rs.getInt("RAT4_EQU2_ID")
		    + ",STAB_RAT4_EQU2_PREV = " + rs.getInt("RAT4_EQU2_PREV")
		    + ",STAB_RAT4_DIF_PREV = " + rs.getInt("RAT4_DIF_PREV")
		    + ",STAB_RAT4_PROB_G = " + rs.getInt("RAT4_PROB_G")
		    + ",STAB_RAT4_PROB_E = " + rs.getInt("RAT4_PROB_E")
		    + ",STAB_RAT4_PROB_P = " + rs.getInt("RAT4_PROB_P")
		    + ",STAB_RAT4_RESUL = '" + rs.getString("RAT4_RESUL") + "'"
		    + ",STAB_RAT4_EQU1_POST = " + rs.getInt("RAT4_EQU1_POST")
		    + ",STAB_RAT4_EQU2_POST = " + rs.getInt("RAT4_EQU2_POST");
	}

	rs.close();
	jdbcHelper.cleanup(connection, selectStatement, null);

	return values;
    }
}
