package com.fiap.reserva.infra.adapter;

import com.fiap.reserva.infra.exception.TechnicalException;
import javafx.util.Pair;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrepararQuery {

    private final int statementValue;
    public PrepararQuery(){
        this.statementValue = 0;
    }

    public PrepararQuery(int statement){
        this.statementValue = statement;
    }

    public PreparedStatement construir(final Connection connection,final StringBuilder query,final List<Pair<TipoDados, Object>> parametros) throws SQLException {
        try (final PreparedStatement ps =
                     this.statementValue != 0 ? connection.prepareStatement(query.toString(), this.statementValue)
                                         : connection.prepareStatement(query.toString())
                ) {
            int index = 1;
            for (Pair<TipoDados, Object> item : parametros) {
                ps.setObject(index++, item.getValue());

                if (TipoDados.NUMBER == item.getKey() ) {
                    ps.setInt(index, (Integer) item.getValue());
                } else if (TipoDados.STRING == item.getKey() ) {
                    ps.setString(index, (String) item.getValue());
                } else if (TipoDados.DATE == item.getKey() ) {
                    ps.setDate(3, Date.valueOf( (LocalDate)item.getValue()) );
                } else {
                    throw new IllegalArgumentException("Tipo de dado não suportado: " + item.getKey());
                }
            }
            return ps;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    public void adicionaItem(List<Pair<TipoDados, Object>> lista, TipoDados tipoDados, Object valor){
        if (lista == null)
            lista = new ArrayList<>();

        lista.add(new Pair<>(tipoDados, valor ));
    }

    public void clean(List<Pair<TipoDados, Object>> lista){
        if (lista != null)
            lista.clear();
    }
}
