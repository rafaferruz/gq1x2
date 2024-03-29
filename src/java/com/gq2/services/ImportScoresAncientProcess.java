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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class ImportScoresAncientProcess extends ImportScoresProcess{

    public ImportScoresAncientProcess() {
    }

    @Override
    public void doImport(String fileScores) {

	// Abre el fichero externo de texto con scoreStrs
//            FileInputStream file = null;
	FileReader fr = null;
	String scoreRow = "";
	Integer counter = 0;

// Inicializa mapas de championships y equipos existentes
	initMaps();

// Abre fichero de scores a importar y lanza bucle de lectura de scores
	try {
	    fr = new FileReader(fileScores);
	    BufferedReader bf = new BufferedReader(fr);

	    do {
		try {
		    scoreRow = bf.readLine();
		    System.out.println((++counter).toString() + " " + scoreRow);
		    if (scoreRow != null) {
			if (scoreRow.startsWith("TEMPORADA")) {
			    continue;
			}
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

	    fr.close();

	} catch (MalformedURLException e) {
	    //               getServletContext().log("URL mal formada");
	} catch (IOException ex) {
	    Logger.getLogger(ImportScoresAncientProcess.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    protected boolean checkingsScore(String scoreStr) {

	championshipRow = "FUT" + scoreStr.substring(2, 4) + "-" + scoreStr.substring(7, 9) + scoreStr.substring(13, 15);
	setChampionshipRow(championshipRow);

	if (verifyChampionship(championshipRow) == 0) {
	    return false;
	}

	if (verifyTeam(scoreStr.substring(28, 45).trim()) == 0) {
	    return false;
	}

	if (verifyTeam(scoreStr.substring(45, 62).trim()) == 0) {
	    return false;
	}

	return true;
    }

    @Override
    protected int saveChampionship(String championshipStr)  {

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
	try {
	    fechaSQL = (new SimpleDateFormat("dd/MM/yyyy")).parse("30/08/" + season);
	} catch (ParseException ex) {
	    Logger.getLogger(ImportScoresAncientProcess.class.getName()).log(Level.SEVERE, null, ex);
	}
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
    protected int saveTeam(String equipo)  {

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
    protected int saveScore(String resultado) {

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
