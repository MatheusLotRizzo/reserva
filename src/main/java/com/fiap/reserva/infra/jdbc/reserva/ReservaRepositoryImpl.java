package com.fiap.reserva.infra.jdbc.reserva;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fiap.reserva.domain.entity.Reserva;
import com.fiap.reserva.domain.entity.Restaurante;
import com.fiap.reserva.domain.entity.Usuario;
import com.fiap.reserva.domain.repository.ReservaRepository;
import com.fiap.reserva.domain.vo.CnpjVo;
import com.fiap.reserva.infra.adapter.PrepararQuery;
import com.fiap.reserva.infra.adapter.TipoDados;
import com.fiap.reserva.infra.exception.TechnicalException;
import javafx.util.Pair;

public class ReservaRepositoryImpl implements ReservaRepository{
    final Connection connection;
    private PrepararQuery queryExecutor;
    private List<Pair<TipoDados, Object>> parametros;

    public ReservaRepositoryImpl(Connection connection) {
        this.connection = connection;
        this.parametros = new ArrayList<>();
    }

    @Override
    public Reserva criar(Reserva reserva){
        final StringBuilder query = new StringBuilder()
                .append("INSERT INTO tb_reserva ")
                .append("(cd_usuario, cd_restaurante, dt_hr_reserva, qt_lugares, ds_status) ")
                .append("VALUES ")
                .append("(?, ?, ?, ?, ?) ")
                ;

        queryExecutor = new PrepararQuery(Statement.RETURN_GENERATED_KEYS);
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getUsuario().getEmailString());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getRestaurante().getCnpjString());
        queryExecutor.adicionaItem(parametros, TipoDados.NUMBER, reserva.getQuantidadeLugares());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getStatus().name());

        try {
            queryExecutor.construir(connection,query,parametros).execute();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return reserva;
    }

    public List<Reserva> buscarTodasPor(Usuario usuario) {
        final List<Reserva> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_reserva r ")
                .append("INNER JOIN tb_usuario u ")
                .append("ON r.cd_usuario = u.rowid ")
                .append("INNER JOIN tb_restaurante re ")
                .append("ON r.cd_cnpj = re.rowid ")
                .append("WHERE u.email = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, usuario.getEmailString());

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                while(rs.next()){
                    list.add(contruirReserva(rs));
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

        return list;
    }

    @Override
    public List<Reserva> buscarTodasPor(Restaurante restaurante) {
        final List<Reserva> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_reserva r ")
                .append("INNER JOIN tb_restaurante re ")
                .append("ON r.cd_restaurante = re.rowid ")
                .append("INNER JOIN tb_usuario u ")
                .append("ON r.cd_usuario = u.rowid ")
                .append("WHERE u.cnpj = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, restaurante.getCnpjString());

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                while(rs.next()){
                    list.add(contruirReserva(rs));
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

        return list;
    }

    @Override
    public List<Reserva> buscarTodasPor(Reserva reserva) {
        final List<Reserva> list = new ArrayList<>();
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_reserva r ")
                .append("INNER JOIN tb_restaurante re ")
                .append("ON r.cd_restaurante = re.rowid ")
                .append("INNER JOIN tb_usuario u ")
                .append("ON r.cd_usuario = u.rowid ")
                .append("WHERE 1 = 1 ")
                ;

        queryExecutor = new PrepararQuery();

        if (reserva.getUsuario() != null) {
            query.append("AND r.cd_usuario = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getUsuario().getEmailString());
        }

        if (reserva.getRestaurante() != null) {
            query.append("AND r.cd_restaurante = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getRestaurante().getCnpjString());
        }

        if (reserva.getStatus() != null) {
            query.append("AND r.dt_hr_reserva = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.DATE, reserva.getDataHora());
        }
        if (reserva.getStatus() != null) {
            query.append("AND r.status = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getStatus().name());
        }
        if (reserva.getQuantidadeLugares() > 0){
            query.append("AND r.qt_lugares = ? ");
            queryExecutor.adicionaItem(parametros, TipoDados.NUMBER, reserva.getQuantidadeLugares());
        }

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                while(rs.next()){
                    list.add(contruirReserva(rs));
                }
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }

        return list;
    }

    @Override
    public Reserva buscar(Reserva reserva) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT * FROM tb_reserva r ")
                .append("INNER JOIN tb_restaurante re ")
                .append("ON r.cd_restaurante = re.cnpj ")
                .append("INNER JOIN tb_usuario u ")
                .append("ON r.cd_usuario = u.email ")
                .append("WHERE r.cd_restaurante = ? ")
                .append("AND r.cd_usuario = ? ")
                .append("AND r.dt_hr_reserva = ? ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getUsuario().getEmailString());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getRestaurante().getCnpjString());
        queryExecutor.adicionaItem(parametros, TipoDados.DATE, reserva.getDataHora());

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                return contruirReserva(rs);
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public Integer obterLotacaoaReserva(Reserva reserva) {
        final StringBuilder query = new StringBuilder()
                .append("SELECT ROUND(SUM(r.qt_lugares)/4,0) total_Mesas FROM tb_reserva r ")
                .append("WHERE r.cd_restaurante = ? ")
                .append("AND r.dt_hr_reserva = ? ")
                .append("AND r.ds_status = 'RESERVADO' ")
                ;

        queryExecutor = new PrepararQuery();
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getRestaurante().getCnpjString());
        queryExecutor.adicionaItem(parametros, TipoDados.DATE, reserva.getDataHora());

        try {
            try (final ResultSet rs = queryExecutor.construir(connection,query,parametros).executeQuery()) {
                return Integer.parseInt(rs.getString("r.total_Mesas"));
            }
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    @Override
    public Reserva alterar(Reserva reserva) {
        final StringBuilder query = new StringBuilder()
                .append("UPDATE tb_reserva ")
                .append("SET qt_lugares = ?, ")
                .append("ds_status = ? ")
                .append("WHERE cd_usuario = ? ")
                .append("AND cd_restaurante = ? ")
                .append("AND dt_hr_reserva = ? ")
                ;

        queryExecutor = new PrepararQuery(Statement.RETURN_GENERATED_KEYS);
        queryExecutor.adicionaItem(parametros, TipoDados.NUMBER, reserva.getQuantidadeLugares());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getStatus().name());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getUsuario().getEmailString());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getRestaurante().getCnpjString());
        queryExecutor.adicionaItem(parametros, TipoDados.DATE, reserva.getDataHora());

        try {
            queryExecutor.construir(connection,query,parametros).executeUpdate();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
        return reserva;
    }

    @Override
    public void excluir(Reserva reserva) {
        final StringBuilder query = new StringBuilder()
                .append("DELETE FROM tb_reserva ")
                .append("WHERE cd_usuario = ? ")
                .append("AND cd_restaurante = ? ")
                .append("AND dt_hr_reserva = ? ")
                ;

        queryExecutor = new PrepararQuery(Statement.RETURN_GENERATED_KEYS);
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getUsuario().getEmailString());
        queryExecutor.adicionaItem(parametros, TipoDados.STRING, reserva.getRestaurante().getCnpjString());
        queryExecutor.adicionaItem(parametros, TipoDados.DATE, reserva.getDataHora());

        try {
            queryExecutor.construir(connection,query,parametros).execute();
        } catch (SQLException e) {
            throw new TechnicalException(e);
        }
    }

    private Reserva contruirReserva(ResultSet rs) throws SQLException {
        return new Reserva(
                new Usuario(rs.getString("u.nome"),rs.getString("u.email")),
                new Restaurante(new CnpjVo(rs.getString("re.cnpj")), rs.getString("re.nome")),
                LocalDateTime.parse(rs.getString("r.dt_hr_reserva")),
                Integer.parseInt(rs.getString("r.qt_lugares"))
        );
    }
}