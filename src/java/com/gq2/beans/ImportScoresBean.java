package com.gq2.beans;

import com.gq2.domain.Team;
import com.gq2.services.ChampionshipService;
import com.gq2.services.ImportScoresWikipediaProcess;
import com.gq2.services.TeamService;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 * @author RAFAEL FERRUZ
 */
@ManagedBean(name = "importScores")
@ViewScoped
public class ImportScoresBean {

    private List<SelectItem> championshipItemList = new ArrayList<>();
    protected Map<String, Integer> teamsMap = new HashMap<>();
    protected Map<String, String> equivalentTeamNamesMap = new HashMap<>();
    protected Map<String, UnknownTeam> unknownTeamNamesMap = new HashMap<>();
    private ChampionshipService championshipService = new ChampionshipService();
    private Integer scoChaId;
    private String fileName;
    private List<UnknownTeam> newNameList = new ArrayList<>();
    private List<String> existingTeamList = new ArrayList<>();
    private boolean equivalenteNamesFormRender = false;
    private List<SelectItem> existingTeamSelectItems = new ArrayList<>();
    private Integer existingTeamSelectItemValue = 0;
    private boolean disableCheckScoresFile = false;
    private boolean disableImportScoresFile = true;

    public ImportScoresBean() {
    }

    public List<SelectItem> getChampionshipItemList() {
	if (championshipItemList.isEmpty()) {
	    championshipItemList = championshipService.getChampionshipItemList();
	}
	return championshipItemList;
    }

    public void setChampionshipItemList(List<SelectItem> championshipItemList) {
	this.championshipItemList = championshipItemList;
    }

    public Integer getScoChaId() {
	return scoChaId;
    }

    public void setScoChaId(Integer scoChaId) {
	this.scoChaId = scoChaId;
    }

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public Map<String, Integer> getTeamsMap() {
	return teamsMap;
    }

    public void setTeamsMap(Map<String, Integer> teamsMap) {
	this.teamsMap = teamsMap;
    }

    public Map<String, String> getEquivalentTeamNamesMap() {
	return equivalentTeamNamesMap;
    }

    public void setEquivalentTeamNamesMap(Map<String, String> equivalentTeamNamesMap) {
	this.equivalentTeamNamesMap = equivalentTeamNamesMap;
    }

    public boolean isEquivalenteNamesFormRender() {
	return equivalenteNamesFormRender;
    }

    public void setEquivalenteNamesFormRender(boolean equivalenteNamesFormRender) {
	this.equivalenteNamesFormRender = equivalenteNamesFormRender;
    }

    public void setNewNameList(List<UnknownTeam> newNameList) {
	this.newNameList = newNameList;
    }

    public List<UnknownTeam> getNewNameList() {
	return newNameList;
    }

    public List<SelectItem> getExistingTeamSelectItems() {
	return existingTeamSelectItems;
    }

    public void setExistingTeamSelectItems(List<SelectItem> existingTeamSelectItems) {
	this.existingTeamSelectItems = existingTeamSelectItems;
    }

    public Integer getExistingTeamSelectItemValue() {
	return existingTeamSelectItemValue;
    }

    public void setExistingTeamSelectItemValue(Integer existingTeamSelectItemValue) {
	this.existingTeamSelectItemValue = existingTeamSelectItemValue;
    }

    public boolean isDisableCheckScoresFile() {
	return disableCheckScoresFile;
    }

    public void setDisableCheckScoresFile(boolean disableCheckScoresFile) {
	this.disableCheckScoresFile = disableCheckScoresFile;
    }

    public boolean isDisableImportScoresFile() {
	return disableImportScoresFile;
    }

    public void setDisableImportScoresFile(boolean disableImportScoresFile) {
	this.disableImportScoresFile = disableImportScoresFile;
    }

    public String openScoresFile() {
	FacesContext fc = FacesContext.getCurrentInstance();

	String fileScores = fc.getExternalContext().getRealPath("/WEB-INF/" + getFileName());
// Abre fichero de scores a importar
	try {
	    try (FileReader fr = new FileReader(fileScores)) {
		BufferedReader bf = new BufferedReader(fr);

		checkScoresFile(bf);
		setEquivalenteNamesFormRender(!unknownTeamNamesMap.isEmpty());
	    }
	} catch (FileNotFoundException ex) {
	    fc.addMessage("Nombre de Fichero de resultados ",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, fileScores + " no encontrado.", fileScores + " no encontrado."));
	} catch (IOException ex) {
	    fc.addMessage("Nombre de Fichero de resultados ",
		    new FacesMessage(FacesMessage.SEVERITY_ERROR, fileScores + " Fallo de lectura.", fileScores + " Fallo de lectura."));
	}

