package com.gq2.domain;

import com.gq2.DAO.DAOFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RAFAEL FERRUZ
 */
public class Printers extends Setup{

    public Printers() {
    }


    public void loadPrinters(){
	List<Setup> printers=new ArrayList<>();
	for (Setup setup:	new DAOFactory().getSetupDAO().loadAllSetups()){
	    if (setup.getStpSection().startsWith("printer-")){
		printers.add(setup);
	    }
	}
    }
}
