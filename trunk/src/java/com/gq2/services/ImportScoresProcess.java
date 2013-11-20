package com.gq2.services;

import com.gq2.domain.Championship;
import com.gq2.DAO.DAOFactory;
import com.gq2.domain.EnrolledTeam;
import com.gq2.domain.Team;
import com.gq2.domain.Score;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author RAFAEL FERRUZ
 */
public abstract class ImportScoresProcess {

    protected Map<String, Championship> championshipMap = new HashMap<>();
    protected Map<String, Integer> teamsMap = new HashMap<>();
    protected List<Championship> championshipsList = new ArrayList<>();
    protected List<Team> teamsList = new ArrayList<>();
    protected Date fechaSQL = new Date(0);
    protected String championshipRow;
    protected List<Integer> enrolledList = new ArrayList<>();
    protected Integer championshipForEnrolling;

    public ImportScoresProcess() {
    }

    protected Map<String, Championship> getChampionshipMap() {
	return championshipMap;
    }

    protected void setChampionshipMap(Map<String, Championship> championshipMap) {
	this.championshipMap = championshipMap;
    }

    protected Map<String, Integer> getTeamsMap() {
	return teamsMap;
    }

    protected void setTeamsMap(Map<String, Integer> teamsMap) {
	this.teamsMap = teamsMap;
    }

    protected List<Championship> getChampionshipList() {
	return championshipsList;
    }

    protected void setChampionshipList(List<Championship> championshipsList) {
	this.championshipsList = championshipsList;
    }

    protected List<Team> getListTeams() {
	return teamsList;
    }

    protected void setListTeams(List<Team> teamsList) {
	this.teamsList = teamsList;
    }

    protected String getChampionshipRow() {
	return championshipRow;
    }

    protected void setChampionshipRow(String championshipRow) {
	this.championshipRow = championshipRow;
    }

    protected Date getFechaSQL() {
	return fechaSQL;
    }

    protected void setFechaSQL(Date fechaSQL) {
	this.fechaSQL = fechaSQL;
    }

    protected void initMaps() throws SQLException, Exception {
	initMapChampionships();
	initMapTeams();
    }

    protected void initMapChampionships() throws SQLException, Exception {

	championshipsList = new DAOFactory().getChampionshipDAO().loadAllChampionships();
	for (Championship championship : championshipsList) {
	    championshipMap.put(championship.getChaCode(), championship);
	}
    }

    protected void initMapTeams() throws SQLException, Exception {

	teamsList = new DAOFactory().getTeamDAO().loadAllTeams();
	for (Team team : teamsList) {
	    teamsMap.put(team.getTeaCode(), team.getTeaId());
	}
    }

    /**
     * Ejecuta el bucle principal de la importacion de datos
     * 
     * @param fileScores    Deber recibir la direccion completa y nombre del fichero que contiene los datos a importar
     */
    abstract public void doImport(String fileScores) throws IOException, Exception ;
    

    protected boolean checkingsScore(String scoreStr) throws SQLException, Exception {
	int championshipId;
	int teamId;
	championshipRow = "FUT" + scoreStr.substring(2, 4) + "-" + scoreStr.substring(7, 9) + scoreStr.substring(13, 15);
	setChampionshipRow(championshipRow);
	// verifica campeonato
	championshipId = verifyChampionship(championshipRow);
	if (championshipId == 0) {
	    return false;
	}
	// verifica equipo1
	teamId = verifyTeam(scoreStr.substring(28, 45).trim());
	if (championshipId == 0) {
	    return false;
	} else {
	    insertInscripcionTeam(championshipId, teamId);
	}
	// verifica equipo2
	teamId = verifyTeam(scoreStr.substring(45, 62).trim());
	if (teamId == 0) {
	    return false;
	} else {
	    insertInscripcionTeam(championshipId, teamId);
	}

	return true;
    }

    /**
     *
     * @param championship	String que contiene los datos basicos para buscar o
     * crear un championship (campeonato) en la BD
     * @return	El campo cha_id del campeonato. 0 si no existe y no ha podido ser
     * creado.
     * @throws SQLException
     * @throws Exception
     */
    protected int verifyChampionship(String championship) throws SQLException, Exception {


	if (getChampionshipMap().get(championship) != null) {
	    return getChampionshipMap().get(championship).getChaId();
	}
// No existe Campeonato.
	// Creamos championship
	return saveChampionship(championship);
    }

