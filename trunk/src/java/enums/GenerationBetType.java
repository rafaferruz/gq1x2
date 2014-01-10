package enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum GenerationBetType {

	BASIC(0, "generationBetTypeBasic"), // Asuntos personales
	LIMITED_456_SIGN_1(1, "generationBetTypeLimited_456_Sign1"), // Asuntos personales
	FORCED_FIRST_5_TO_111X2_SIGNS(2, "generationBetTypeForcedFirst5To111X2Signs"); // Asuntos personales

	private final int id;
	private final String key;

	GenerationBetType(int id, String key) {
		this.id = id;
		this.key = key;
	}

	public int getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	/**
	 * Devuelve la lista de tipos de faltas existentes
	 * 
	 * @return Lista de tipos de faltas
	 */
	public static List<GenerationBetType> listGenerationBetTypes() {
		return new ArrayList<>(Arrays.asList(GenerationBetType.values()));
	}

	/** Parsea el entero y devuelve el valor del enumerado */
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
