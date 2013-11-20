package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Rating4;
import com.gq2.DAO.Rating4DAO;
import com.gq2.beans.ScoreBean;
import com.gq2.domain.Score;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author RAFAEL FERRUZ
 */
public class Rating4Service implements Serializable {

    static final int PREVIOUS_ROUNDS_TO_CALCULATE_RATING = 1;
    private int chaId;
    private int round;
    private List<Rating4> rating4CurrentRoundList = new ArrayList<>();
    private List<Rating4> rating4PreviousRoundList = new ArrayList<>();
    private int[][] puntuacionEsperada = {
	{0, 3, 50},
	{4, 10, 51},
	{11, 17, 52},
	{18, 25, 53},
	{26, 32, 54},
	{33, 39, 55},
	{40, 46, 56},
	{47, 53, 57},
	{54, 61, 58},
	{62, 68, 59},
	{69, 76, 60},
	{77, 83, 61},
	{84, 91, 62},
	{92, 98, 63},
	{99, 106, 64},
	{107, 113, 65},
	{114, 121, 66},
	{122, 129, 67},
	{130, 137, 68},
	{138, 145, 69},
	{146, 153, 70},
	{154, 162, 71},
	{163, 170, 72},
	{171, 179, 73},
	{180, 188, 74},
	{189, 197, 75},
	{198, 206, 76},
	{207, 215, 77},
	{216, 225, 78},
	{226, 235, 79},
	{236, 245, 80},
	{246, 256, 81},
	{257, 267, 82},
	{268, 278, 83},
	{279, 290, 84},
	{291, 302, 85},
	{303, 315, 86},
	{316, 328, 87},
	{329, 344, 88},
	{345, 357, 89},
	{358, 374, 90},
	{375, 391, 91},
	{392, 411, 92},
	{412, 432, 93},
	{433, 456, 94},
	{457, 484, 95},
	{485, 517, 96},
	{518, 559, 97},
	{560, 619, 98},
	{620, 735, 99},
	{736, 1000, 100}
    };
    Rating4DAO r4dao;

    public Rating4Service() {
    }

    public Rating4Service(int chaId, int round) {
	this.chaId = chaId;
	this.round = round;
    }

    public int getChaId() {
	return chaId;
    }

    public void setChaId(int chaId) {
	this.chaId = chaId;
    }

    public List<Rating4> getRating4CurrentRoundList() {
	return rating4CurrentRoundList;
    }

    public void setRat4CurrentRoundList(List<Rating4> rating4CurrentRoundList) {
	this.rating4CurrentRoundList = rating4CurrentRoundList;
    }

    public List<Rating4> getRating4PreviousList() {
	return rating4PreviousRoundList;
    }

    public void setRat4PreviousList(List<Rating4> rating4PreviousList) {
	this.rating4PreviousRoundList = rating4PreviousList;
    }

    public int getRound() {
	return round;
    }

    public void setRound(int round) {
	this.round = round;
    }

    private List<Rating4> getRating4Round(Integer chaId, Integer round) {
	return new DAOFactory().getRating4DAO().loadAllRating4Round(chaId, round);
    }

    public int deleteDataRating4Round(Integer chaId, Integer round) {
// Se eliminan los registros que existan de la clasificaci�n de la ronda actual
	return new DAOFactory().getRating4DAO().deleteRating4Round(chaId, round);
    }

    /**
     * Este metodo realiza dos funciones: - Carga las filas de Rating4 de la
     * ronda anterior en una lista para posteriores operaciones. - Actualiza la
     * lista de Rating4 de la ronda actual a partir de la lista de Rating4 de la
     * ronda anterior y de los resultados de la ronda actual SIN CALCULAR LOS
     * RATING DE LA RONDA ACTUAL
     *
     * @param scoreList
     */
    public void getRating4PreviousRoundList(List<ScoreBean> scoreList) {

	rating4CurrentRoundList.clear();    // Se limpia la lista de Rating4 actual para ir llenandola
	if (!scoreList.isEmpty()) {
	    // Obtenemos un List con la clasificacion de la ronda anterior
	    setRat4PreviousList(getRating4Round(scoreList.get(0).getScoChaId(), scoreList.get(0).getScoRound() - 1));
	    for (Score score : scoreList) {
// Fase de actualizaci�n del Equipos en Score
		updateTeamsRating4FromScore(getRating4PreviousList(), score);
	    }
	}
    }

