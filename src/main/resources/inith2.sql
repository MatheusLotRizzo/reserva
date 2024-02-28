create table tb_reserva (
    rowid bigint auto_increment primary key,
    cd_usuario bigint not null,
    cd_restaurante bigint not null,
    dt_hr_reserva TIMESTAMP,
    qt_lugares numeric,
    ds_status varchar
);

create table tb_usuario (
    rowid bigint auto_increment primary key,
    nm_usuario varchar not null,
    ic_telefone varchar not null,
    ic_email varchar unique not null
);

create table tb_avaliacao (
    rowid bigint auto_increment primary key,
    cd_usuario bigint not null,
    cd_restaurante bigint not null,
    qt_pontos int,
    ds_comentario varchar
);

create table tb_restaurante (
    rowid bigint auto_increment primary key,
    cd_cnpj varchar unique,
    nm_restaurante varchar,
    ic_email varchar,
    ds_tipo_cozinha varchar
);

create table tb_restaurante_endereco(
    rowid bigint auto_increment primary key,
    cd_restaurante bigint not null,
    cd_cep varchar,
    ds_numero varchar,
    ds_complemento varchar,
    nm_bairro varchar,
    nm_cidade varchar,
    uf_estado varchar
);

create table tb_restaurante_horarios(
    rowid bigint auto_increment primary key,
    cd_restaurante bigint not null,
    hr_abertura TIME not null,
    hr_fechamento TIME not null
);


ALTER TABLE tb_reserva ADD CONSTRAINT FK_USUARIO_RESERVA 
FOREIGN KEY(cd_usuario) REFERENCES tb_usuario(rowid);

ALTER TABLE tb_reserva ADD CONSTRAINT FK_RESTAURANTE_RESERVA 
FOREIGN KEY(cd_restaurante) REFERENCES tb_restaurante(rowid);

ALTER TABLE tb_avaliacao ADD CONSTRAINT FK_USUARIO_AVALIACAO 
FOREIGN KEY(cd_usuario) REFERENCES tb_usuario(rowid);

ALTER TABLE tb_avaliacao ADD CONSTRAINT FK_RESTAURANTE_AVALIACAO 
FOREIGN KEY(cd_restaurante) REFERENCES tb_restaurante(rowid);

ALTER TABLE tb_restaurante_endereco ADD CONSTRAINT FK_RESTAURANTE_ENDERECO 
FOREIGN KEY(cd_restaurante) REFERENCES tb_restaurante(rowid);

ALTER TABLE tb_restaurante_horarios ADD CONSTRAINT FK_RESTAURANTE_HORARIOS_RESTAURANTE
FOREIGN KEY(cd_restaurante) REFERENCES tb_restaurante(rowid);