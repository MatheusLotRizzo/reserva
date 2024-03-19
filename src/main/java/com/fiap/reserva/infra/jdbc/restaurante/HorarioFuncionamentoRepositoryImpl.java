package com.fiap.reserva.infra.jdbc.restaurante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

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
                .append("(cd_restaurante, hr_abertura, hr_fechamento) ")
                .append("VALUES ")
                .append("(?, ?, ?) ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, cnpj.getNumero());
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
                .append("WHERE cd_restaurante = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setTimestamp(i++, Timestamp.valueOf(horarioFuncionamento.getHorarioInicial()));
            ps.setTimestamp(i++, Timestamp.valueOf(horarioFuncionamento.getHorarioFinal()));

            //WHERE
            ps.setString(i++, cnpj.getNumero());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public HorarioFuncionamento obter(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_restaurante_horarios hf ")
                .append("WHERE hf.cd_restaurante = ? ")
                .append("AND hf.hr_abertura = ? ")
                .append("AND hf.hr_fechamento = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, cnpj.getNumero());
            ps.setTimestamp(i++, Timestamp.valueOf(horarioFuncionamento.getHorarioInicial()));
            ps.setTimestamp(i++, Timestamp.valueOf(horarioFuncionamento.getHorarioFinal()));

            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirHorarioFuncionamento(rs);
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return null;
    }

    protected HorarioFuncionamento construirHorarioFuncionamento(ResultSet rs) throws SQLException {
        return new HorarioFuncionamento(
    		DayOfWeek.valueOf(rs.getString("nm_dia_semana")),
            LocalDateTime.parse(rs.getString("hr_abertura")),
            LocalDateTime.parse(rs.getString("hr_fechamento"))
        );
    }
}
