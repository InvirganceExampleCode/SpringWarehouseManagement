update tbl_shipper 
set 
    code_shipper = :shortName, 
    nama = :name 
where id = :id