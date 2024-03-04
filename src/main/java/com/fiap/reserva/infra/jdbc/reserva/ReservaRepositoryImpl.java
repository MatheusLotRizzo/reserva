package com.fiap.reserva.infra.jdbc.reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.SituacaoReserva;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.infra.exception.TechnicalException;

public class ReservaRepositoryImpl implements ReservaRepository{
    final Connection connection;
    
    public ReservaRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    public List<Reserva> buscarTodasPor(Usuario usuario) throws BusinessException {
        final List<Reserva> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
            .append("SELECT * FROM tb_reserva r ")
            .append("WHERE r.cd_usuario = ? ")
            ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            ps.setString(1, usuario.getEmailString());
            
            try (final ResultSet rs = ps.executeQuery()) {
                while(rs.next()){

                    list.add(new Reserva(
                        UUID.fromString(rs.getString("r.cd_numero_reserva")),
                        new Usuario(rs.getString("r.cd_usuario")), 
                        new Restaurante(rs.getString("r.cd_restaurante")), 
                        rs.getTimestamp("r.dt_hr_reserva").toLocalDateTime(), 
                        SituacaoReserva.valueOf(rs.getString("r.ds_status"))
                    ));
                }
            } 
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

        return list;
    }

    @Override
    public List<Reserva> buscarTodasPor(Restaurante restaurante) throws BusinessException {
        final List<Reserva> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
            .append("SELECT * FROM tb_reserva r ")
            .append("WHERE r.cd_restaurante = ? ")
            ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            ps.setString(1, restaurante.getCnpjString());
            
            try (final ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    list.add(new Reserva(
                        UUID.fromString(rs.getString("r.cd_numero_reserva")),
                        new Usuario(rs.getString("r.cd_usuario")), 
                        new Restaurante(rs.getString("r.cd_restaurante")), 
                        rs.getTimestamp("r.dt_hr_reserva").toLocalDateTime(), 
                        SituacaoReserva.valueOf(rs.getString("r.ds_status"))
                    ));
                }
            } 
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

        return list;
    }

    @Override
    public List<Reserva> buscarTodasPor(Reserva reserva) throws BusinessException {
        final List<Reserva> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
            .append("SELECT * FROM tb_reserva r ")
            .append("WHERE r.cd_usuario = ? ")
            .append("AND r.cd_restaurante = ? ")
            .append("AND r.dt_hr_reserva = ? ")
            ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            ps.setString(1, reserva.getUsuario().getEmailString());
            ps.setString(2, reserva.getRestaurante().getCnpjString());
            ps.setTimestamp(3, Timestamp.valueOf(reserva.getDataHora()));
            
            try (final ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    list.add(new Reserva(
                        UUID.fromString(rs.getString("r.cd_numero_reserva")),
                        new Usuario(rs.getString("r.cd_usuario")), 
                        new Restaurante(rs.getString("r.cd_restaurante")), 
                        rs.getTimestamp("r.dt_hr_reserva").toLocalDateTime(), 
                        SituacaoReserva.valueOf(rs.getString("r.ds_status"))
                    ));
                }
            } 
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

        return list;
    }

    @Override
    public Reserva buscarPor(Reserva reserva) throws BusinessException {
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_reserva r ")
                .append("WHERE r.cd_usuario = ? ")
                .append("AND r.cd_restaurante")
                .append("AND r.dt_hr_reserva = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i=1;
            ps.setString(i++, reserva.getUsuario().getEmailString());
            ps.setString(i++, reserva.getRestaurante().getCnpjString());
            ps.setTimestamp(i++, Timestamp.valueOf(reserva.getDataHora()));
            
            try (final ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    return new Reserva(
                        UUID.fromString(rs.getString("r.cd_numero_reserva")),
                        new Usuario(rs.getString("r.cd_usuario")), 
                        new Restaurante(rs.getString("r.cd_restaurante")), 
                        rs.getTimestamp("r.dt_hr_reserva").toLocalDateTime(), 
                        SituacaoReserva.valueOf(rs.getString("r.ds_status"))
                    );
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return null;
    }

    @Override
    public Reserva criar(Reserva reserva) {
        final StringBuilder query = new StringBuilder()
        .append("INSERT INTO tb_reserva ")
        .append("(cd_numero_reserva, cd_usuario, cd_restaurante, dt_hr_reserva, qt_lugares, ds_status) ")
        .append("VALUES ")
        .append("(?, ?, ?, ?, ?, ?) ")
        ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, reserva.getNumeroReserva().toString());
            ps.setString(i++, reserva.getUsuario().getEmailString());
            ps.setString(i++, reserva.getRestaurante().getCnpjString());
            ps.setTimestamp(i++, Timestamp.valueOf(reserva.getDataHora()));
            ps.setString(i++, reserva.getSituacao().name());
            ps.execute();

            return reserva;
            
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public Reserva alterar(Reserva reserva) {
        final StringBuilder query = new StringBuilder()
            .append("UPDATE tb_reserva SET ")
            .append("dt_hr_reserva = ?, ")
            .append("ds_status = ? ")
            .append("WHERE cd_numero_reserva = ? ")
            ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setTimestamp(i++, Timestamp.valueOf(reserva.getDataHora()));
            ps.setString(i++, reserva.getSituacao().name());
            
            //WHERE
            ps.setString(i++, reserva.getNumeroReserva().toString());

            ps.executeUpdate();

            return reserva;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public void excluir(Reserva reserva) {
        final StringBuilder query = new StringBuilder()
                .append("DELETE FROM tb_reserva ")
                .append("WHERE cd_numero_reserva = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, reserva.getNumeroReserva().toString());
            
            ps.execute();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }
   
}
