package com.fiap.reserva.infra.jdbc.restaurante;

import com.fiap.reserva.domain.entity.HorarioFuncionamento;
import com.fiap.reserva.domain.repository.HorarioFuncionamentoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import com.fiap.reserva.infra.adapter.PrepararQuery;
import com.fiap.reserva.infra.adapter.TipoDados;
import com.fiap.reserva.infra.exception.TechnicalException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HorarioFuncionamentoRepositoryImpl implements HorarioFuncionamentoRepository {

    final Connection connection;
    private PrepararQuery queryExecutor;
    private List<Pair<TipoDados, Object>> parametros;

    public HorarioFuncionamentoRepositoryImpl(Connection connection) {
        this.connection = connection;
        this.parametros = new ArrayList<>();
    }

    @Override
    public void cadastrar(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento) {
        final StringBuilder query = new StringBuilder()
                .append("INSERT INTO tb_horario_funcionamento ")
                .append("(cd_restaurante, hr_inicial, hr_final) ")
                .append("VALUES ")
                .append("(?, ?, ?) ")
                ;

        queryExecutor = new PrepararQuery(Statement.RETURN_GENERATED_KEYS);
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, cnpj.getNumero());
        queryExecutor.adicionaItem(parametros, TipoDados.DATE, horarioFuncionamento.getHorarioInicial());
        queryExecutor.adicionaItem(parametros, TipoDados.DATE, horarioFuncionamento.getHorarioFinal());

        try {
            queryExecutor.construir(connection,query,parametros).execute();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public void alterar(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento) {
        final StringBuilder query = new StringBuilder()
                .append("UPDATE tb_horario_funcionamento ")
                .append("SET hr_inicial = ?, ")
                .append("hr_final = ? ")
                .append("WHERE cd_restaurante = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, cnpj.getNumero());
        queryExecutor.adicionaItem(parametros, TipoDados.DATE, horarioFuncionamento.getHorarioInicial());
        queryExecutor.adicionaItem(parametros, TipoDados.DATE, horarioFuncionamento.getHorarioFinal());

        try {
            queryExecutor.construir(connection,query,parametros).executeUpdate();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public HorarioFuncionamento obter(CnpjVo cnpj, HorarioFuncionamento horarioFuncionamento) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_horario_funcionamento hf ")
                .append("WHERE hf.cd_restaurante = ? ")
                .append("AND hf.hr_inicial = ? ")
                .append("AND hf.hr_final = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, cnpj.getNumero());
        queryExecutor.adicionaItem(parametros, TipoDados.DATE, horarioFuncionamento.getHorarioInicial());
        queryExecutor.adicionaItem(parametros, TipoDados.DATE, horarioFuncionamento.getHorarioFinal());

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                return contruirHorarioFuncionamento(rs);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    protected HorarioFuncionamento contruirHorarioFuncionamento(ResultSet rs) throws SQLException {
        return new HorarioFuncionamento(
                LocalDateTime.parse(rs.getString("hf.hr_inicial")),
                LocalDateTime.parse(rs.getString("hf.hr_final"))
        );
    }
}
