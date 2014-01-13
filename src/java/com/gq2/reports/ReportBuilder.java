package com.gq2.reports;

import com.gq2.domain.Company;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase de utilidad para facilitar la generación de informes. - Encapsula todo
 * el código necesario para generar un PDF con JasperReport, incluyendo
 * compilación y cacheo del .jrxml. - Permite, opcionalmente, que los centros
 * tengan versiones personalizadas de un mismo informe, según un archivo de
 * propiedades. - Permite, opcionalmente, que haya informes personalizados para
 * cada año.
 */
public class ReportBuilder {

    transient private final Logger log = LogManager.getLogger(this.getClass());
    public static final int PDF = 0;
    public static final int EXCEL = 0;
    public static final int WORD = 0;
    public static final int RTF = 0;
    /**
     * Cacheo en memoria de los archivos de propiedades.
     */
    private static final Map<ReportMap, Properties> reportMapping = new HashMap<ReportMap, Properties>();

    /**
     * Devuelve una referencia al archivo fuente (.jrxml). Cachea el archivo de
     * properties en memoria.
     */
    private List<File> getSourceReportFiles(ServletContext context, ReportMap companyReportMap, Integer year,
	    String reportKey) {
	ReportMap defaultReportMap = ReportMap.getDefaultReportMap();
	synchronized (reportMapping) {
	    // Inicialización
	    // Cargamos el archivo de propiedades general, si no está cargado ya.
	    if (!reportMapping.containsKey(defaultReportMap)) {
		File propertiesFile = new File(context.getRealPath("/WEB-INF/ireports/"
			+ defaultReportMap.getFileName()));
		Properties properties = new Properties();
		try {
		    properties.load(new FileReader(propertiesFile));
		} catch (IOException ex) {
		    log.error("Error al leer archivo de propiedades de informes", ex);
		    throw new RuntimeException("Error al leer archivo de propiedades de informes", ex);
		}
		reportMapping.put(defaultReportMap, properties);
	    }
	    // Cargamos el archivo de propiedades del centro, si no está cargado ya.
	    if (!reportMapping.containsKey(companyReportMap)) {
		File propertiesFile = new File(context.getRealPath("/WEB-INF/ireports/"
			+ companyReportMap.getFileName()));
		Properties properties = new Properties();
		try {
		    properties.load(new FileReader(propertiesFile));
		} catch (IOException ex) {
		    log.error("Error al leer archivo de propiedades de informes", ex);
		    throw new RuntimeException("Error al leer archivo de propiedades de informes", ex);
		}
		reportMapping.put(companyReportMap, properties);
	    }
	}
	// Cogemos el valor de la primera propiedad que exista, siguiendo el siguiente orden:
	// 1. Propiedad del centro con año
	// 2. Propiedad general con año
	// 3. Propiedad del centro sin año
	// 4. Propiedad general sin año
	String[] fileNames;
	String reportKeyWithYear = reportKey + "@" + year;
	if (year != null && reportMapping.get(companyReportMap).containsKey(reportKeyWithYear)) {
	    fileNames = reportMapping.get(companyReportMap).getProperty(reportKeyWithYear).split(",");
	} else if (year != null && reportMapping.get(defaultReportMap).containsKey(reportKeyWithYear)) {
	    fileNames = reportMapping.get(defaultReportMap).getProperty(reportKeyWithYear).split(",");
	} else if (reportMapping.get(companyReportMap).containsKey(reportKey)) {
	    fileNames = reportMapping.get(companyReportMap).getProperty(reportKey).split(",");
	} else if (reportMapping.get(defaultReportMap).containsKey(reportKey)) {
	    fileNames = reportMapping.get(defaultReportMap).getProperty(reportKey).split(",");
	} else {
	    log.error("Informe no encontrado: " + reportKey);
	    throw new RuntimeException("Informe no encontrado");
	}
	List<File> reportFiles = new ArrayList<File>();
	for (String fileName : fileNames) {
	    File reportFile = new File(context.getRealPath("/WEB-INF/ireports/" + fileName.trim()));
	    if (!reportFile.canRead()) {
		log.error("No se puede leer el archivo de informe: " + fileName);
		throw new RuntimeException("No se puede leer el archivo de informe");
	    }
	    reportFiles.add(reportFile);
	}
	return reportFiles;
    }

    /**
     * Devuelve la ruta del archivo JasperReport compilado (.jasper). Si el
     * archivo compilado no existe, o tiene fecha anterior al archivo fuente, lo
     * compila. Lo mismo con los subinformes que pueda haber.
     */
    private String getReportFileName(ServletContext context, ReportMap companyReportMap, Integer year,
	    String reportKey) {
	List<File> sourceFiles = getSourceReportFiles(context, companyReportMap, year, reportKey);
	String mainReportFileName = null;
	for (File sourceFile : sourceFiles) {
	    String sourceFileName = sourceFile.getAbsolutePath();
	    String compiledFileName = sourceFileName.substring(0, sourceFileName.length() - ".jrxml".length())
		    + ".jasper";
	    File compiledFile = new File(compiledFileName);
	    if (!compiledFile.exists() || compiledFile.lastModified() < sourceFile.lastModified()) {
		// Si el archivo compilado no existe, o tiene fecha anterior al archivo fuente, lo compilamos.
		try {
		    JasperDesign reportDesign = JRXmlLoader.load(sourceFile);
		    JasperCompileManager.compileReportToFile(reportDesign, compiledFileName);
		} catch (JRException ex) {
		    log.error("Error al compilar informe", ex);
		    throw new RuntimeException("Error al compilar el informe", ex);
		}
	    }
	    // El primero es el informe principal, almacenamos su ruta
	    if (mainReportFileName == null) {
		mainReportFileName = compiledFileName;
	    }
	}
	return mainReportFileName;
    }

