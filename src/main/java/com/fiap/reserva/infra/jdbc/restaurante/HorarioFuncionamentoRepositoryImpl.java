package com.fiap.reserva.infra.jdbc.restaurante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.infra.exception.TechnicalException;

public class HorarioFuncionamentoRepositoryImpl implements HorarioFuncionamentoRepository {

    final Connection connection;

    public HorarioFuncionamentoRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void cadastrar(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento) {
        final StringBuilder query = new StringBuilder()
                .append("INSERT INTO tb_restaurante_horarios ")
                .append("(cd_restaurante, nm_dia_semana, hr_abertura, hr_fechamento) ")
                .append("VALUES ")
                .append("(?, ?, ?, ?) ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, cnpj.getNumero());
            ps.setString(i++, horarioFuncionamento.getDiaDaSemana().name());
            ps.setTimestamp(i++, Timestamp.valueOf(horarioFuncionamento.getHorarioInicial()));
            ps.setTimestamp(i++, Timestamp.valueOf(horarioFuncionamento.getHorarioFinal()));
            ps.execute();

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public void alterar(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento) {
        final StringBuilder query = new StringBuilder()
                .append("UPDATE tb_restaurante_horarios ")
                .append("SET hr_abertura = ?, ")
                .append("hr_fechamento = ? ")
                .append("WHERE cd_restaurante = ? AND nm_dia_semana = ?");

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setTimestamp(i++, Timestamp.valueOf(horarioFuncionamento.getHorarioInicial()));
            ps.setTimestamp(i++, Timestamp.valueOf(horarioFuncionamento.getHorarioFinal()));

            //WHERE
            ps.setString(i++, cnpj.getNumero());
            ps.setString(i++, horarioFuncionamento.getDiaDaSemana().name());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public boolean existeHorario(CnpjVo cnpj, DayOfWeek diaDaSemana) {
        final String query = "SELECT COUNT(*) > 0 FROM tb_restaurante_horarios WHERE cd_restaurante = ? AND nm_dia_semana = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, cnpj.getNumero());
            ps.setString(2, diaDaSemana.name());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return false;
    }
}
