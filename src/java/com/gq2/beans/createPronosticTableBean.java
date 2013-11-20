/*
 * FcreatePronosticTableBean.java
 *
 * Created on 19-ago-2009, 17:39:27
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

public class createPronosticTableBean implements Serializable {

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

    public createPronosticTableBean() {
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
		System.out.println("Generación Tabla Pronósticos finalizada. Campeonato " + rsCto.getString("CTO_DESCRIPCION") + "  Ronda " + ronda.toString());
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
	jdbcHelper.cleanup(connection, selectStatement, insertStatement);

    }

    private void procesaCampeonato() throws SQLException, NamingException {


	deletePronosticosRondaActual();
// Se cargan las variables de colecciones con los datos de Resultados y Clasificaciones            
	iniciarColecciones();

	if (results.size() > 0) {
	    synchronized (this) {
		generarPronosticosRondaActual();
	    }
	} else {
	    // Final por no existir resultados
	}
	System.out.println("Generación Clasificación finalizada. Ronda " + ronda.toString());

    }

    private void deletePronosticosRondaActual() throws SQLException, NamingException {
// Se eliminan los registros que existan de la clasificación de la ronda actual
	Connection connection = jdbcHelper.getConnection();
	String selectStr =
		"DELETE FROM PRONOSTICOS WHERE PRO_CTO_ID = "
		+ ctoId.toString() + " AND PRO_RONDA = "
		+ ronda.toString();

	selectStatement =
		connection.prepareStatement(selectStr);
	selectStatement.executeUpdate();
	jdbcHelper.cleanup(connection, selectStatement, null);

    }

    private void generarPronosticosRondaActual() throws SQLException, NamingException {


	for (Map<String, Object> r : results) {

	    String values = "PRO_ID = 0"
		    + ",PRO_CTO_ID = " + ctoId.toString()
		    + ",PRO_RONDA = " + ronda.toString()
		    + ",PRO_FECHA = '" + r.get("RES_FECHA").toString() + "'"
		    + ",PRO_RES_ID = " + r.get("RES_ID").toString()
		    + ",PRO_RES_EQU1_ID = " + r.get("RES_EQU1_ID").toString()
		    + ",PRO_RES_EQU2_ID = " + r.get("RES_EQU2_ID").toString()
		    + ",PRO_RES_RESUL1 = " + r.get("RES_RESUL1").toString()
		    + ",PRO_RES_RESUL2 = " + r.get("RES_RESUL2").toString();

// Bucle de actualización para el EQUIPO1
	    values = values + leerClasifRondaAnterior((Integer) r.get("RES_EQU1_ID"), (Integer) r.get("RES_EQU2_ID"));
	    values = values + leerClasifGpeRondaAnterior((Integer) r.get("RES_EQU1_ID"), (Integer) r.get("RES_EQU2_ID"));
	    values = values + leerRat4Ronda((Integer) r.get("RES_EQU1_ID"));
	    insertPronosticosRondaActual(values);

	}
// Final de actualizacion de clasificacion 
    }

    private void iniciarColecciones() throws SQLException, NamingException {
	// Leer resultados ronda actual        
	results.clear();
	results.addAll(leerResultadosRonda(ctoId, ronda));
    }

    private void insertPronosticosRondaActual(String values) throws SQLException, NamingException {
// Se insertan los nuevos registros de clasificación a partir de la la nueva lista
	Connection connection = jdbcHelper.getConnection();

	String insertStr =
		"INSERT INTO PRONOSTICOS SET " + values;
	insertStatement =
		connection.prepareStatement(insertStr);
	insertStatement.executeUpdate();
	jdbcHelper.cleanup(connection, insertStatement, null);

    }

    private List leerResultadosRonda(Integer campeonato, Integer ronda) throws SQLException, NamingException {

	Connection connection = jdbcHelper.getConnection();
	String selectStr =
		"SELECT * FROM RESULTADOS WHERE RES_CTO_ID = "
		+ campeonato.toString() + " AND RES_RONDA = "
		+ ronda.toString() + " ORDER BY RES_ID";
	Map<String, Object> resultado = new HashMap<String, Object>();
	List<Map> resultados = new LinkedList<Map>();
	resultados.clear();
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

    private String leerClasifRondaAnterior(Integer equipo1, Integer equipo2) throws SQLException, NamingException {

	Connection connection = jdbcHelper.getConnection();
	String values = "";
	rondaAnterior = ronda - 1;
	String selectStr =
		"SELECT * FROM CLASIFICACIONES WHERE CLA_CTO_ID = "
		+ ctoId.toString() + " AND CLA_RONDA = "
		+ rondaAnterior.toString() + " AND CLA_EQU_ID = "
		+ equipo1.toString();

	selectStatement =
		connection.prepareStatement(selectStr);
	ResultSet rs = selectStatement.executeQuery();

	while (rs.next()) {
	    values = ",PRO_CLAPREV1_ID = " + rs.getInt("CLA_ID")
		    + ",PRO_CLAPREV1_PJT = " + rs.getInt("CLA_PJT")
		    + ",PRO_CLAPREV1_PGT = " + rs.getInt("CLA_PGT")
		    + ",PRO_CLAPREV1_PET = " + rs.getInt("CLA_PET")
		    + ",PRO_CLAPREV1_PPT = " + rs.getInt("CLA_PPT")
		    + ",PRO_CLAPREV1_GFT = " + rs.getInt("CLA_GFT")
		    + ",PRO_CLAPREV1_GCT = " + rs.getInt("CLA_GCT")
		    + ",PRO_CLAPREV1_PJC = " + rs.getInt("CLA_PJC")
		    + ",PRO_CLAPREV1_PGC = " + rs.getInt("CLA_PGC")
		    + ",PRO_CLAPREV1_PEC = " + rs.getInt("CLA_PEC")
		    + ",PRO_CLAPREV1_PPC = " + rs.getInt("CLA_PPC")
		    + ",PRO_CLAPREV1_GFC = " + rs.getInt("CLA_GFC")
		    + ",PRO_CLAPREV1_GCC = " + rs.getInt("CLA_GCC");

	}
	rs.close();

	selectStr =
		"SELECT * FROM CLASIFICACIONES WHERE CLA_CTO_ID = "
		+ ctoId.toString() + " AND CLA_RONDA = "
		+ rondaAnterior.toString() + " AND CLA_EQU_ID = "
		+ equipo2.toString();

	selectStatement =
		connection.prepareStatement(selectStr);
	rs = selectStatement.executeQuery();

	while (rs.next()) {
	    values = values + ",PRO_CLAPREV2_ID = " + rs.getInt("CLA_ID")
		    + ",PRO_CLAPREV2_PJT = " + rs.getInt("CLA_PJT")
		    + ",PRO_CLAPREV2_PGT = " + rs.getInt("CLA_PGT")
		    + ",PRO_CLAPREV2_PET = " + rs.getInt("CLA_PET")
		    + ",PRO_CLAPREV2_PPT = " + rs.getInt("CLA_PPT")
		    + ",PRO_CLAPREV2_GFT = " + rs.getInt("CLA_GFT")
		    + ",PRO_CLAPREV2_GCT = " + rs.getInt("CLA_GCT")
		    + ",PRO_CLAPREV2_PJF = " + rs.getInt("CLA_PJF")
		    + ",PRO_CLAPREV2_PGF = " + rs.getInt("CLA_PGF")
		    + ",PRO_CLAPREV2_PEF = " + rs.getInt("CLA_PEF")
		    + ",PRO_CLAPREV2_PPF = " + rs.getInt("CLA_PPF")
		    + ",PRO_CLAPREV2_GFF = " + rs.getInt("CLA_GFF")
		    + ",PRO_CLAPREV2_GCF = " + rs.getInt("CLA_GCF");

	}
	rs.close();

	jdbcHelper.cleanup(connection, selectStatement, null);

	return values;
    }

    private String leerClasifGpeRondaAnterior(Integer equipo1, Integer equipo2) throws SQLException, NamingException {

	Connection connection = jdbcHelper.getConnection();
	String values = "";
	rondaAnterior = ronda - 1;
	String selectStr =
		"SELECT * FROM CLASIFGPE WHERE GPE_CTO_ID = "
		+ ctoId.toString() + " AND GPE_RONDA = "
		+ rondaAnterior.toString() + " AND GPE_EQU_ID = "
		+ equipo1.toString();

	selectStatement =
		connection.prepareStatement(selectStr);
	ResultSet rs = selectStatement.executeQuery();

	while (rs.next()) {
	    values = ",PRO_GPEPREV1_ID = " + rs.getInt("GPE_ID")
		    + ",PRO_GPEPREV1_PSG = " + rs.getInt("GPE_PSG")
		    + ",PRO_GPEPREV1_PSE = " + rs.getInt("GPE_PSE")
		    + ",PRO_GPEPREV1_PSP = " + rs.getInt("GPE_PSP")
		    + ",PRO_GPEPREV1_PSNG = " + rs.getInt("GPE_PSNG")
		    + ",PRO_GPEPREV1_PSNE = " + rs.getInt("GPE_PSNE")
		    + ",PRO_GPEPREV1_PSNP = " + rs.getInt("GPE_PSNP");
	}

	rs.close();


	selectStr =
		"SELECT * FROM CLASIFGPE WHERE GPE_CTO_ID = "
		+ ctoId.toString() + " AND GPE_RONDA = "
		+ rondaAnterior.toString() + " AND GPE_EQU_ID = "
		+ equipo2.toString();

	selectStatement =
		connection.prepareStatement(selectStr);
	rs = selectStatement.executeQuery();

	while (rs.next()) {
	    values = values + ",PRO_GPEPREV2_ID = " + rs.getInt("GPE_ID")
		    + ",PRO_GPEPREV2_PSG = " + rs.getInt("GPE_PSG")
		    + ",PRO_GPEPREV2_PSE = " + rs.getInt("GPE_PSE")
		    + ",PRO_GPEPREV2_PSP = " + rs.getInt("GPE_PSP")
		    + ",PRO_GPEPREV2_PSNG = " + rs.getInt("GPE_PSNG")
		    + ",PRO_GPEPREV2_PSNE = " + rs.getInt("GPE_PSNE")
		    + ",PRO_GPEPREV2_PSNP = " + rs.getInt("GPE_PSNP");
	}

	rs.close();

	jdbcHelper.cleanup(connection, selectStatement, null);

	return values;
    }

    private String leerRat4Ronda(Integer equipo) throws SQLException, NamingException {

	Connection connection = jdbcHelper.getConnection();
	String values = "";
	String selectStr =
		"SELECT * FROM RATING4 WHERE RAT4_CTO_ID = "
		+ ctoId.toString() + " AND RAT4_RONDA = "
		+ ronda.toString() + " AND RAT4_EQU1_ID = "
		+ equipo.toString();

	selectStatement =
		connection.prepareStatement(selectStr);
	ResultSet rs = selectStatement.executeQuery();

	while (rs.next()) {
	    values = ",PRO_RAT4_ID = " + rs.getInt("RAT4_ID")
		    + ",PRO_RAT4_EQU1_ID = " + rs.getInt("RAT4_EQU1_ID")
		    + ",PRO_RAT4_EQU1_PREV = " + rs.getInt("RAT4_EQU1_PREV")
		    + ",PRO_RAT4_EQU2_ID = " + rs.getInt("RAT4_EQU2_ID")
		    + ",PRO_RAT4_EQU2_PREV = " + rs.getInt("RAT4_EQU2_PREV")
		    + ",PRO_RAT4_DIF_PREV = " + rs.getInt("RAT4_DIF_PREV")
		    + ",PRO_RAT4_PROB_G = " + rs.getInt("RAT4_PROB_G")
		    + ",PRO_RAT4_PROB_E = " + rs.getInt("RAT4_PROB_E")
		    + ",PRO_RAT4_PROB_P = " + rs.getInt("RAT4_PROB_P")
		    + ",PRO_RAT4_RESUL = '" + rs.getString("RAT4_RESUL") + "'"
		    + ",PRO_RAT4_EQU1_POST = " + rs.getInt("RAT4_EQU1_POST")
		    + ",PRO_RAT4_EQU2_POST = " + rs.getInt("RAT4_EQU2_POST");
	}

	rs.close();
	jdbcHelper.cleanup(connection, selectStatement, null);

	return values;
    }
}
