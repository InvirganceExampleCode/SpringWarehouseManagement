create table if not exists tbl_barang (
    id int primary key,
    berat int not null,
    kategori varchar(255),
    name varchar(255) not null
);

create table if not exists tbl_shipper (
    id int primary key,
    code_shipper varchar(255) not null,
    name varchar(255) not null
);

create table if not exists tbl_ship (
    id int primary key,
    barang_id int not null references tbl_barang,
    shipper_id int not null,
    stok int not null,
    date timestamp,
    tipe varchar(255)
);

create table if not exists tbl_stok (
    id int primary key,
    barang_id int not null references tbl_barang,
    stok int not null
);

create sequence if not exists hibernate_sequence;