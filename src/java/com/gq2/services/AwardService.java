package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Award;
import java.util.List;

/**
 * @author RAFAEL FERRUZ
 */
public class AwardService {

    public AwardService() {
    }

    public int save(Award award) {

	return new DAOFactory().getAwardDAO().save(award);
    }

    public Award load(int awardId) {

	return new DAOFactory().getAwardDAO().load(awardId);
    }

    public boolean delete(Award award) {
	return new DAOFactory().getAwardDAO().delete(award);
    }

    public boolean update(Award award) {
	return new DAOFactory().getAwardDAO().update(award);
    }

    public List<Award> loadConditionalAwardList(int season, int orderNumber, String description) {
	return new DAOFactory().getAwardDAO().loadConditionalAwardList(season, orderNumber, description);
    }

    public Award loadConditionalAward(int season, int orderNumber, String description) {
	return new DAOFactory().getAwardDAO().loadConditionalAward(season, orderNumber, description);
    }

}
