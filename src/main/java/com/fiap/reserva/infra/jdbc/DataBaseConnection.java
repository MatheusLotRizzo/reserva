package com.fiap.reserva.infra.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.fiap.reserva.infra.exception.TechnicalException;
import org.springframework.beans.factory.annotation.Value;

public class DataBaseConnection {
    @Value("${spring.datasource.driverClassName}")
    static String classname;
    @Value("${spring.datasource.url}")
    static String url;
    @Value("${spring.datasource.username}")
    static String username;
    @Value("${spring.datasource.password}")
    static String password;

    public static Connection conectar(){
        try {
            Class.forName (classname);
            return DriverManager.getConnection (url, username,password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new TechnicalException(e);
        }
    }
}

