package com.fiap.reserva.infra.jdbc.reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.infra.exception.TechnicalException;

public class ReservaRepositoryImpl implements ReservaRepository{
    final Connection connection;
    
    public ReservaRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Reserva criar(Reserva reserva){
        final StringBuilder query = new StringBuilder()
            .append("INSERT INTO tb_reserva ")
            .append("(cd_usuario, cd_restaurante, dt_hr_reserva, qt_lugares, ds_status) ")
            .append("VALUES ")
            .append("(?, ?, ?, ?, ?) ")
            ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            ps.setString(i++, reserva.getUsuario().getEmailString());
            ps.setString(i++, reserva.getRestaurante().getCnpjString());
            //ps.setDate(i++, new java.sql.Date(reserva.getDataHora().atZone(ZoneId.systemDefault()).toInstant()));
            ps.setInt(i++, reserva.getQuantidadeLugares());
            ps.setString(i++, reserva.getStatus().name());
            ps.execute();
            
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return reserva;
    }

    public List<Reserva> buscarTodasPor(Usuario usuario) {
        final List<Reserva> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
            .append("SELECT * FROM tb_reserva r ")
            .append("INNER JOIN tb_usuario u ")
            .append("ON r.cd_usuario = u.rowid ")
            .append("WHERE u.ic_email = ? ")
            ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            ps.setString(1, usuario.getEmailString());
            
            try (final ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    
                }
            } 
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

        return list;
    }

    @Override
    public List<Reserva> buscarTodasPor(Restaurante restaurante) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodasPor'");
    }

    @Override
    public List<Reserva> buscarTodasPor(Reserva reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarTodasPor'");
    }

    @Override
    public Reserva buscar(Reserva reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscar'");
    }

    @Override
    public Reserva alterar(Reserva reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alterar'");
    }

    @Override
    public void excluir(Reserva reserva) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluir'");
    }
}