    private void updateTeamsRating4FromScore(List<Rating4> rating4PreviousRoundList, Score score) {

	/* Completa la ronda anterior con nuevos registros generados a partir
	 de los scores por si no existieran. Esto sucede siempre en la primera
	 ronda de un campeonato y no deberia suceder en rondas posteriores. */
	completeRating4PreviousRoundList(rating4PreviousRoundList, score);
// Bucle de actualizaci�n de rating

	for (Rating4 r1 : rating4PreviousRoundList) {
	    if (r1.getRat4Team1Id() == score.getScoTeam1Id()) {
		for (Rating4 r2 : rating4PreviousRoundList) {
		    if (r2.getRat4Team1Id() == score.getScoTeam2Id()) {
			// Se crean los registros de Rating4 de los dos equipos del Score con datos iniciales
			Rating4 rating1 = createNewRating4Team1(score, r1, r2);
			Rating4 rating2 = createNewRating4Team2(score, r1, r2);
//			rating1.setRat4Team2Post(rating2.getRat4Team1Post());
//			rating2.setRat4Team2Post(rating1.getRat4Team1Post());
			rating4CurrentRoundList.add(rating1);
			rating4CurrentRoundList.add(rating2);
			break;
		    }
		}
		break;
	    }
	}
// Final de actualizacion de clasificacion 

    }

    private void completeRating4PreviousRoundList(List<Rating4> rating4PreviousRoundList, Score score) {
	// Completa la ronda anterior con nuevos registros generados a partir
	// de los scores por si no existieran
	boolean noExiste = true;
	for (Rating4 r1 : rating4PreviousRoundList) {
	    if (r1.getRat4Team1Id() == score.getScoTeam1Id()) {
		noExiste = false;
		break;
	    }
	}
	if (noExiste) {
	    // Se crea un nuevo registro de rating4CurrentRoundList anterior
	    rating4PreviousRoundList.add(crearRating4Equipo(score.getScoChaId(),
		    score.getScoRound() - 1, score.getScoTeam1Id(), score.getScoDate()));
	}
	noExiste = true;
	for (Rating4 r1 : rating4PreviousRoundList) {
	    if (r1.getRat4Team1Id() == score.getScoTeam2Id()) {
		noExiste = false;
		break;
	    }
	}
	if (noExiste) {
	    // Se crea un nuevo registro de rating4CurrentRoundList anterior
	    rating4PreviousRoundList.add(crearRating4Equipo(score.getScoChaId(),
		    score.getScoRound() - 1, score.getScoTeam2Id(), score.getScoDate()));
	}

    }

    private Rating4 createNewRating4Team1(Score score, Rating4 r1, Rating4 r2) {
	Rating4 rating4 = new Rating4();
	rating4.setRat4ChaId(score.getScoChaId());
	rating4.setRat4Round(score.getScoRound());
	rating4.setRat4Date(score.getScoDate());
	rating4.setRat4Team1Id(score.getScoTeam1Id());
	rating4.setRat4Team1Previous(r1.getRat4Team1Post());
	rating4.setRat4Team2Id(score.getScoTeam2Id());
	rating4.setRat4Team2Previous(r2.getRat4Team1Post());
	rating4.setRat4PreviousDiference(r1.getRat4Team1Post() - r2.getRat4Team1Post());
	rating4.setRat4ProbabilityWin(0);
	rating4.setRat4ProbabilityDraw(0);
	rating4.setRat4ProbabilityLose(0);
	if (score.getScoScore1() > score.getScoScore2()) {
	    rating4.setRat4ScoreSign("W");
	} else if (score.getScoScore1() == score.getScoScore2()) {
	    rating4.setRat4ScoreSign("D");
	} else if (score.getScoScore1() < score.getScoScore2()) {
	    rating4.setRat4ScoreSign("L");
	}

	rating4.setRat4Team1Post(1300);
	rating4.setRat4Team2Post(1300);
	return rating4;

    }

