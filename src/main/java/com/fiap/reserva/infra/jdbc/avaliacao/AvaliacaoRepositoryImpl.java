package com.fiap.reserva.infra.jdbc.avaliacao;

import com.fiap.reserva.domain.entity.*;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
import com.fiap.reserva.infra.exception.TechnicalException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoRepositoryImpl implements AvaliacaoRepository {
    final Connection connection;

    public AvaliacaoRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Avaliacao> buscarTodasPor(Restaurante restaurante) throws BusinessException {
        final List<Avaliacao> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_avaliacao a ")
                .append("WHERE a.cd_restaurante = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            ps.setString(1, restaurante.getCnpjString());

            try (final ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    list.add(new Avaliacao (
                            new Usuario(rs.getString("cd_usuario")),
                            new Restaurante(rs.getString("cd_restaurante")),
                            rs.getInt("qt_pontos"),
                            rs.getString("comentario")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

        return list;
    }

    @Override
    public Avaliacao avaliar(Avaliacao avaliacao) throws BusinessException {
        final StringBuilder query = new StringBuilder()
                .append("INSERT INTO tb_avaliacao ")
                .append("(cd_usuario, cd_restaurante, qt_pontos, ds_comentario) ")
                .append("VALUES ")
                .append("(?, ?, ?, ?) ");

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, avaliacao.getUsuario().getEmailString());
            ps.setString(i++, avaliacao.getRestaurante().getCnpjString());
            ps.setInt(i++, avaliacao.getPontuacao());
            ps.setString(i++, avaliacao.getComentario());
            ps.execute();

            return avaliacao;

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }
}
