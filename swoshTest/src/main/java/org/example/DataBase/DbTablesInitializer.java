package org.example.DataBase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DbTablesInitializer {

    static String query1 = """
            CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    account_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    password VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                    first_name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                    last_name VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                    signature_number INT UNSIGNED NOT NULL,
                    ssn BIGINT UNSIGNED NOT NULL UNIQUE,
                    logged_in TINYINT(1) NOT NULL DEFAULT 0
            );
            """;
    static String query2 = """
            CREATE TABLE IF NOT EXISTS accounts (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    account_created DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    phone_number VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                    user_id INT NOT NULL,
                    balance BIGINT UNSIGNED NOT NULL
            );
            """;
    static String query3 = """
            CREATE TABLE IF NOT EXISTS transactions (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    sender VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                    recipient VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                    date_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                    amount BIGINT UNSIGNED NOT NULL
            );
            """;

    public DbTablesInitializer() {
        createTablesAndColumnsInDb();
    }

    private void createTablesAndColumnsInDb() {
        ArrayList<String> sqlSetTableAndColumns = new ArrayList<>();
        sqlSetTableAndColumns.add(query1);
        sqlSetTableAndColumns.add(query2);
        sqlSetTableAndColumns.add(query3);

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            for (String sqlSetTableAndColumn : sqlSetTableAndColumns) {
                conn.prepareStatement(sqlSetTableAndColumn).executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