    private Rating4 createNewRating4Team2(Score score, Rating4 r1, Rating4 r2) {
	Rating4 rating4 = new Rating4();
	rating4.setRat4ChaId(score.getScoChaId());
	rating4.setRat4Round(score.getScoRound());
	rating4.setRat4Date(score.getScoDate());
	rating4.setRat4Team1Id(score.getScoTeam2Id());
	rating4.setRat4Team1Previous(r2.getRat4Team1Post());
	rating4.setRat4Team2Id(score.getScoTeam1Id());
	rating4.setRat4Team2Previous(r1.getRat4Team1Post());
	rating4.setRat4PreviousDiference(r2.getRat4Team1Post() - r1.getRat4Team1Post());
	rating4.setRat4ProbabilityWin(0);
	rating4.setRat4ProbabilityDraw(0);
	rating4.setRat4ProbabilityLose(0);
	if (score.getScoScore2() > score.getScoScore1()) {
	    rating4.setRat4ScoreSign("W");
	} else if (score.getScoScore2() == score.getScoScore1()) {
	    rating4.setRat4ScoreSign("D");
	} else if (score.getScoScore2() < score.getScoScore1()) {
	    rating4.setRat4ScoreSign("L");
	}

	rating4.setRat4Team1Post(1300);
	rating4.setRat4Team2Post(1300);
	return rating4;

    }

    public void generateRating4CurrentRound(List<ScoreBean> scoreList) {
	int indexOfr1, indexOfr2;
	for (Score score : scoreList) {

// Bucle de actualizaci�n de rating
	    for (Rating4 r1 : rating4CurrentRoundList) {
		if (r1.getRat4Team1Id() == score.getScoTeam1Id()) {
		    indexOfr1 = rating4CurrentRoundList.indexOf(r1);
		    for (Rating4 r2 : rating4CurrentRoundList) {
			if (r2.getRat4Team1Id() == score.getScoTeam2Id()) {
			    rating4UpdateRating(score, r1, r2, PREVIOUS_ROUNDS_TO_CALCULATE_RATING);
			    /*
			     indexOfr2 = rating4CurrentRoundList.indexOf(r2);
			     // Se actualizan los datos de rating
			     rating4CurrentRoundList.set(indexOfr1, rating4Actualizar1Rating(score, r1, r2, PREVIOUS_ROUNDS_TO_CALCULATE_RATING));
			     Rating4 rating1 = rating4CurrentRoundList.get(indexOfr1);
			     rating4CurrentRoundList.set(indexOfr2, rating4Actualizar2Rating(score, r1, r2, PREVIOUS_ROUNDS_TO_CALCULATE_RATING));
			     Rating4 rating2 = rating4CurrentRoundList.get(indexOfr2);
			     rating1.setRat4Team2Post(rating2.getRat4Team1Post());
			     rating2.setRat4Team2Post(rating1.getRat4Team1Post());
			     //			    rating4CurrentRoundList.set(indexOfr1, rating1);
			     //			    rating4CurrentRoundList.set(indexOfr2, rating2);
			     * */
			    break;
			}
		    }
		    break;
		}

	    }
	}
    }

