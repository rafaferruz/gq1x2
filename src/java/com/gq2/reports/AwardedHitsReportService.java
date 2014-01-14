package com.gq2.reports;

import com.gq2.DAO.DAOFactory;
import java.util.List;

public class AwardedHitsReportService {

    public AwardedHitsReportService() {
    }

    public List<AwardedHit> getAwardedHitList(Integer chaId, Integer orderNumber,
	    String betDescription, List<String> reductionNameList, Integer hitsNumber) {
	return new DAOFactory().getHitDAO().loadAwardedHitList(chaId,orderNumber,betDescription,reductionNameList,hitsNumber);
    }
}
