create table if not exists tbl_barang (
    id int primary key,
    berat int not null,
    kategori varchar(255),
    nama varchar(255) not null
);

create table if not exists tbl_shipper (
    id int primary key,
    code_shipper varchar(255) not null,
    nama varchar(255) not null
);

create table if not exists tbl_ship (
    id int primary key,
    barang_id int not null references tbl_barang,
    shipper_id int not null references tbl_shipper,
    stok int not null,
    tanggal timestamp,
    tipe varchar(255)
);

create table if not exists tbl_stok (
    id int primary key,
    barang_id int not null references tbl_barang,
    stok int not null
);

create sequence if not exists hibernate_sequence start with 5;

delete from tbl_stok where barang_id in (1, 2);
delete from tbl_barang where id in (1, 2);

insert into tbl_barang values (1, 1, 'CLOTHING', 'Children''s Outfit');
insert into tbl_barang values (2, 2, 'APPLIANCES', 'Iron');

insert into tbl_stok values (3, 1, 0);
insert into tbl_stok values (4, 2, 0);

