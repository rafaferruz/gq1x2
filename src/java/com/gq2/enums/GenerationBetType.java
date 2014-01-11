package com.gq2.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum GenerationBetType {

    BASIC(0, "generationBetTypeBasic", "Generated Authomatically"), // Asuntos personales
    LIMITED_456_SIGN_1(1, "generationBetTypeLimited_456_Sign1", "Limited_456_signs_1"), // Asuntos personales
    FORCED_FIRST_5_TO_111X2_SIGNS(2, "generationBetTypeForcedFirst5To111X2Signs", "Forced_First_5_To_111X2_Signs"); // Asuntos personales
    private final int id;
    private final String key;
    private final String text;

    GenerationBetType(int id, String key, String text) {
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
    public static List<GenerationBetType> listGenerationBetTypes() {
	return new ArrayList<>(Arrays.asList(GenerationBetType.values()));
    }

    /**
     * Parsea el entero y devuelve el valor del enumerado
     */
    public static GenerationBetType parse(int i) {
	switch (i) {
	    case 0:
		return GenerationBetType.BASIC;
	    case 1:
		return GenerationBetType.LIMITED_456_SIGN_1;
	    case 2:
		return GenerationBetType.FORCED_FIRST_5_TO_111X2_SIGNS;
	    default:
		throw new EnumConstantNotPresentException(GenerationBetType.class, i
			+ ": Identificador para tipo de falta, no permitido");
	}
    }
}
