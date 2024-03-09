create table tb_reserva (
    rowid bigint auto_increment,
    cd_numero_reserva uuid primary key,
    cd_usuario varchar not null,
    cd_restaurante varchar not null,
    dt_hr_reserva TIMESTAMP not null,
    ds_status varchar not null
);

create table tb_usuario (
    rowid bigint auto_increment not null,
    nm_usuario varchar not null,
    ic_telefone varchar not null,
    ic_email varchar primary key
);

create table tb_avaliacao (
    rowid bigint auto_increment primary key,
    cd_usuario varchar not null,
    cd_restaurante varchar not null,
    qt_pontos int,
    ds_comentario varchar
);

create table tb_restaurante (
    rowid bigint auto_increment,
    cd_cnpj varchar primary key,
    nm_restaurante varchar,
    ds_tipo_cozinha varchar,
    qt_capacidade_mesas int
);

create table tb_restaurante_endereco(
    rowid bigint auto_increment primary key,
    cd_restaurante varchar not null,
    cd_cep varchar,
    ds_logradouro varchar,
    ds_numero varchar,
    ds_complemento varchar,
    nm_bairro varchar,
    nm_cidade varchar,
    uf_estado varchar
);

create table tb_restaurante_horarios(
    rowid bigint auto_increment primary key,
    cd_restaurante varchar not null,
    hr_abertura TIME not null,
    hr_fechamento TIME not null
);


ALTER TABLE tb_reserva ADD CONSTRAINT FK_USUARIO_RESERVA 
FOREIGN KEY(cd_usuario) REFERENCES tb_usuario(ic_email);

ALTER TABLE tb_reserva ADD CONSTRAINT FK_RESTAURANTE_RESERVA 
FOREIGN KEY(cd_restaurante) REFERENCES tb_restaurante(cd_cnpj);

ALTER TABLE tb_avaliacao ADD CONSTRAINT FK_USUARIO_AVALIACAO 
FOREIGN KEY(cd_usuario) REFERENCES tb_usuario(ic_email);

ALTER TABLE tb_avaliacao ADD CONSTRAINT FK_RESTAURANTE_AVALIACAO 
FOREIGN KEY(cd_restaurante) REFERENCES tb_restaurante(cd_cnpj);

ALTER TABLE tb_restaurante_endereco ADD CONSTRAINT FK_RESTAURANTE_ENDERECO 
FOREIGN KEY(cd_restaurante) REFERENCES tb_restaurante(cd_cnpj);

ALTER TABLE tb_restaurante_horarios ADD CONSTRAINT FK_RESTAURANTE_HORARIOS_RESTAURANTE
FOREIGN KEY(cd_restaurante) REFERENCES tb_restaurante(cd_cnpj);