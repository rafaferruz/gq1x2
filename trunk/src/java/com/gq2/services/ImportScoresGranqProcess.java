package com.gq2.services;

import com.gq2.domain.Championship;
import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Team;
import com.gq2.domain.Score;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ImportScoresGranqProcess extends ImportScoresProcess {

    public ImportScoresGranqProcess() {
    }

    @Override
    public void doImport(String fileScores) throws IOException, Exception {

	// Abre el fichero externo de texto con scoreStrs
//            FileInputStream file = null;
	FileReader fr = null;
	String scoreRow = "";
	long counter = 0;

// Inicializa mapas de championships y equipos existentes
	initMaps();

// Abre fichero de scores a importar y lanza bucle de lectura de scores
	try {
	    fr = new FileReader(fileScores);
	    BufferedReader bf = new BufferedReader(fr);

	    do {
		try {
		    scoreRow = bf.readLine();
		    System.out.println(Long.toString(++counter) + " " + scoreRow);
		    if (scoreRow != null) {
			// pasa l�nea de scoreStr a tratamiento base de datos para verificar championship, equipos e inscripciones
			if (checkingsScore(scoreRow) == false) {
// ha fallado alguna comprobaci�n
			    break;
			}
// se guarda el scoreStr en la base de datos
			if (saveScore(scoreRow) == 0) {
			    continue;
			}
		    }
		} catch (IOException e) {
		    //                       getServletContext().log("ERROR al leer de la fuente de datos" + e.toString());
		}

	    } while (scoreRow != null);
	    // Persiste la ultima inscripcion de equipos si hay pendientes de realizar
	    if (championshipForEnrolling != null) {
		insertInscripcionTeam(0, 0);
	    }
	    fr.close();

	} catch (MalformedURLException e) {
	    //               getServletContext().log("URL mal formada");
	}
    }

    @Override
    protected boolean checkingsScore(String scoreStr) throws SQLException, Exception {
	int championshipId;
	int teamId;
	char c = 9;
	int i;
	for (i = 0; i < 20; i++) {
	    System.out.print(scoreStr.charAt(i));
	}
	String[] scoreStrData = scoreStr.split(String.valueOf(c));

	setChampionshipRow(scoreStrData[0].trim());

	// verifica campeonato
	championshipId = verifyChampionship(getChampionshipRow());
	if (championshipId < 67) {
	    championshipId = 0;
	}
	if (championshipId == 0) {
	    return false;
	}

	// verifica equipo1
	teamId = verifyTeam(scoreStrData[2].trim());
	if (teamId == 0) {
	    return false;
	} else {
	    insertInscripcionTeam(championshipId, teamId);
	}

	// verifica equipo2
	teamId = verifyTeam(scoreStrData[3].trim());
	if (teamId == 0) {
	    return false;
	} else {
	    insertInscripcionTeam(championshipId, teamId);
	}

	return true;
    }

    @Override
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

    @Override
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

    @Override
    protected int saveScore(String scoreRow) throws Exception {
	char c = 9;
	String[] scoreStrData = scoreRow.split(String.valueOf(c));

	Score sco = new Score();
	sco.setScoChaId(championshipMap.get(championshipRow).getChaId());
	sco.setScoRound(Integer.parseInt(scoreStrData[1].trim()));
// Calcula la fecha de la jornada tomando como base la fecha de inicio del championship
	sco.setScoDate(new Date(championshipMap.get(championshipRow).getChaStartDate().getTime()
		+ ((long) (sco.getScoRound() - 1) * 7L * 24L * 60L * 60L * 1000L)));
	sco.setScoTeam1Id(teamsMap.get(scoreStrData[2].trim()));
	sco.setScoTeam2Id(teamsMap.get(scoreStrData[3].trim()));
	sco.setScoScore1(Integer.parseInt(scoreStrData[4].trim()));
	sco.setScoScore2(Integer.parseInt(scoreStrData[5].trim()));

	return new DAOFactory().getScoreDAO().save(sco);
    }
}
