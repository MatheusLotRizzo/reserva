package com.fiap.reserva.infra.jdbc.usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.exception.BusinessException;
import com.fiap.reserva.domain.repository.UsuarioRepository;
import com.fiap.reserva.domain.vo.EmailVo;
import com.fiap.reserva.infra.exception.TechnicalException;

public class UsuarioRepositoryImpl implements UsuarioRepository {

    final Connection connection;

    public UsuarioRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Usuario> buscarTodos() throws BusinessException {
        final List<Usuario> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
                .append("SELECT u.ic_email, u.nm_usuario, u.ic_telefone FROM tb_usuario u");

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            try (final ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    list.add(contruirUsuario(rs));
                }
            }
        }catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return list;
    }

    @Override
    public Usuario buscarPor(EmailVo emailVo) throws BusinessException {
        final StringBuilder query = new StringBuilder()
                .append("SELECT u.ic_email, u.nm_usuario, u.ic_telefone FROM tb_usuario u ")
                .append("WHERE u.ic_email = ? ");

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, emailVo.getEndereco());

            try (final ResultSet rs = ps.executeQuery()) {
                if (rs.next()){
                    return contruirUsuario(rs);
                }
            }
        }catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return null;
    }

    @Override
    public Usuario cadastrar(Usuario usuario) {
        final StringBuilder query = new StringBuilder()
                .append("INSERT INTO tb_usuario ")
                .append("(ic_email, nm_usuario, ic_telefone) ")
                .append("VALUES ")
                .append("(?, ?, ?) ");

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, usuario.getEmailString());
            ps.setString(i++, usuario.getNome());
            ps.setString(i, usuario.getCelular());
            ps.execute();
            return usuario;
        } catch(SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public Usuario alterar(Usuario usuario) {
        final StringBuilder query = new StringBuilder()
                .append("UPDATE tb_usuario ")
                .append("SET nm_usuario = ?, ")
                .append("ic_telefone = ? ")
                .append("WHERE ic_email = ? ");

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i++, usuario.getNome());
            ps.setString(i++, usuario.getCelular());
            ps.setString(i, usuario.getEmailString());
            ps.executeUpdate();
            return usuario;
        }  catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public void excluir(EmailVo email) {
        final StringBuilder query = new StringBuilder()
                .append("DELETE FROM tb_usuario ")
                .append("WHERE ic_email = ? ");

        try (final PreparedStatement ps = connection.prepareStatement(query.toString())) {
            int i = 1;
            ps.setString(i, email.getEndereco());
            ps.execute();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    private Usuario contruirUsuario(ResultSet rs) throws SQLException, BusinessException {
        return new Usuario(
                rs.getString("nm_usuario"),
                rs.getString("ic_email"),
                rs.getString("ic_telefone")
        );
    }
}