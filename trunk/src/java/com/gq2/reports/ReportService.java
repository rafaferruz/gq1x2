package com.gq2.reports;

import com.gq2.DAO.DAOFactory;
import java.util.List;

public class ReportService {

    public ReportService() {
    }

    public List<AwardedHit> getAwardedHitList() {
	return new DAOFactory().getHitDAO().loadAwardedHitList();
    }
    
}
