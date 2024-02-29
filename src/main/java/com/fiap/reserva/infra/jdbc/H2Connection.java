package com.fiap.reserva.infra.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.fiap.reserva.infra.exception.TechnicalException;

public class H2Connection{

    public static Connection conectar(){
        try {
            Class.forName ("org.h2.Driver");
            return DriverManager.getConnection ("jdbc:h2:mem:reserva;DB_CLOSE_ON_EXIT=FALSE;INIT=RUNSCRIPT FROM 'src/main/resources/inith2.sql'", "sa",""); 
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new TechnicalException(e);
        } 
    }
}

