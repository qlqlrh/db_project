package com.example.lightning.app;

import com.example.lightning.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // 데이터베이스 연결 생성
            connection = DatabaseConfig.getConnection();
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        } finally {
            // 연결 닫기
            DatabaseConfig.closeConnection(connection);
        }
    }
}