    /**
     *
     * @param score
     * @param r1
     * @param r2
     * @param previousRoundsNumber Numero de rondas previas a considerar en el
     * calculo del rating de la ronda actual
     * @return
     */
    private Rating4 rating4Actualizar1Rating(Score score, Rating4 r1, Rating4 r2, int previousRoundsNumber) {

	List<Rating4> rating4LatestRoundsBeforeDateListTeam1 = new DAOFactory().getRating4DAO().readLatestBeforeDateRating4Team(score.getScoTeam1Id(), score.getScoDate(), previousRoundsNumber);
	List<Rating4> rating4LatestRoundsBeforeDateListTeam2 = new DAOFactory().getRating4DAO().readLatestBeforeDateRating4Team(score.getScoTeam2Id(), score.getScoDate(), previousRoundsNumber);

	int mediaRivales = 0;
	int mediaPropia = 0;
	double puntuacionObtenida = 0.0;
	int diferenciaRating;
	int ratingInicial = 0;
	int ratingFinal;
	double k = 15.0;

	int nRows = rating4LatestRoundsBeforeDateListTeam1.size();

	if (nRows == 0) {
// El equipo no tiene enfrentamientos anteriores en ninguna categor�a
	    ratingInicial = 1300;
	    mediaPropia = 1300;
	    mediaRivales = 1300;
	    puntuacionObtenida = 0.5;
	    nRows = 1;
	}
	for (Rating4 rating : rating4LatestRoundsBeforeDateListTeam1) {
	    mediaRivales = mediaRivales + rating.getRat4Team2Post();
	    mediaPropia = mediaPropia + rating.getRat4Team1Post();
	    switch (rating.getRat4ScoreSign()) {
		case "W":
		    puntuacionObtenida = puntuacionObtenida + 1.0;
		    break;
		case "D":
		    puntuacionObtenida = puntuacionObtenida + 0.5;
		    break;
	    }

	    ratingInicial = rating.getRat4Team1Post();
	}
// Se han encontrado un m�nimo de 4 registros de rating
	if (ratingInicial > 0) {
	    mediaRivales = mediaRivales / nRows;
	    mediaPropia =
		    mediaPropia / nRows;
	    diferenciaRating =
		    mediaPropia - mediaRivales;
	    /* Lineas anuladas el 17/11/2013
	     * double a =  nRows * probabilidadVictoria(diferenciaRating);
	     a = puntuacionObtenida - a;
	     //            ratingFinal = ratingInicial + (int) (k * a);
	     ratingFinal = mediaPropia + (int) (k * a);
	     */
	    diferenciaRating = r1.getRat4Team1Previous() - r1.getRat4Team2Previous();
	    switch (r1.getRat4ScoreSign()) {
		case "W":
		    puntuacionObtenida = 1.0;
		    break;
		case "D":
		    puntuacionObtenida = 0.5;
		    break;
		case "L":
		    puntuacionObtenida = 0.0;
		    break;
	    }
	    double a = puntuacionObtenida - probabilidadVictoria(diferenciaRating);
	    ratingFinal = r1.getRat4Team1Previous() + (int) (k * a);
	    r1.setRat4Team1Post(ratingFinal);
	}

	return r1;

    }

