package com.fiap.reserva.infra.jdbc.restaurante;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.TipoCozinha;
import com.fiap.reserva.domain.repository.RestauranteRepository;
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

public class RestauranteRepositoryImpl implements RestauranteRepository {

    final Connection connection;
    private PrepararQuery queryExecutor;
    private List<Pair<TipoDados, Object>> parametros;

    private EnderecoRepositoryImpl enderecoRepository;
    private HorarioFuncionamentoRepositoryImpl horarioFuncionamentoRepository;

    public RestauranteRepositoryImpl(Connection connection) {
        this.connection = connection;
        this.parametros = new ArrayList<>();
    }

    @Override
    public Restaurante buscarPorCnpj(CnpjVo cnpj) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_restaurante re ")
                .append("INNER JOIN tb_horario_funcionamento hf ")
                .append("ON re.cnpj = hf.cd_restaurante ")
                .append("INNER JOIN tb_endereco e ")
                .append("ON re.cnpj = e.cd_restaurante ")
                .append("WHERE re.cnpj = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, cnpj.getNumero());

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                return contruirRestaurante(rs);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public Restaurante buscarPorNome(String nome) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_restaurante re ")
                .append("INNER JOIN tb_horario_funcionamento hf ")
                .append("ON re.cnpj = hf.cd_restaurante ")
                .append("INNER JOIN tb_endereco e ")
                .append("ON re.cnpj = e.cd_restaurante ")
                .append("WHERE re.ds_nome = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, nome);

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                return contruirRestaurante(rs);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public List<Restaurante> buscarPorTipoCozinha(TipoCozinha tipoCozinha) {
        final List<Restaurante> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_restaurante re ")
                .append("INNER JOIN tb_horario_funcionamento hf ")
                .append("ON re.cnpj = hf.cd_restaurante ")
                .append("INNER JOIN tb_endereco e ")
                .append("ON re.cnpj = e.cd_restaurante ")
                .append("WHERE re.ds_tipocozinha = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, tipoCozinha.name());

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
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
                .append("SELECT SUM(re.qt_capacidade) total_Mesas FROM tb_restaurante re ")
                .append("WHERE re.cnpj = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, restaurante.getCnpjString());

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                return Integer.parseInt(rs.getString("re.total_Mesas"));
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public List<Restaurante> buscarPorLocalizacao(EnderecoVo enderecoVo) {
        final List<Restaurante> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_restaurante re ")
                .append("INNER JOIN tb_horario_funcionamento hf ")
                .append("ON re.cnpj = hf.cd_restaurante ")
                .append("INNER JOIN tb_endereco e ")
                .append("ON re.cnpj = e.cd_restaurante ")
                .append("WHERE 1 = 1 ")
                ;

        queryExecutor = new PrepararQuery();
        if (enderecoVo.getCep() != null) {
            query.append("AND e.nome = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getCep());
        }
        if (enderecoVo.getLogradouro() != null) {
            query.append("AND e.ds_logradouro = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getLogradouro());
        }
        if (enderecoVo.getBairro() != null) {
            query.append("AND e.ds_bairro = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getBairro());
        }
        if (enderecoVo.getCidade() != null) {
            query.append("AND e.ds_cidade = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getCidade());
        }
        if (enderecoVo.getEstado() != null) {
            query.append("AND e.ds_estado = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.STRING, enderecoVo.getEstado());
        }

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
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
                .append("(cnpj, ds_nome, qt_capacidade, ds_tipocozinha) ")
                .append("VALUES ")
                .append("(?, ?, ?, ?) ")
                ;

        queryExecutor = new PrepararQuery(Statement.RETURN_GENERATED_KEYS);
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, restaurante.getCnpjString());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, restaurante.getNome());
        queryExecutor.adicionaItem(parametros, TipoDados.NUMBER, restaurante.getCapacidade());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, restaurante.getTipoCozinha());

        try {
            queryExecutor.construir(connection,query,parametros).execute();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return restaurante;
    }

    @Override
    public Restaurante alterar(Restaurante restaurante) {
        final StringBuilder query = new StringBuilder()
                .append("UPDATE tb_restaurante ")
                .append("SET ds_nome = ?, ")
                .append("qt_capacidade = ?, ")
                .append("ds_tipocozinha = ? ")
                .append("WHERE cnpj = ? ")
                ;

        queryExecutor = new PrepararQuery(Statement.RETURN_GENERATED_KEYS);
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, restaurante.getCnpjString());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, restaurante.getNome());
        queryExecutor.adicionaItem(parametros, TipoDados.NUMBER, restaurante.getCapacidade());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, restaurante.getTipoCozinha());

        try {
            queryExecutor.construir(connection,query,parametros).executeUpdate();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return restaurante;
    }

    @Override
    public void excluir(CnpjVo cnpj) {
        final StringBuilder query = new StringBuilder()
                .append("DELETE FROM tb_restaurante ")
                .append("WHERE cnpj = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, cnpj.getNumero());

        try {
            queryExecutor.construir(connection,query,parametros).execute();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    private Restaurante contruirRestaurante(ResultSet rs) throws SQLException {
        return new Restaurante(
                rs.getString("re.cnpj"),
                rs.getString("re.ds_nome"),
                enderecoRepository.contruirEndereco(rs),
                horarioFuncionamentoRepository.contruirHorarioFuncionamento(rs),
                Integer.parseInt(rs.getString("re.qt_capacidade")),
                TipoCozinha.valueOf(rs.getString("re.ds_tipocozinha"))
        );
    }
}
