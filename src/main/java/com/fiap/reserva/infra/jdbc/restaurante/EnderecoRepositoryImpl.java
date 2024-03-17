package com.fiap.reserva.infra.jdbc.restaurante;

import com.fiap.reserva.domain.repository.EnderecoRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import com.fiap.reserva.infra.exception.TechnicalException;

import java.sql.*;

public class EnderecoRepositoryImpl implements EnderecoRepository {

    final Connection connection;

    public EnderecoRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void cadastrar(CnpjVo cnpj, EnderecoVo enderecoVo) {
        final StringBuilder query = new StringBuilder()
                .append("INSERT INTO tb_restaurante_endereco ")
                .append("(cd_restaurante, cd_cep, ds_logradouro, ds_numero, ds_complemento, nm_bairro, nm_cidade, uf_estado) ")
                .append("VALUES ")
                .append("(?, ?, ?, ?, ?, ?, ?, ? ) ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, cnpj.getNumero());
            ps.setString(i++, enderecoVo.getCep());
            ps.setString(i++, enderecoVo.getLogradouro());
            ps.setString(i++, enderecoVo.getNumero());
            ps.setString(i++, enderecoVo.getComplemento());
            ps.setString(i++, enderecoVo.getBairro());
            ps.setString(i++, enderecoVo.getCidade());
            ps.setString(i++, enderecoVo.getEstado());
            ps.execute();

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public void alterar(CnpjVo cnpj, EnderecoVo enderecoVo) {
        final StringBuilder query = new StringBuilder()
                .append("UPDATE tb_restaurante_endereco ")
                .append("SET cd_cep = ?, ")
                .append("ds_logradouro = ?, ")
                .append("ds_numero = ?, ")
                .append("ds_complemento = ?, ")
                .append("nm_bairro = ?, ")
                .append("nm_cidade = ?, ")
                .append("uf_estado = ? ")
                .append("WHERE cd_restaurante = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, enderecoVo.getCep());
            ps.setString(i++, enderecoVo.getLogradouro());
            ps.setString(i++, enderecoVo.getNumero());
            ps.setString(i++, enderecoVo.getComplemento());
            ps.setString(i++, enderecoVo.getBairro());
            ps.setString(i++, enderecoVo.getCidade());
            ps.setString(i++, enderecoVo.getEstado());

            //WHERE
            ps.setString(i++, cnpj.getNumero());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public EnderecoVo obter(CnpjVo cnpj, EnderecoVo enderecoVo) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_restaurante_endereco e ")
                .append("WHERE e.cd_restaurante = ? ")
                .append("AND e.cd_cep = ? ")
                ;
        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            ps.setString(1, cnpj.getNumero());
            ps.setString(2, enderecoVo.getCep());

            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirEndereco(rs);
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return null;
    }

    protected EnderecoVo construirEndereco(ResultSet rs) throws SQLException {
           	return new EnderecoVo(
            rs.getString("cd_cep"),
            rs.getString("ds_logradouro"),
            rs.getString("ds_numero"),
            rs.getString("ds_complemento"),
            rs.getString("nm_bairro"),
            rs.getString("nm_cidade"),
            rs.getString("uf_estado")
        );
    }
}
