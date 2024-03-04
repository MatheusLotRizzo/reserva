package com.fiap.reserva.infra.jdbc.avaliacao;

import com.fiap.reserva.domain.entity.Avaliacao;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.repository.AvaliacaoRepository;
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

public class AvaliacaoRepositoryImpl implements AvaliacaoRepository {

    final Connection connection;
    private PrepararQuery queryExecutor;
    private List<Pair<TipoDados, Object>> parametros;

    public AvaliacaoRepositoryImpl(Connection connection) {
        this.connection = connection;
        this.parametros = new ArrayList<>();
    }
    @Override
    public List<Avaliacao> buscarTodos(Restaurante restaurante) {
        final List<Avaliacao> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_avaliacao a ")
                .append("INNER JOIN tb_restaurante re ")
                .append("ON a.cd_restaurante = re.cnpj ")
                .append("INNER JOIN tb_usuario u ")
                .append("ON a.cd_usuario = u.email ")
                .append("WHERE a.cd_restaurante = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, restaurante.getCnpjString());

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                while(rs.next()){
                    list.add(contruirAvaliacao(rs));
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return list;

    }

    @Override
    public Avaliacao avaliar(Avaliacao avaliacao) {
        final StringBuilder query = new StringBuilder()
                .append("INSERT INTO tb_avaliacao ")
                .append("(cd_usuario, cd_restaurante, qt_pontuacao, ds_comentario) ")
                .append("VALUES ")
                .append("(?, ?, ?, ?) ")
                ;

        queryExecutor = new PrepararQuery(Statement.RETURN_GENERATED_KEYS);
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, avaliacao.getUsuario().getEmailString());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, avaliacao.getRestaurante().getCnpjString());
        queryExecutor.adicionaItem(parametros, TipoDados.NUMBER, avaliacao.getPontuacao());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, avaliacao.getComentario());

        try {
            queryExecutor.construir(connection,query,parametros).execute();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return avaliacao;
    }

    private Avaliacao contruirAvaliacao(ResultSet rs) throws SQLException {
        return new Avaliacao(
                new Usuario(
                        rs.getString("u.nome"),
                        rs.getString("u.email")
                ),
                new Restaurante(
                        rs.getString("re.cnpj"),
                        rs.getString("re.nome")
                ),
                Integer.parseInt(rs.getString("a.qt_pontuacao")),
                rs.getString("a.ds_comentario")
        );
    }
}