    private Rating4 rating4Actualizar2Rating(Score score, Rating4 r1, Rating4 r2, int previousRoundsNumber) {

	List<Rating4> rating4LatestRoundsBeforeDateList = new DAOFactory().getRating4DAO().readLatestBeforeDateRating4Team(score.getScoTeam1Id(), score.getScoDate(), previousRoundsNumber);

	int mediaRivales = 0;
	int mediaPropia = 0;
	double puntuacionObtenida = 0.0;
	int diferenciaRating;
	int ratingInicial = 0;
	int ratingFinal;
	double k = 15.0;

	int nRows = rating4LatestRoundsBeforeDateList.size();

	if (nRows == 0) {
// El equipo no tiene enfrentamientos anteriores en ninguna categor�a
	    ratingInicial = 1300;
	    mediaPropia = 1300;
	    mediaRivales = 1300;
	    puntuacionObtenida = 0.5;
	    nRows = 1;
	} else {
	    for (Rating4 rating : rating4LatestRoundsBeforeDateList) {
		mediaRivales = mediaRivales + rating.getRat4Team2Post();
		mediaPropia =
			mediaPropia + rating.getRat4Team1Post();
		switch (rating.getRat4ScoreSign()) {
		    case "W":
			puntuacionObtenida = puntuacionObtenida + 1.0;
			break;
		    case "D":
			puntuacionObtenida = puntuacionObtenida + 0.5;
			break;
		}

		ratingInicial = rating.getRat4Team1Post();
	    }
	}
// Se han encontrado un m�nimo de 4 registros de rating
//        nRows=Math.max(nRows, 1);
	if (ratingInicial > 0) {
	    mediaRivales = mediaRivales / nRows;
	    mediaPropia =
		    mediaPropia / nRows;
	    diferenciaRating =
		    mediaPropia - mediaRivales;
	    /* Lineas anuladas el 17/11/2013
	     * double a =  nRows * probabilidadVictoria(diferenciaRating);
	     a = puntuacionObtenida - a;
	     //            ratingFinal = ratingInicial + (int) (k * a);
	     ratingFinal = mediaPropia + (int) (k * a);
	     */
	    diferenciaRating = r2.getRat4Team1Previous() - r2.getRat4Team2Previous();
	    switch (r2.getRat4ScoreSign()) {
		case "W":
		    puntuacionObtenida = 1.0;
		    break;
		case "D":
		    puntuacionObtenida = 0.5;
		    break;
		case "L":
		    puntuacionObtenida = 0.0;
		    break;
	    }
	    double a = puntuacionObtenida - probabilidadVictoria(diferenciaRating);
	    ratingFinal = r2.getRat4Team1Previous() + (int) (k * a);

	    r2.setRat4Team1Post(ratingFinal);
	}

	return r2;

    }

    /**
     *
     * @param score
     * @param r1
     * @param r2
     * @param previousRoundsNumber Numero de rondas previas a considerar en el
     * calculo del rating de la ronda actual
     * @return
     */
    private void rating4UpdateRating(Score score, Rating4 r1, Rating4 r2, int previousRoundsNumber) {

	previousRoundsNumber = 1;
	List<Rating4> rating4LatestRoundsBeforeDateListTeam1 = new DAOFactory().getRating4DAO().readLatestBeforeDateRating4Team(score.getScoTeam1Id(), score.getScoDate(), previousRoundsNumber);
	List<Rating4> rating4LatestRoundsBeforeDateListTeam2 = new DAOFactory().getRating4DAO().readLatestBeforeDateRating4Team(score.getScoTeam2Id(), score.getScoDate(), previousRoundsNumber);

	double puntuacionObtenidaTeam1 = 0.0;
	double puntuacionObtenidaTeam2 = 0.0;
	int rat4Team1Previous = 1300;
	int rat4Team2Previous = 1300;
	double puntuacionEsperadaTeam1 = 0.0;
	double puntuacionEsperadaTeam2 = 0.0;
	double k = 32.0;

	if (rating4LatestRoundsBeforeDateListTeam1.isEmpty()) {
	    rat4Team1Previous = 1300;
	} else {
	    rat4Team1Previous = rating4LatestRoundsBeforeDateListTeam1.get(0).getRat4Team1Post();
	}
	if (rating4LatestRoundsBeforeDateListTeam2.isEmpty()) {
	    rat4Team2Previous = 1300;
	} else {
	    rat4Team2Previous = rating4LatestRoundsBeforeDateListTeam2.get(0).getRat4Team1Post();
	}
	/* TEAM 1: Calculo de la puntuacion esperada.
	 * 
	 */
	puntuacionEsperadaTeam1 = puntuacionEsperadaTeam1 + (1 / (1 + Math.pow(10, ((rat4Team2Previous - rat4Team1Previous) / 400))));
	/* TEAM 1: Calculo de la puntuacion obtenida.
	 * 
	 */
	switch (r1.getRat4ScoreSign()) {
	    case "W":
		puntuacionObtenidaTeam1 = 1.0;
		break;
	    case "D":
		puntuacionObtenidaTeam1 = 0.5;
		break;
	    case "L":
		puntuacionObtenidaTeam1 = 0.0;
		break;
	}
	/* TEAM 1: Calculo del rating nuevo.
	 * 
	 */
	r1.setRat4Team1Post((int) (rat4Team1Previous + k * (puntuacionObtenidaTeam1 - puntuacionEsperadaTeam1)));
	/* TEAM 2: Calculo de la puntuacion esperada.
	 * 
	 */
	    puntuacionEsperadaTeam2 = puntuacionEsperadaTeam2 + (1 / (1 + Math.pow(10, ((rat4Team1Previous - rat4Team2Previous) / 400))));
	switch (r2.getRat4ScoreSign()) {
	    /* TEAM 1: Calculo de la puntuacion obtenida.
	     * 
	     */
	    case "W":
		puntuacionObtenidaTeam2 = 1.0;
		break;
	    case "D":
		puntuacionObtenidaTeam2 = 0.5;
		break;
	    case "L":
		puntuacionObtenidaTeam2 = 0.0;
		break;
	}
	/* TEAM 1: Calculo del rating nuevo.
	 * 
	 */
	r2.setRat4Team1Post((int) (rat4Team2Previous + k * (puntuacionObtenidaTeam2 - puntuacionEsperadaTeam2)));

	r1.setRat4Team2Post(r2.getRat4Team1Post());
	r2.setRat4Team2Post(r1.getRat4Team1Post());
	return;
    }

