package com.gq2.services;

import com.gq2.DAO.DAOFactory;
import com.gq2.domain.Prognostic;
import java.sql.*;
import java.util.List;

/**
 * @author RAFAEL FERRUZ
 */
public class PrognosticService{

    public PrognosticService() {
    }

    public int save(Prognostic prognostic) throws SQLException {

	return new DAOFactory().getPrognosticDAO().save(prognostic);
    }
    /*
     public Prognostic load(int chaId) {

     return new DAOFactory().getPrognosticDAO().load(chaId);
     }

     public boolean delete(Prognostic prognostic) {
     return new DAOFactory().getPrognosticDAO().delete(prognostic);
     }

     public boolean update(Prognostic prognostic) {
     return new DAOFactory().getPrognosticDAO().update(prognostic);
     }
     */

    public ResultSet loadPrognosticRoundListWithTeamNames(int chaId, int round) {
	return new DAOFactory().getPrognosticDAO().loadPrognosticRoundListWithTeamNames(chaId, round);
    }

    public List<Prognostic> loadPrognosticRoundList(int chaId, int round) {
	return new DAOFactory().getPrognosticDAO().loadPrognosticRoundList(chaId, round);
    }

}