    /**
     * Genera el informe rellenado con los parámetros y la colección pasadas.
     */
    private JasperPrint fillReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters, Collection dataCollection) {
	JRDataSource dataSource;
	if (dataCollection != null) {
	    dataSource = new JRBeanCollectionDataSource(dataCollection);
	} else {
	    dataSource = new JREmptyDataSource();
	}
	String reportFileName = getReportFileName(context, company.getReportMap(), year, reportKey);
	// Establecemos el locale
	parameters.put(JRParameter.REPORT_LOCALE, locale);
	try {
	    return JasperFillManager.fillReport(reportFileName, parameters, dataSource);
	} catch (JRException ex) {
	    log.error("Error al generar informe. reportFileName: " + reportFileName + " - Parameters "
		    + parameters, ex);
	    throw new RuntimeException("Error al generar el informe: ", ex);
	}
    }

    /**
     * Escribe el informe en el formato seleccionado, en el outputStream
     * recibido.
     *
     * @param jasperPrint Objeto que contiene el informe.
     * @param outputStream Stream donde se escribe el informe generado.
     * @param fileFormat Formato de archivo.
     */
    private void writeToStream(JasperPrint jasperPrint, OutputStream outputStream, FileFormat fileFormat) {
	try {
	    switch (fileFormat) {
		case PDF:
		    JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		    break;
		case XML:
		    JasperExportManager.exportReportToXmlStream(jasperPrint, outputStream);
		    break;
		case EXCEL:
		    JRXlsExporter xlsExporter = new JRXlsExporter();
		    xlsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    xlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		    xlsExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		    xlsExporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, false);
		    xlsExporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, true);
		    xlsExporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, false);
		    xlsExporter.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BACKGROUND, true);
		    xlsExporter.setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BORDER, true);
		    xlsExporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
		    xlsExporter.exportReport();
		    break;
		case CSV:
		    JRCsvExporter csvExporter = new JRCsvExporter();
		    csvExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    csvExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		    csvExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		    csvExporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, "\t");
		    csvExporter.setParameter(JRCsvExporterParameter.RECORD_DELIMITER, "\r\n");
		    csvExporter.exportReport();
		    break;
		case RTF:
		    JRRtfExporter rtfExporter = new JRRtfExporter();
		    rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    rtfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		    rtfExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		    rtfExporter.exportReport();
		    break;
		case HTML:
		    JRHtmlExporter htmlExporter = new JRHtmlExporter();
		    htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    htmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		    htmlExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		    htmlExporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
		    htmlExporter.exportReport();
		    break;
		case XHTML:
		    JRXhtmlExporter xhtmlExporter = new JRXhtmlExporter();
		    xhtmlExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    xhtmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		    xhtmlExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		    xhtmlExporter.exportReport();
		    break;
		case ODT:
		    JROdtExporter odtExporter = new JROdtExporter();
		    odtExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    odtExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		    odtExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		    odtExporter.exportReport();
		    break;
		case ODS:
		    JROdsExporter odsExporter = new JROdsExporter();
		    odsExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    odsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		    odsExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		    odsExporter.exportReport();
		    break;
		case DOCX:
		    JRDocxExporter docxExporter = new JRDocxExporter();
		    docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		    docxExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		    docxExporter.exportReport();
		    break;
		case XLSX:
		    JRXlsxExporter xlsxExporter = new JRXlsxExporter();
		    xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		    xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		    xlsxExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8");
		    xlsxExporter.exportReport();
		    break;
		default:
		    throw new RuntimeException("Formato de archivo no especificado o no reconocido");
	    }
	} catch (JRException ex) {
	    log.error("Error al generar informe en " + fileFormat, ex);
	    throw new RuntimeException("Error al generar el informe en " + fileFormat, ex);
	}
    }

    /**
     * Devuelve un archivo PDF con el informe correspondiente.
     *
     * @param context ServletContext de la aplicación.
     * @param company Empresa para el que se genera el informe.
     * @param year Año al que corresponde el informe.
     * @param reportKey Clave que identifica al informe en el archivo de
     * properties.
     * @param parameters Mapa de objetos que se pasan al informe.
     * @param dataCollection Lista de objetos que se pasan al informe.
     * @param fileFormat Formato de archivo de salida.
     * @return Un array de bytes con el PDF generado.
     */
    public byte[] getReport(ServletContext context, Locale locale, Company company, Integer year, String reportKey,
	    Map<String, Object> parameters, Collection dataCollection, FileFormat fileFormat) {
	JasperPrint jasperPrint = fillReport(context, locale, company, year, reportKey, parameters, dataCollection);
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	writeToStream(jasperPrint, outputStream, fileFormat);
	return outputStream.toByteArray();
    }

    /**
     * Escribe en el OutputStream proporcionado un archivo con el informe
     * correspondiente.
     *
     * @param context ServletContext de la aplicación.
     * @param company Empresa para el que se genera el informe.
     * @param year Año al que corresponde el informe.
     * @param reportKey Clave que identifica al informe en el archivo de
     * properties.
     * @param parameters Mapa de objetos que se pasan al informe.
     * @param dataCollection Lista de objetos que se pasan al informe.
     * @param fileFormat Formato de archivo de salida.
     * @param outputStream Flujo de salida donde se escribe el informe.
     */
    public void writeReport(ServletContext context, Locale locale, Company company, Integer year, String reportKey,
	    Map<String, Object> parameters, Collection dataCollection, FileFormat fileFormat, OutputStream outputStream) {
	JasperPrint jasperPrint = fillReport(context, locale, company, year, reportKey, parameters, dataCollection);
	writeToStream(jasperPrint, outputStream, fileFormat);
    }

    /*
     * Métodos de conveniencia para hacer las llamadas más sencilla (no tener que elegir el tipo y no tener que poner
     * parámetros con valor 'null'.
     */
    public byte[] getPDFReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters, Collection dataCollection) {
	return getReport(context, locale, company, year, reportKey, parameters, dataCollection, FileFormat.PDF);
    }

    public byte[] getExcelReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters, Collection dataCollection) {
	return getReport(context, locale, company, year, reportKey, parameters, dataCollection, FileFormat.EXCEL);
    }

    public byte[] getRtfReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters, Collection dataCollection) {
	return getReport(context, locale, company, year, reportKey, parameters, dataCollection, FileFormat.RTF);
    }

    public byte[] getPDFReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters) {
	return getReport(context, locale, company, year, reportKey, parameters, null, FileFormat.PDF);
    }

    public byte[] getExcelReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters) {
	return getReport(context, locale, company, year, reportKey, parameters, null, FileFormat.EXCEL);
    }

    public byte[] getRtfReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters) {
	return getReport(context, locale, company, year, reportKey, parameters, null, FileFormat.RTF);
    }

    public void writePDFReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters, Collection dataCollection, OutputStream outputStream) {
	writeReport(context, locale, company, year, reportKey, parameters, dataCollection, FileFormat.PDF,
		outputStream);
    }

    public void writeExcelReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters, Collection dataCollection, OutputStream outputStream) {
	writeReport(context, locale, company, year, reportKey, parameters, dataCollection, FileFormat.EXCEL,
		outputStream);
    }

    public void writeRtfReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters, Collection dataCollection, OutputStream outputStream) {
	writeReport(context, locale, company, year, reportKey, parameters, dataCollection, FileFormat.RTF,
		outputStream);
    }

    public void writePDFReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters, OutputStream outputStream) {
	writeReport(context, locale, company, year, reportKey, parameters, null, FileFormat.PDF, outputStream);
    }

    public void writeExcelReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters, OutputStream outputStream) {
	writeReport(context, locale, company, year, reportKey, parameters, null, FileFormat.EXCEL, outputStream);
    }

    public void writeRtfReport(ServletContext context, Locale locale, Company company, Integer year,
	    String reportKey, Map<String, Object> parameters, OutputStream outputStream) {
	writeReport(context, locale, company, year, reportKey, parameters, null, FileFormat.RTF, outputStream);
    }

    public void writePDFReport(ServletContext context, Locale locale, Company company, String reportKey,
	    Map<String, Object> parameters, OutputStream outputStream) {
	writeReport(context, locale, company, null, reportKey, parameters, null, FileFormat.PDF, outputStream);
    }

    public void writePDFReport(ServletContext context, Locale locale, Company company, String reportKey,
	    Collection dataCollection, OutputStream outputStream) {
	writeReport(context, locale, company, null, reportKey, new HashMap<String, Object>(), dataCollection,
		FileFormat.PDF, outputStream);
    }

    public void writeRtfReport(ServletContext context, Locale locale, Company company, String reportKey,
	    Collection dataCollection, OutputStream outputStream) {
	writeReport(context, locale, company, null, reportKey, new HashMap<String, Object>(), dataCollection,
		FileFormat.RTF, outputStream);
    }

    public void writeExcelReport(ServletContext context, Locale locale, Company company, String reportKey,
	    Map<String, Object> parameters, OutputStream outputStream) {
	writeReport(context, locale, company, null, reportKey, parameters, null, FileFormat.EXCEL, outputStream);
    }

    public void writeRtfReport(ServletContext context, Locale locale, Company company, String reportKey,
	    Map<String, Object> parameters, OutputStream outputStream) {
	writeReport(context, locale, company, null, reportKey, parameters, null, FileFormat.RTF, outputStream);
    }
}
