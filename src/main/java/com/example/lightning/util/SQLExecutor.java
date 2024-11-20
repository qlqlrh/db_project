package com.example.lightning.util;

import com.example.lightning.config.DatabaseConfig;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExecutor {
    public static void executeSQLFromFile(String filePath) {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            StringBuilder sql = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sql.append(line).append(" ");
                if (line.trim().endsWith(";")) { // SQL 문이 끝나면 실행
                    stmt.execute(sql.toString());
                    sql.setLength(0); // StringBuilder 초기화
                }
            }
            System.out.println("SQL Execution Completed.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}

