package com.fiap.reserva.infra.jdbc.usuario;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;
import com.fiap.reserva.infra.adapter.PrepararQuery;
import com.fiap.reserva.infra.adapter.TipoDados;
import com.fiap.reserva.infra.exception.TechnicalException;
import javafx.util.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    final Connection connection;
    private PrepararQuery queryExecutor;
    private List<Pair<TipoDados, Object>> parametros;

    public UsuarioRepositoryImpl(Connection connection) {
        this.connection = connection;
        this.parametros = new ArrayList<>();
    }
    @Override
    public List<Usuario> buscarTodos(Usuario usuario) {
        final List<Usuario> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_usuario u ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, usuario.getEmailString());

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                while(rs.next()){
                    list.add(contruirUsuario(rs));
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

        return list;
    }

    @Override
    public Usuario buscar(Usuario usuario) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_usuario r ")
                .append("WHERE u.email = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, usuario.getEmailString());

        if (usuario.getNome() != null) {
            query.append("AND u.nome = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.STRING, usuario.getNome());
        }
        if (usuario.getCelular() != null) {
            query.append("AND u.celular = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.STRING, usuario.getCelular());
        }

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                return contruirUsuario(rs);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public Usuario buscarPorEmail(EmailVo email) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_usuario r ")
                .append("WHERE u.email = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, email.getEndereco());

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                return contruirUsuario(rs);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public Usuario cadastrar(Usuario usuario) {
        final StringBuilder query = new StringBuilder()
                .append("INSERT INTO tb_usuario ")
                .append("(ic_email, nm_usuario, ic_telefone) ")
                .append("VALUES ")
                .append("(?, ?, ?) ")
                ;

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, usuario.getEmailString());
            ps.setString(i++, usuario.getNome());
            ps.setString(i++, usuario.getCelular());
        }  catch (SQLException e) {
            throw new TechnicalException(e);
        }

        return usuario;
    }

    @Override
    public Usuario alterar(Usuario usuario) {
        final StringBuilder query = new StringBuilder()
                .append("UPDATE tb_usuario ")
                .append("SET ds_nome = ?, ")
                .append("ds_celular = ? ")
                .append("WHERE email = ? ")
                ;

        queryExecutor = new PrepararQuery(Statement.RETURN_GENERATED_KEYS);
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, usuario.getNome());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, usuario.getCelular());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, usuario.getEmailString());

        try {
            queryExecutor.construir(connection,query,parametros).executeUpdate();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return usuario;
    }

    @Override
    public void excluir(EmailVo email) {
        final StringBuilder query = new StringBuilder()
                .append("DELETE FROM tb_usuario ")
                .append("WHERE email = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, email.getEndereco());

        try {
            queryExecutor.construir(connection,query,parametros).execute();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    private Usuario contruirUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getString("u.nome"),
                rs.getString("u.email"),
                rs.getString("u.celular")
        );
    }
}