	return "";
    }

    public String updateTeams() {
	TeamService teamService = new TeamService();
	Team team;
	for (UnknownTeam unknown : newNameList) {
	    switch (unknown.getEquivalentId()) {
		case 0:
		    team = new Team();
		    team.setTeaName(unknown.getNewName());
		    team.setTeaCode(unknown.getNewName().length() > 24 ? unknown.getNewName().substring(0, 24) : unknown.getNewName());
		    teamService.save(team);
		    break;
		default:
		    team = teamService.load(unknown.getEquivalentId());
		    team.setTeaEquivalentNames((team.getTeaEquivalentNames() == null ? "" : team.getTeaEquivalentNames()) + unknown.getNewName() + "/");
		    teamService.update(team);
		    break;
	    }
	}
	setEquivalenteNamesFormRender(false);
	return "";
    }

    public String importScoresFile() {
	ImportScoresWikipediaProcess importProcess = new ImportScoresWikipediaProcess();
	importProcess.setChaId(scoChaId);
	FacesContext fc = FacesContext.getCurrentInstance();

	String fileScores = fc.getExternalContext().getRealPath("/WEB-INF/" + getFileName());
	importProcess.doImport(fileScores);

	return "index";
    }

    public String cancelUpdating() {
	return "";
    }

    public String cancel() {
	return "index";
    }

    private void checkScoresFile(BufferedReader bf) throws IOException {

	ImportScoresWikipediaProcess importProcess = new ImportScoresWikipediaProcess();

	/* Se obtienen los mapas de equipos actuales y nombres de equipos equivalentes actuales */
	importProcess.initMaps();
	teamsMap = importProcess.getTeamsMap();
	existingTeamList.clear();
	existingTeamList.addAll(teamsMap.keySet());
	Collections.sort(existingTeamList,
		new Comparator<String>() {
	    @Override
	    public int compare(String s1, String s2) {
		return s1.compareTo(s2);
	    }
	});
	/* Se crea la lista de SelectItems para mostrar la listbox de equipos actuales */
	existingTeamSelectItems.clear();
	existingTeamSelectItems.add(new SelectItem(0, "Crear nuevo equipo"));
	for (String team : existingTeamList) {
	    existingTeamSelectItems.add(new SelectItem((Integer) teamsMap.get(team), team));
	}

	/* Se crea un mapa con los nombres equivalentes de los equipos actuales */
	equivalentTeamNamesMap = importProcess.getEquivalentTeamNamesMap();

	unknownTeamNamesMap.clear();
	String scoreRow;
	int numberLine = 0;
	while ((scoreRow = bf.readLine()) != null) {
	    numberLine++;
	    if (!scoreRow.startsWith("Jornada") && !scoreRow.startsWith("Local")) {
		/* Comprueba que existe el nombre de equipo o su equivalente, 
		 * en otro caso lo añade a una lista de nombres desconocidos
		 */
		if (scoreRow.split(",")[0].equals("") || 
			(scoreRow.split(",")[2].equals("")&& scoreRow.split(",")[3].equals(""))) {
		    System.out.println(numberLine + "  " + scoreRow);
		} else {
		    /* Se comprueba solamente el equipo local ya que todos los equipos tienen
		     * condicion de local en algun momento.
		     */
		    checkTeamNameInMaps(scoreRow.split(",")[0]);
		}
	    }
	}
	newNameList.clear();
	newNameList.addAll(unknownTeamNamesMap.values());
	if (newNameList.isEmpty()) {
	    setDisableCheckScoresFile(true);
	    setDisableImportScoresFile(false);
	}
    }

    private void checkTeamNameInMaps(String teamName) {
	if (null == teamsMap.get(teamName)
		&& null == equivalentTeamNamesMap.get(teamName)
		&& null == unknownTeamNamesMap.get(teamName)) {
	    unknownTeamNamesMap.put(teamName, new UnknownTeam(teamName, 0));
	}
    }

    public class UnknownTeam {

	private String newName;
	private Integer equivalentId;

	public UnknownTeam(String newName, Integer equivalentId) {
	    this.newName = newName;
	    this.equivalentId = equivalentId;
	}

	public String getNewName() {
	    return newName;
	}

	public void setNewName(String newName) {
	    this.newName = newName;
	}

	public Integer getEquivalentId() {
	    return equivalentId;
	}

	public void setEquivalentId(Integer equivalentId) {
	    this.equivalentId = equivalentId;
	}
    }
}
