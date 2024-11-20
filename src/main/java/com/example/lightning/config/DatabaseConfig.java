package com.example.lightning.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    // 데이터베이스 연결 정보 (수정 필요)
    private static final String URL = "jdbc:postgresql://localhost:5432/db_term_project"; // 데이터베이스 URL
    private static final String USERNAME = "postgres"; // 데이터베이스 사용자 이름
    private static final String PASSWORD = "postgres"; // 데이터베이스 비밀번호

    /**
     * 데이터베이스 연결(Connection) 반환
     */
    public static Connection getConnection() throws SQLException {
        // MySQL 드라이버 로드 (JDBC 4.0 이상에서는 선택 사항이지만 명시적으로 추가 가능)
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load PostgreSQL JDBC Driver", e);
        }

        // 연결 생성
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * 데이터베이스 연결 닫기 (Connection, Statement, ResultSet)
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
