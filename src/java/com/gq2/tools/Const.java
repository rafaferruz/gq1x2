package com.gq2.tools;

/**
 *
 * @author RAFAEL FERRUZ
 *
 * Define todas las constantes usadas en la aplicacion
 *
 */
public class Const {

    /* Usadas en StatPrognostic */
    public static final int MINIMUN_SIGN_1 = 4;    // m�nimo n�mero de 1 esperados
    public static final int MAXIMUN_SIGN_1 = 10;    // maximo n�mero de 1 esperados
    public static final int MINIMUN_SIGN_X = 2;    // m�nimo n�mero de X esperados
    public static final int MAXIMUN_SIGN_X = 6;    // maximo n�mero de X esperados
    public static final int MINIMUN_SIGN_2 = 2;    // m�nimo n�mero de 2 esperados
    public static final int MAXIMUN_SIGN_2 = 6;    // maximo n�mero de 2 esperados
    public static final int RMINUS = -2;
    public static final int RPLUS = 2;
    public static final int HISTORIC_PERCENT_SIGN_1 = 45;  // Porcentaje historico de signos 1 en la decada 2006-2015
    public static final int HISTORIC_PERCENT_SIGN_X = 29;  // Porcentaje historico de signos 1 en la decada 2006-2015
    public static final int HISTORIC_PERCENT_SIGN_2 = 26;  // Porcentaje historico de signos 1 en la decada 2006-2015
    public static final int HISTORIC_BETS_1 = 6;	//  numero de signos 1 por quiniela historico
    public static final int HISTORIC_BETS_X = 4;	//  numero de signos 1 por quiniela historico
    public static final int HISTORIC_BETS_2 = 4;	//  numero de signos 1 por quiniela historico
    public static final int MAX_MIN_RANGE = 2;	    // Rango entre minimo y maximo numero de un signo
    public static final int DIFERENCE_RATING_FOR_CUTTING_SIGN_1 = 15;
    public static final int DIFERENCE_RATING_FOR_CUTTING_SIGN_2 = -125;
    /* Usadas en PrePoolBean */
    public static final int FIRST_SEASON = 2000;
    public static final int LAST_SEASON = 2013;
    public static final int HITTED_PROGNOSTIC = 0; // Valor para marcar las apuestas que han sido acertadas
    public static final int FAILED_PROGNOSTIC = 1;	// Valor para las apuestas que han sido falladas
    /* Usada en BetService */
    public static final int CORRECTION_FACTOR_CALCULATION_RANGE_GROUP = 100; // Valor para modificar el porcentaje en calculos de la horquilla de signos
    public static final int WIDTH_RANGE_GROUP  = 1; // Ancho de la horquilla de signos
    public static final int BASE_DIFERENCE_RATING_FOR_GROUP_1 = 30; // Valor base de diferencia (dividida por 10) de rating para asignar un partido al grupo 1
    public static final int BASE_DIFERENCE_RATING_FOR_GROUP_2 = 5; // Valor base de diferencia de (dividida por 10) rating para asignar un partido al grupo 1
    public static final int BASE_DIFERENCE_RATING_FOR_GROUP_3 = -12; // Valor base de diferencia de (dividida por 10) rating para asignar un partido al grupo 1
    public static final int BASE_DIFERENCE_RATING_FOR_GROUP_4 = -999; // Valor base de diferencia de (dividida por 10) rating para asignar un partido al grupo 1

    /* Usadas en Creacion de Apuestas */
    public static final int MAXIMUN_COLUMNS_BY_FORM = 8;
    public static final int MAXIMUN_LINES_BY_FORM = 14;
    public static final String GENERATED_AUTHOMATICALLY_TEXT = "Generated Authomatically";

    /* Usadas en la Gestion de Columnas */
    public static final int AUTHOMATIC_REDUCTION_TO = 9;
    public static final String AUTHOMATIC_REDUCTION_SUFFIX_TEXT = "Authomatic_reduction_to_";
    public static final int AUTHOMATIC_REDUCE_FROM_COLUMN = 1;
    public static final int AUTHOMATIC_MAXIMUN_COLUMNS_IN_REDUCTION = 32;
    public static final String MAXIMUN_COLUMNS_SUFFIX_TEXT = "Maximum_columns_in_reduction_";
}
