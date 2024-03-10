package com.fiap.reserva.infra.jdbc.restaurante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.repository.RestauranteRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.domain.vo.EnderecoVo;
import com.fiap.reserva.infra.exception.TechnicalException;

public class RestauranteRepositoryImpl implements RestauranteRepository {

    final Connection connection;

    private EnderecoRepositoryImpl enderecoRepository;
    private HorarioFuncionamentoRepositoryImpl horarioFuncionamentoRepository;

    public RestauranteRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Restaurante buscarPorCnpj(CnpjVo cnpj) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_restaurante re ")
                .append("INNER JOIN tb_restaurante_horarios hf ")
                .append("ON re.cd_cnpj = hf.cd_restaurante ")
                .append("INNER JOIN tb_restaurante_endereco e ")
                .append("ON re.cd_cnpj = e.cd_restaurante ")
                .append("WHERE re.cd_cnpj = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            ps.setString(1, cnpj.getNumero());

            try (final ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    return contruirRestaurante(rs);
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return null;
    }

    @Override
    public Restaurante buscarPorNome(String nome) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_restaurante re ")
                .append("INNER JOIN tb_restaurante_horarios hf ")
                .append("ON re.cd_cnpj = hf.cd_restaurante ")
                .append("INNER JOIN tb_restaurante_endereco e ")
                .append("ON re.cd_cnpj = e.cd_restaurante ")
                .append("WHERE re.nm_restaurante = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            ps.setString(1, nome);

            try (final ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    return contruirRestaurante(rs);
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return null;
    }

    @Override
    public List<Restaurante> buscarPorTipoCozinha(TipoCozinha tipoCozinha) {
        final List<Restaurante> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_restaurante re ")
                .append("INNER JOIN tb_restaurante_horarios hf ")
                .append("ON re.cd_cnpj = hf.cd_restaurante ")
                .append("INNER JOIN tb_restaurante_endereco e ")
                .append("ON re.cd_cnpj = e.cd_restaurante ")
                .append("WHERE re.ds_tipo_cozinha = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            ps.setString(1, tipoCozinha.name());

            try (final ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    list.add(contruirRestaurante(rs));
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return list;
    }

    @Override
    public Integer obterLotacaoMaximaRestaurante(Restaurante restaurante) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT re.qt_capacidade_mesas total_Mesas FROM tb_restaurante re ")
                .append("WHERE re.cd_cnpj = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            ps.setString(1, restaurante.getCnpjString());

            try (final ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    return Integer.parseInt(rs.getString("re.total_Mesas"));
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return null;
    }

    @Override
    public List<Restaurante> buscarPorLocalizacao(EnderecoVo enderecoVo) {
        final List<Restaurante> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_restaurante re ")
                .append("INNER JOIN tb_restaurante_horarios hf ")
                .append("ON re.cd_cnpj = hf.cd_restaurante ")
                .append("INNER JOIN tb_restaurante_endereco e ")
                .append("ON re.cd_cnpj = e.cd_restaurante ")
                .append("WHERE 1 = 1 ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            
            if (enderecoVo.getCep() != null) {
                query.append("AND e.cd_cep = ? ");
                ps.setString(i++, enderecoVo.getCep());
            }
            if (enderecoVo.getLogradouro() != null) {
                query.append("AND e.ds_logradouro = ? ");
                ps.setString(i++, enderecoVo.getLogradouro());
            }
            if (enderecoVo.getBairro() != null) {
                query.append("AND e.nm_bairro = ? ");
                ps.setString(i++, enderecoVo.getBairro());
            }
            if (enderecoVo.getCidade() != null) {
                query.append("AND e.nm_cidade = ? ");
                ps.setString(i++, enderecoVo.getCidade());
            }
            if (enderecoVo.getEstado() != null) {
                query.append("AND e.uf_estado = ? ");
                ps.setString(i++, enderecoVo.getEstado());
            }

            try (final ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    list.add(contruirRestaurante(rs));
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return list;
    }

    @Override
    public Restaurante cadastrar(Restaurante restaurante) {
        final StringBuilder query = new StringBuilder()
                .append("INSERT INTO tb_restaurante ")
                .append("(cd_cnpj, nm_restaurante, qt_capacidade_mesas, ds_tipo_cozinha) ")
                .append("VALUES ")
                .append("(?, ?, ?, ?) ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, restaurante.getCnpjString());
            ps.setString(i++, restaurante.getNome());
            ps.setInt(i++, restaurante.getCapacidadeMesas());
            ps.setString(i++, restaurante.getTipoCozinha().name());
            ps.execute();

            return restaurante;

        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public Restaurante alterar(Restaurante restaurante) {
        final StringBuilder query = new StringBuilder()
                .append("UPDATE tb_restaurante ")
                .append("SET nm_restaurante = ?, ")
                .append("qt_capacidade_mesas = ?, ")
                .append("ds_tipo_cozinha = ? ")
                .append("WHERE cd_cnpj = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, restaurante.getNome());
            ps.setInt(i++, restaurante.getCapacidadeMesas());
            ps.setString(i++, restaurante.getTipoCozinha().name());

            //WHERE
            ps.setString(i++, restaurante.getCnpjString());

            ps.executeUpdate();

            return restaurante;
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public void excluir(CnpjVo cnpj) {
        final StringBuilder query = new StringBuilder()
                .append("DELETE FROM tb_restaurante ")
                .append("WHERE cd_cnpj = ? ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, cnpj.getNumero());

            ps.execute();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    private Restaurante contruirRestaurante(ResultSet rs) throws SQLException {
        return new Restaurante(
                rs.getString("re.cd_cnpj"),
                rs.getString("re.nm_restaurante"),
                enderecoRepository.construirEndereco(rs),
                Collections.emptyList(), // horarioFuncionamentoRepository.construirHorarioFuncionamento(rs), <- ta errado tem de ser uma lista
                rs.getInt("re.qt_capacidade_mesas"),
                TipoCozinha.valueOf(rs.getString("re.ds_tipo_cozinha"))
        );
    }
}
