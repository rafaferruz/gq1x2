/*
Añade columnas de errores producidos en las apuestas realizadas
*/
ALTER TABLE bets ADD COLUMN bet_group1_errors_1 INTEGER DEFAULT 0 AFTER bet_group1_values_2;
ALTER TABLE bets ADD COLUMN bet_group1_errors_X INTEGER DEFAULT 0 AFTER bet_group1_errors_1;
ALTER TABLE bets ADD COLUMN bet_group1_errors_2 INTEGER DEFAULT 0 AFTER bet_group1_errors_X;
ALTER TABLE bets ADD COLUMN bet_group2_errors_1 INTEGER DEFAULT 0 AFTER bet_group2_values_2;
ALTER TABLE bets ADD COLUMN bet_group2_errors_X INTEGER DEFAULT 0 AFTER bet_group2_errors_1;
ALTER TABLE bets ADD COLUMN bet_group2_errors_2 INTEGER DEFAULT 0 AFTER bet_group2_errors_X;
ALTER TABLE bets ADD COLUMN bet_group3_errors_1 INTEGER DEFAULT 0 AFTER bet_group3_values_2;
ALTER TABLE bets ADD COLUMN bet_group3_errors_X INTEGER DEFAULT 0 AFTER bet_group3_errors_1;
ALTER TABLE bets ADD COLUMN bet_group3_errors_2 INTEGER DEFAULT 0 AFTER bet_group3_errors_X;
ALTER TABLE bets ADD COLUMN bet_group4_errors_1 INTEGER DEFAULT 0 AFTER bet_group4_values_2;
ALTER TABLE bets ADD COLUMN bet_group4_errors_X INTEGER DEFAULT 0 AFTER bet_group4_errors_1;
ALTER TABLE bets ADD COLUMN bet_group4_errors_2 INTEGER DEFAULT 0 AFTER bet_group4_errors_X;
ALTER TABLE bets ADD COLUMN bet_group5_errors_1 INTEGER DEFAULT 0 AFTER bet_group5_values_2;
ALTER TABLE bets ADD COLUMN bet_group5_errors_X INTEGER DEFAULT 0 AFTER bet_group5_errors_1;
ALTER TABLE bets ADD COLUMN bet_group5_errors_2 INTEGER DEFAULT 0 AFTER bet_group5_errors_X;
