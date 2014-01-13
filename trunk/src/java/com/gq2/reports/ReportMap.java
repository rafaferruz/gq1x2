/**
 * Representa una personalización del archivo de informes.
 * Así, un centro puede tener algunos informes personalizados. Los que no estén en su archivo personalizado,
 * se cojerán del archivo general.
 */
package com.gq2.reports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ReportMap {

	NONE(0, "reportMap.none", null),
	ANOTHER(1, "reportMap.another", "another");
	
	private final Integer id; // Identificador
	private final String key;
	private final String filenameSuffix; // Sufijo del archivo de informes

	ReportMap(Integer id, String key, String filenameSuffix) {
		this.id = id;
		this.key = key;
		this.filenameSuffix = filenameSuffix;
	}

	public Integer getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	/**
	 * Devuelve el nombre de archivo correspondiente a este ReportMap. El nombre de archivo es relativo a
	 * /WEB-INF/resources/reports
	 */
	public String getFileName() {
		if (this.filenameSuffix == null) {
			return "reports.properties";
		} else {
			return "reports_" + this.filenameSuffix + ".properties";
		}
	}

	/**
	 * Devuelve la lista de las personalizaciones disponibles.
	 */
	public static List<ReportMap> list() {
		return new ArrayList<>(Arrays.asList(ReportMap.values()));
	}

	/**
	 * Devuelve la personalización por defecto.
	 */
	public static ReportMap getDefaultReportMap() {
		return ReportMap.NONE;
	}

	public static ReportMap parse(Integer id) {
		switch (id) {
			case 0:
				return ReportMap.NONE;
			case 1:
				return ReportMap.ANOTHER;
			default:
				throw new EnumConstantNotPresentException(ReportMap.class,
						"Identificador de personalización no válido. " + id);
		}
	}
}
