package com.fiap.reserva.infra.jdbc.restaurante;

import com.fiap.reserva.domain.repository.EnderecoRepository;
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
import java.util.ArrayList;
import java.util.List;

public class EnderecoRepositoryImpl implements EnderecoRepository {

    final Connection connection;
    private PrepararQuery queryExecutor;
    private List<Pair<TipoDados, Object>> parametros;

    public EnderecoRepositoryImpl(Connection connection) {
        this.connection = connection;
        this.parametros = new ArrayList<>();
    }

    @Override
    public void cadastrar(CnpjVo cnpj, EnderecoVo enderecoVo) {
        final StringBuilder query = new StringBuilder()
                .append("INSERT INTO tb_endereco ")
                .append("(cd_restaurante, ds_cep, ds_logradouro, ds_numero, ds_complemento, ds_bairro, ds_cidade, ds_estado) ")
                .append("VALUES ")
                .append("(?, ?, ?, ?, ?, ?, ?, ? ) ")
                ;

        queryExecutor = new PrepararQuery(Statement.RETURN_GENERATED_KEYS);
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, cnpj.getNumero());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getCep());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getLogradouro());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getNumero());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getComplemento());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getBairro());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getCidade());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getEstado());

        try {
            queryExecutor.construir(connection,query,parametros).execute();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public void alterar(CnpjVo cnpj, EnderecoVo enderecoVo) {
        final StringBuilder query = new StringBuilder()
                .append("UPDATE tb_endereco ")
                .append("SET ds_cep = ?, ")
                .append("ds_logradouro = ? ")
                .append("ds_numero = ? ")
                .append("ds_complemento = ? ")
                .append("ds_bairro = ? ")
                .append("ds_cidade = ? ")
                .append("ds_estado = ? ")
                .append("WHERE cd_restaurante = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, cnpj.getNumero());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getCep());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getLogradouro());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getNumero());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getComplemento());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getBairro());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getCidade());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getEstado());

        try {
            queryExecutor.construir(connection,query,parametros).executeUpdate();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    protected EnderecoVo contruirEndereco(ResultSet rs) throws SQLException {
        return new EnderecoVo(
            rs.getString("e.cep"),
            rs.getString("e.ds_logradouro"),
            rs.getString("e.ds_numero"),
            rs.getString("e.ds_complemento"),
            rs.getString("e.ds_bairro"),
            rs.getString("e.ds_cidade"),
            rs.getString("e.ds_estado")
        );
    }
}