    private Double probabilidadVictoria(Integer diferenciaRating) {
	Integer diferencia;
	Integer signo = 1;
	if (diferenciaRating < 0) {
	    signo = -1;
	}

	diferencia = diferenciaRating * signo;
	for (int i = 0; i < this.puntuacionEsperada.length - 1; i++) {
	    if (this.puntuacionEsperada[i][0] <= diferencia && this.puntuacionEsperada[i][1] >= diferencia) {
		if (signo == 1) {
		    return (Double.valueOf(this.puntuacionEsperada[i][2]) / 100.0);
		} else {
		    return (1.0 - (Double.valueOf(this.puntuacionEsperada[i][2]) / 100.0));
		}

	    }
	}
	return 0.0;
    }

    private Rating4 crearRating4Equipo(int chaId, int round, int team, Date date) {
	Rating4 regRating4 = new Rating4();
	regRating4.setRat4Id(0);
	regRating4.setRat4ChaId(chaId);
	regRating4.setRat4Round(round);
	regRating4.setRat4Date(date);
	regRating4.setRat4Team1Id(team);
	regRating4.setRat4Team1Previous(1300);
	regRating4.setRat4Team2Id(0);
	regRating4.setRat4Team2Previous(1300);
	regRating4.setRat4PreviousDiference(0);
	regRating4.setRat4ProbabilityWin(0);
	regRating4.setRat4ProbabilityDraw(0);
	regRating4.setRat4ProbabilityLose(0);
	regRating4.setRat4ScoreSign("");
	regRating4.setRat4Team1Post(1300);
	regRating4.setRat4Team2Post(1300);
	return regRating4;
    }

    public int insertRating4RoundData(Integer chaId, Integer round) {
	// Se eliminan las filas de Rating4 de la ronda actual
	deleteDataRating4Round(chaId, round);
// Se insertan los nuevos registros de rating4CurrentRoundList a partir de la la nueva lista
	return new DAOFactory().getRating4DAO().insertRating4CurrentRoundList(rating4CurrentRoundList);
    }
}