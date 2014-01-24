package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.ScoreBean;
import com.gq2.domain.Score;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 * @author RAFAEL FERRUZ
 */
public class ScoreService {

    public ScoreService() {
    }

    public int save(Score score){

	return new DAOFactory().getScoreDAO().save(score);
    }

    public Score load(int chaId) {

	return new DAOFactory().getScoreDAO().load(chaId);
    }

    public boolean delete(Score score) {
	return new DAOFactory().getScoreDAO().delete(score);
    }

    public boolean update(Score score) {
	return new DAOFactory().getScoreDAO().update(score);
    }
    
    public List<ScoreBean> getChampionshipRoundScores(int chaId, int round){
	return new DAOFactory().getScoreDAO().loadChampionshipRoundScores(chaId, round);
    }
    public void updateScoreList(List<ScoreBean>scoreList){
	new DAOFactory().getScoreDAO().updateScoreList(scoreList);
    }
        public boolean updateScoresDate(Score score) {
	return new DAOFactory().getScoreDAO().updateScoresDate(score.getScoChaId(), score.getScoRound(), score.getScoDate());
    }

        public Score loadOnTeams(int chaId, int teamId1, int teamId2) {

	return new DAOFactory().getScoreDAO().loadOnTeams(chaId, teamId1, teamId2);
    }

}
