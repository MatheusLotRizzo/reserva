--CREATE TABLES
create table tb_reserva (
    rowid bigint auto_increment not null,
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
    rowid bigint auto_increment not null,
    cd_cnpj varchar primary key,
    nm_restaurante varchar,
    ds_tipo_cozinha varchar,
    qt_capacidade_mesas int
);

create table tb_restaurante_endereco(
    rowid bigint auto_increment not null,
    cd_restaurante varchar primary key,
    cd_cep varchar not null,
    ds_logradouro varchar not null,
    ds_numero varchar not null,
    ds_complemento varchar,
    nm_bairro varchar not null,
    nm_cidade varchar not null,
    uf_estado varchar not null
);

create table tb_restaurante_horarios(
    rowid bigint auto_increment primary key,
    cd_restaurante varchar not null,
    nm_dia_semana varchar not null,
    hr_abertura TIME not null,
    hr_fechamento TIME not null
);
--ALTERS TABLES
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

-- Massa de Dados
INSERT INTO tb_usuario (nm_usuario, ic_telefone, ic_email)  values ('Denis Benjamim','13997279686','denis.benjamim@gmail.com');
INSERT INTO tb_usuario (nm_usuario, ic_telefone, ic_email)  values ('Matheus Rizzo','14999998888','matheus@gmail.com');
INSERT INTO tb_usuario (nm_usuario, ic_telefone, ic_email)  values ('Matheus Rizzo','14999998888','matheus2@gmail.com');
INSERT INTO tb_restaurante (cd_cnpj,nm_restaurante,ds_tipo_cozinha,qt_capacidade_mesas) values ('71736952000116', 'Restaurante Denis Benjamim', 'JAPONESA', 3);
INSERT INTO tb_restaurante (cd_cnpj,nm_restaurante,ds_tipo_cozinha,qt_capacidade_mesas) values ('98376018000197', 'Restaurante Sem Mesas', 'JAPONESA', 0);
INSERT INTO tb_restaurante_endereco (cd_restaurante, cd_cep, ds_logradouro, ds_numero, ds_complemento, nm_bairro, nm_cidade, uf_estado) VALUES ('71736952000116', '11533180', 'RUA SAO VICENTE', '273', 'apto 11', 'Casqueiro', 'Cubatão', 'SP');
INSERT INTO tb_restaurante_horarios (cd_restaurante, nm_dia_semana, hr_abertura, hr_fechamento) VALUES ('71736952000116', 'MONDAY' ,'18:00:00','23:59:59');
INSERT INTO tb_restaurante_horarios (cd_restaurante, nm_dia_semana, hr_abertura, hr_fechamento) VALUES ('71736952000116', 'TUESDAY' ,'18:00:00','23:59:59');
INSERT INTO tb_restaurante_horarios (cd_restaurante, nm_dia_semana, hr_abertura, hr_fechamento) VALUES ('71736952000116', 'WEDNESDAY' ,'18:00:00','23:59:59');
INSERT INTO tb_restaurante_horarios (cd_restaurante, nm_dia_semana, hr_abertura, hr_fechamento) VALUES ('71736952000116', 'THURSDAY' ,'18:00:00','23:59:59');
INSERT INTO tb_restaurante_horarios (cd_restaurante, nm_dia_semana, hr_abertura, hr_fechamento) VALUES ('71736952000116', 'FRIDAY' ,'18:00:00','23:59:59');
INSERT INTO tb_restaurante_horarios (cd_restaurante, nm_dia_semana, hr_abertura, hr_fechamento) VALUES ('71736952000116', 'SATURDAY' ,'12:00:00','18:00:00');
INSERT INTO tb_restaurante_horarios (cd_restaurante, nm_dia_semana, hr_abertura, hr_fechamento) VALUES ('71736952000116', 'SUNDAY' ,'08:00:00','12:00:00');
-- MASSA PARA TESTE INTEGRAÇÃO RESERVA
INSERT INTO tb_reserva (cd_numero_reserva, cd_usuario, cd_restaurante, dt_hr_reserva, ds_status)VALUES ('cd5b81eb-1228-4c80-92e6-dc05b18d5e89', 'denis.benjamim@gmail.com', '71736952000116', '2024-03-13T12:00:00','RESERVADO');
INSERT INTO tb_reserva (cd_numero_reserva, cd_usuario, cd_restaurante, dt_hr_reserva, ds_status)VALUES ('fb93bb65-7e37-4990-8fb0-5a77e620a4ab', 'denis.benjamim@gmail.com', '71736952000116', '2024-03-13T08:00:00','RESERVADO');