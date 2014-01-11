package com.gq2.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ReductionType {

NO_REDUCTIONS(0, "noReductions", ""), // Sin reducciones
REDUCTION13(13, "reduction13", ""), // Reduccion a 13
REDUCTION12(12, "reduction12", ""), // Reduccion a 12
REDUCTION11(11, "reduction11", ""), // Reduccion a 11
REDUCTION10(10, "reduction10", ""), // Reduccion a 10
REDUCTION9(9, "reduction9", ""); // Reduccion a 9

private final int id;
private final String key;
private final String text;

ReductionType(int id, String key, String text) {
	this.id = id;
	this.key = key;
	this.text = text;
}

public int getId() {
	return id;
}

public String getKey() {
	return key;
}

public String getText() {
	return text;
}

/**
 * Devuelve la lista de tipos de faltas existentes
 *
 * @return Lista de tipos de faltas
 */
public static List<ReductionType> listGenerationBetTypes() {
	return new ArrayList<>(Arrays.asList(ReductionType.values()));
}

/**
 * Parsea el entero y devuelve el valor del enumerado
 */
public static ReductionType parse(int i) {
	switch (i) {
	case 0:
		return ReductionType.NO_REDUCTIONS;
	case 13:
		return ReductionType.REDUCTION13;
	case 12:
		return ReductionType.REDUCTION12;
	case 11:
		return ReductionType.REDUCTION11;
	case 10:
		return ReductionType.REDUCTION10;
	case 9:
		return ReductionType.REDUCTION9;
	default:
		throw new EnumConstantNotPresentException(ReductionType.class, i
			+ ": Identificador para tipo de falta, no permitido");
	}
}
}
