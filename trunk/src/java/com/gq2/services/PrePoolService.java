package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.PrePool;
import com.gq2.domain.Prognostic;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RAFAEL FERRUZ
 */
public class PrePoolService {

    /* tableRatings ha sido definida con los datos calculados el 06/12/2013 */
    private static Integer[][] tableRatings = {
	{-30, 257, 237, 362},
	{-20, 257, 212, 226,},
	{-12, 287, 242, 207},
	{-6, 308, 203, 190},
	{0, 356, 232, 217},
	{5, 400, 254, 238},
	{12, 389, 233, 180},
	{20, 387, 211, 176},
	{30, 358, 211, 130},
	{9999, 549, 196, 136}
    };
    /* private static Integer[][] tableRatings = {
     {-34, 0, 0, 1},
     {- 31, 0, 0, 1},
     {-26, 1, 1, 3},
     {-25, 0, 2, 0},
     {-24, 0, 0, 4},
     {-23, 0, 2, 7},
     {-22, 6, 3, 13},
     {-21, 7, 4, 9},
     {-20, 4, 6, 19},
     {-19, 8, 11, 25},
     {-18, 9, 12, 25},
     {-17, 19, 26, 32},
     {-16, 23, 27, 40},
     {-15, 29, 28, 60},
     {-14, 45, 49, 72},
     {-13, 73, 72, 83},
     {-12, 100, 79, 101},
     {-11, 108, 100, 116},
     {-10, 175, 136, 123},
     {-9, 187, 172, 151},
     {-8, 290, 217, 176},
     {-7, 318, 246, 251},
     {-6, 401, 284, 253},
     {-5, 504, 335, 281},
     {-4, 517, 383, 336},
     {-3, 659, 436, 356},
     {-2, 742, 482, 333},
     {-1, 782, 434, 360},
     {0, 1130, 617, 442},
     {1, 814, 483, 297},
     {2, 751, 420, 258},
     {3, 770, 386, 258},
     {4, 715, 371, 200},
     {5, 553, 298, 192},
     {6, 505, 236, 174},
     {7, 448, 205, 128},
     {8, 390, 161, 104},
     {9, 308, 104, 66},
     {10, 267, 91, 67},
     {11, 205, 72, 34},
     {12, 166, 48, 30},
     {13, 131, 44, 25},
     {14, 105, 33, 19},
     {15, 85, 23, 15},
     {16, 61, 7, 7},
     {17, 52, 10, 6},
     {18, 33, 5, 0},
     {19, 30, 4, 6},
     {20, 23, 3, 1},
     {21, 16, 3, 1},
     {22, 11, 2, 0},
     {23, 7, 1, 1},
     {24, 3, 0, 0},
     {25, 3, 0, 0},
     {26, 4, 1, 0},
     {27, 7, 0, 1},
     {28, 1, 0, 0},
     {31, 1, 0, 0},
     {9999, 1, 0, 0}
     };*/

    public PrePoolService() {
    }

    public int save(PrePool prePool) {

	return new DAOFactory().getPrePoolDAO().save(prePool);
    }

    public PrePool load(int chaId) {

	return new DAOFactory().getPrePoolDAO().load(chaId);
    }

    public int delete(PrePool prePool) {
	return new DAOFactory().getPrePoolDAO().delete(prePool);
    }

    public boolean update(PrePool prePool) {
	return new DAOFactory().getPrePoolDAO().update(prePool);
    }

    public List<PrePool> loadPrePool(int season, int order_number) {
	return new DAOFactory().getPrePoolDAO().loadSeasonPrePoolListOrderByRating(season, order_number);
    }

    public List<PrePool> loadPrePoolById(int season, int order_number) {
	return new DAOFactory().getPrePoolDAO().loadSeasonPrePoolListOrderById(season, order_number);
    }

    public int deleteMatch(PrePool prePool) {
	return new DAOFactory().getPrePoolDAO().deleteMatch(prePool);
    }

    public List<PrePool> loadRoundPrePoolList(int season, int order_number) {
	return new DAOFactory().getPrePoolDAO().loadRoundPrePoolList(season, order_number);
    }

    public int deleteSeasons(int first_season, int last_season) {
	return new DAOFactory().getPrePoolDAO().deleteSeasons(first_season, last_season);
    }

    public List<Integer> loadPrePoolOrderNumberList(Integer season, Integer firstRound, Integer lastRound) {
	List<Integer> orderNumberList = new DAOFactory().getPrePoolDAO().loadPrePoolOrderNumberList(season);
	List<Integer> resultList = new ArrayList<>();
	for (Integer i : orderNumberList) {
	    if (i >= firstRound && i <= lastRound) {
		resultList.add(i);
	    }
	}
	return resultList;
    }

    public List<PrePool> loadNlastPreTool(Integer season, Integer orderNumber, Integer orderNumber4) {
	return new DAOFactory().getPrePoolDAO().loadNlastPreTool(season, orderNumber, orderNumber4);
    }

    public static Long getPrePoolRatPointsFromPrognostic(Prognostic prognostic) {
	Long ratPoints = Math.round(100.0 * (prognostic.getPro_cla_previous1_home_won_games() * 2
		+ prognostic.getPro_cla_previous1_home_drawn_games()
		- prognostic.getPro_cla_previous2_out_won_games() * 2
		- prognostic.getPro_cla_previous2_out_drawn_games())
		/ prognostic.getPro_cla_previous1_total_played_games());
	return ratPoints;
    }

    public static String getPrePoolPrognosticFromPercents(PrePool prePool) {
	if ((prePool.getPrePercentWin() + prePool.getPrePercentDraw())
		>= (prePool.getPrePercentDraw() + prePool.getPrePercentLose())) {
	    return "WD";
	} else {
	    return "DL";
	}
    }

    public static void setPrePoolPercentsFromPrognosticPreviousDiference(Prognostic prognostic, PrePool prePool) {
	for (int i = 0; i < tableRatings.length; i++) {
	    if (tableRatings[i][0] > (prognostic.getPro_rat4_previous_diference() / 10)) {
		prePool.setPrePercentWin(100 * tableRatings[i][1]
			/ (tableRatings[i][1]
			+ tableRatings[i][2]
			+ tableRatings[i][3]));
		prePool.setPrePercentDraw(100 * tableRatings[i][2]
			/ (tableRatings[i][1]
			+ tableRatings[i][2]
			+ tableRatings[i][3]));
		prePool.setPrePercentLose(100 * tableRatings[i][3]
			/ (tableRatings[i][1]
			+ tableRatings[i][2]
			+ tableRatings[i][3]));
//		i = tableRatings.length;
		break;
	    }

	}

    }
}