    /**
     *
     * @param equipo	String que contiene los datos basicos para buscar o crear
     * un team (equipo) en la BD
     * @return	El campo tea_id del equipo. 0 si no existe y no ha podido ser
     * creado.
     * @throws SQLException
     * @throws Exception
     */
    protected int verifyTeam(String equipo) throws SQLException, Exception {

	if (getTeamsMap().get(equipo) != null) {
	    return getTeamsMap().get(equipo);
	}
// No existe Equipo.
	// Creamos equipo
	return saveTeam(equipo);
    }

    protected int saveChampionship(String championshipStr) throws Exception {

	int season;
	Championship championship = new Championship();
	championship.setChaId(0);
	championship.setChaCode(championshipStr);
	championship.setChaStatus(0);
	championship.setChaDescription(championshipStr);
	championship.setChaSeason("");
	if ((Integer.parseInt(championshipStr.substring(3, 5))) < 70) {
	    season = 2000 + (Integer.parseInt(championshipStr.substring(3, 5)));
	} else {
	    season = 1900 + (Integer.parseInt(championshipStr.substring(3, 5)));
	}
	fechaSQL = (new SimpleDateFormat("dd/MM/yyyy")).parse("30/08/" + season);
	championship.setChaStartDate(fechaSQL);
	championship.setChaPointsWin(3);
	championship.setChaPointsDraw(1);
	championship.setChaPointsLose(0);
	championship.setChaMaxTeams(20);
	//Guarda championship, recupera el Id del objeto guardado y si es > 0 lo integra en el map
	championship.setChaId(new DAOFactory().getChampionshipDAO().save(championship));
	if (championship.getChaId() > 0) {
	    championshipMap.put(championshipStr, championship);
	}
	return championship.getChaId();
    }

    protected int saveTeam(String equipo) throws Exception {

	Team tea = new Team();
	tea.setTeaId(0);
	tea.setTeaCode(equipo.substring(0, Math.min(24, equipo.length())));
	tea.setTeaStatus(0);
	tea.setTeaName(equipo);
	tea.setTeaRating(0);
	tea.setTeaId(new DAOFactory().getTeamDAO().save(tea));
	if (tea.getTeaId() > 0) {
	    teamsMap.put(tea.getTeaCode(), tea.getTeaId());
	}
	return tea.getTeaId();
    }

    protected void insertInscripcionTeam(Integer championshipId, Integer teamId) {

	// Se comprueba si el campeonato aun no tiene equipos pero hay 
	// pendiente de guardar los equipos de un campeonato anteriormente leido
	if (championshipId != championshipForEnrolling && championshipForEnrolling != null) {
	    // Se persiten los enroll de los equipos en un campeonato
	    saveEnrollings();
	    enrolledList.clear();
	}
	championshipForEnrolling = championshipId;
	if (!enrolledList.contains(teamId)){
	    enrolledList.add(teamId);
	}
    }

    private void saveEnrollings() {
	List<EnrolledTeam> enrolledTeamList = new ArrayList<>();
	for (Integer teamId : enrolledList) {
	    enrolledTeamList.add(new EnrolledTeam(championshipForEnrolling, teamId));
	}
	new DAOFactory().getEnrolledTeamDAO().saveEnrolledTeamList(enrolledTeamList);
    }

    protected int saveScore(String resultado) throws Exception {

	Score res = new Score();
	res.setScoChaId(championshipMap.get(championshipRow).getChaId());
	res.setScoRound(Integer.parseInt(resultado.substring(21, 23).trim()));
// Calcula la fecha de la jornada tomando como base la fecha de inicio del championship
	res.setScoDate(new Date(championshipMap.get(championshipRow).getChaStartDate().getTime()
		+ ((long) (res.getScoRound() - 1) * 7L * 24L * 60L * 60L * 1000L)));
	res.setScoTeam1Id(teamsMap.get(resultado.substring(28, 45).trim()));
	res.setScoTeam2Id(teamsMap.get(resultado.substring(45, 62).trim()));
	res.setScoScore1(Integer.parseInt(resultado.substring(62, resultado.lastIndexOf("-"))));
	res.setScoScore2(Integer.parseInt(resultado.substring(resultado.lastIndexOf("-") + 1)));

	return new DAOFactory().getScoreDAO().save(res);
    }
}
