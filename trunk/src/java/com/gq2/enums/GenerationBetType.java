package com.gq2.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.model.SelectItem;

public enum GenerationBetType {

    BASIC(0, "generationBetTypeBasic", "Generated Authomatically"), // Asuntos personales
    LIMITED_456_SIGN_1(1, "generationBetTypeLimited_456_Sign1", "Limited_456_signs_1"), // Asuntos personales
    FORCED_FIRST_5_TO_111X2_SIGNS(2, "generationBetTypeForcedFirst5To111X2Signs", "Forced_First_5_To_111X2_Signs"), // Asuntos personales
    FORCED_FIRST_4_TO_111Xo2_SIGNS(3, "generationBetTypeForcedFirst4To111Xo2Signs", "Forced_First_4_To_111Xo2_Signs"), // Asuntos personales
    FIRST_4_TO_111Xo2_SIGNS_6_1(4, "generationBetTypeFirst4To111Xo2Signs_6_1", "First_4_To_111Xo2_Signs_6_1"), // Asuntos personales
    FIRST_4_TO_111Xo2_SIGNS_5_1(5, "generationBetTypeFirst4To111Xo2Signs_5_1", "First_4_To_111Xo2_Signs_5_1"); // Asuntos personales
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
	    case 3:
		return GenerationBetType.FORCED_FIRST_4_TO_111Xo2_SIGNS;
	    case 4:
		return GenerationBetType.FIRST_4_TO_111Xo2_SIGNS_6_1;
	    case 5:
		return GenerationBetType.FIRST_4_TO_111Xo2_SIGNS_5_1;
	    default:
		throw new EnumConstantNotPresentException(GenerationBetType.class, i
			+ ": Identificador para tipo de falta, no permitido");
	}
    }
}
