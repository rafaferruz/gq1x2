package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.beans.ClassificationBean;
import com.gq2.domain.Classification;
import java.sql.*;
import java.util.List;

/**
 * @author RAFAEL FERRUZ
 */
public class ClassificationService {

    public ClassificationService() {
    }

    public int save(Classification classification) throws SQLException {

	return new DAOFactory().getClassificationDAO().save(classification);
    }

    public Classification load(int chaId) {

	return new DAOFactory().getClassificationDAO().load(chaId);
    }

    public int delete(Classification classification) {
	return new DAOFactory().getClassificationDAO().delete(classification);
    }

    public boolean update(Classification classification) {
	return new DAOFactory().getClassificationDAO().update(classification);
    }

        public List<ClassificationBean> getClassificationBeanList(int chaId, int round) {
	return new DAOFactory().getClassificationDAO().loadRoundClassificationBeanList( chaId,  round);
    }

}
