package com.gq2.services;

import com.gq2.domain.Championship;
import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Team;
import com.gq2.domain.Score;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ImportScoresWikipediaProcess extends ImportScoresProcess {

    private int chaId;
    private int round;
    private int teamId;
    private Date date = null;
    private Championship championship;
    private ChampionshipService championshipService;
    private ScoreService scoreService;
    private TeamService teamService;

    public ImportScoresWikipediaProcess() {
	championshipService = new ChampionshipService();
	scoreService = new ScoreService();
	teamService=new TeamService();
    }

    public int getChaId() {
	return chaId;
    }

    public void setChaId(int chaId) {
	this.chaId = chaId;
    }

    public int getRound() {
	return round;
    }

    public void setRound(int round) {
	this.round = round;
    }

    public int getTeamId() {
	return teamId;
    }

    public void setTeamId(int teamId) {
	this.teamId = teamId;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public Championship getChampionship() {
	return championship;
    }

    public void setChampionship(Championship championship) {
	this.championship = championship;
    }

    @Override
    public void doImport(String fileScores) {

	// Abre el fichero externo de texto con scoreStrs
//            FileInputStream file = null;
	FileReader fr = null;
	String scoreRow = "";
	long counter = 0;

// Inicializa mapas de championships y equipos existentes
	initMaps();
	championship = championshipService.load(chaId);

// Abre fichero de scores a importar y lanza bucle de lectura de scores
	try {
	    fr = new FileReader(fileScores);
	    BufferedReader bf = new BufferedReader(fr);

	    do {
		try {
		    scoreRow = bf.readLine();
		    if (scoreRow != null) {
			System.out.println(Long.toString(++counter) + " " + scoreRow);
			// pasa l�nea de scoreStr a tratamiento base de datos para verificar championship, equipos e inscripciones
			if (checkingsScore(scoreRow) == false) {
			    System.out.println(chaId + " " + round + " " + scoreRow);
			    continue;
			}
// se guarda el scoreStr en la base de datos
			if (saveScore(scoreRow) == 0) {
			    System.out.println("ya existe " + chaId + " " + round + " " + scoreRow);
			    continue;
			}
		    }
		} catch (IOException e) {
		    //                       getServletContext().log("ERROR al leer de la fuente de datos" + e.toString());
		}

	    } while (scoreRow != null);
	    // Persiste la ultima inscripcion de equipos si hay pendientes de realizar
	    if (championshipForEnrolling != null && !enrolledList.isEmpty()) {
		List<Team> realEnrolledList = teamService.getTeamListInChampionship(chaId);
		for (Team t : realEnrolledList) {
		    for (Integer id : enrolledList) {
			if (id == t.getTeaId()) {
			    enrolledList.remove(id);
			    break;
			}
		    }
		}
		if (!enrolledList.isEmpty()) {
		    insertInscripcionTeam(0, 0);
		}
	    }
	    fr.close();

	} catch (MalformedURLException e) {
	    //               getServletContext().log("URL mal formada");
	} catch (IOException ex) {
	    Logger.getLogger(ImportScoresWikipediaProcess.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    protected boolean checkingsScore(String scoreStr) {

	String[] dataScore = scoreStr.split(",");

	if (dataScore[0].startsWith("Jornada")) {
	    /* Se establece la ronda del resultado */
	    setRound(Integer.parseInt(dataScore[0].split(" ")[1]));
	    setDate(null);
	    return false;
	}

	if (dataScore[0].startsWith("Local")) {
	    /* Skip la linea */
	    return false;
	}



	// verifica equipo1
	teamId = verifyTeam(dataScore[0]);
	if (teamId == 0) {
	    return false;
	} else {
	    insertInscripcionTeam(chaId, teamId);
	}

	// verifica equipo2
	teamId = verifyTeam(!dataScore[2].equals("") ? dataScore[2] : dataScore[3]);
	if (teamId == 0) {
	    return false;
	} else {
	    insertInscripcionTeam(chaId, teamId);
	}

	return true;
    }

    @Override
    protected int saveScore(String scoreStr) {

	String[] dataScore = scoreStr.split(",");

	Score sco = new Score();
	sco.setScoChaId(getChaId());
	sco.setScoRound(getRound());
// Calcula la fecha de la jornada tomando la primera fecha que se encuentra en una jornada
	if (getDate() == null) {
	    setDate(calculateRoundDate(!dataScore[6].equals("") ? dataScore[6] : dataScore[7]));
	}
	sco.setScoDate(getDate());
	
	String scoreName;
	scoreName = dataScore[0];
	if (teamsMap.get(scoreName) == null) {
	    if (equivalentTeamNamesMap.get(scoreName) == null) {
		throw new RuntimeException();
	    } else {
		scoreName = equivalentTeamNamesMap.get(scoreName);
	    }
	}
	sco.setScoTeam1Id(teamsMap.get(scoreName));
	
	scoreName = !dataScore[2].equals("") ? dataScore[2] : dataScore[3];
	if (teamsMap.get(scoreName) == null) {
	    if (equivalentTeamNamesMap.get(scoreName) == null) {
		throw new RuntimeException();
	    } else {
		scoreName = equivalentTeamNamesMap.get(scoreName);
	    }
	}
	sco.setScoTeam2Id(teamsMap.get(scoreName));
	sco.setScoScore1(Integer.parseInt(dataScore[1].split("-")[0].trim()));
	sco.setScoScore2(Integer.parseInt(dataScore[1].split("-")[1].trim()));

        if (scoreService.loadOnTeams(sco.getScoChaId(), sco.getScoTeam1Id(), sco.getScoTeam2Id())==null){
	    return scoreService.save(sco);
	}
	return 0;
    }

    private Date calculateRoundDate(String str) {
	String[] dateArr = str.split(" ");
	String day = dateArr[0];
	String month = "8";
	Calendar cal = Calendar.getInstance();
	cal.setTime(getChampionship().getChaStartDate());
	String year = String.valueOf(cal.get(Calendar.YEAR));
	switch (dateArr[2].toLowerCase()) {
	    case "agosto":
		month = "8";
		break;
	    case "septiembre":
		month = "9";
		break;
	    case "octubre":
		month = "10";
		break;
	    case "noviembre":
		month = "11";
		break;
	    case "diciembre":
		month = "12";
		break;
	    case "enero":
		month = "1";
		year = String.valueOf(cal.get(Calendar.YEAR) + 1);
		break;
	    case "febrero":
		month = "2";
		year = String.valueOf(cal.get(Calendar.YEAR) + 1);
		break;
	    case "marzo":
		month = "3";
		year = String.valueOf(cal.get(Calendar.YEAR) + 1);
		break;
	    case "abril":
		month = "4";
		year = String.valueOf(cal.get(Calendar.YEAR) + 1);
		break;
	    case "mayo":
		month = "5";
		year = String.valueOf(cal.get(Calendar.YEAR) + 1);
		break;
	    case "junio":
		month = "6";
		year = String.valueOf(cal.get(Calendar.YEAR) + 1);
		break;
	    case "julio":
		month = "7";
		year = String.valueOf(cal.get(Calendar.YEAR) + 1);
		break;
	}
	try {
	    return (new SimpleDateFormat("d/M/yyyy")).parse(day + "/" + month + "/" + year);
	} catch (ParseException ex) {
	    Logger.getLogger(ImportScoresWikipediaProcess.class.getName()).log(Level.SEVERE, null, ex);
	}
	return null;
    }
}
