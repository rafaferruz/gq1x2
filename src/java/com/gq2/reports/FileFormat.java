/**
 * Enumerado para tipos de contenido en la repuesta
 * Representa un formato de archivo y el contentType a incluir en el objeto Response.
 */
package com.gq2.reports;

public enum FileFormat {

	PDF("pdf", "application/pdf"),
	XML("xml", "text/xml"),
	TXT("txt", "text/plain"),
	EXCEL("xls", "application/excel"),
	CSV("csv", "text/csv"),
	RTF("rtf", "application/rtf"),
	HTML("html", "text/html"),
	XHTML("xhtml", "text/xhtml"),
	ODT("odt", "application/msword"), // Open Document Text
	ODS("ods", "application/excel"), // Open Document Stylesheet
	DOC("doc", "application/msword"),
	DOCX("docx", "application/x-msword"),
	XLSX("xlsx", "application/x-excel");
	private final String param;
	private final String contentType;

	FileFormat(String param, String contentType) {
		this.param = param;
		this.contentType = contentType;
	}


	public static FileFormat parse(String str) {
		switch (str) {
			case "pdf":
				return FileFormat.PDF;
			case "xml":
				return FileFormat.XML;
			case "excel":
				return FileFormat.EXCEL;
			case "xls":
				return FileFormat.EXCEL;
			case "csv":
				return FileFormat.CSV;
			case "rtf":
				return FileFormat.RTF;
			case "html":
				return FileFormat.HTML;
			case "xhtml":
				return FileFormat.XHTML;
			case "odt":
				return FileFormat.ODT;
			case "ods":
				return FileFormat.ODS;
			case "docx":
				return FileFormat.DOCX;
			case "xlsx":
				return FileFormat.XLSX;
			default:
				throw new EnumConstantNotPresentException(FileFormat.class, str
					+ " : identificador de FileFormat no permitido");
		}
	}
	public String getContentType() {
		return contentType;
	}

	public String getParam() {
		return param;
	}
}
