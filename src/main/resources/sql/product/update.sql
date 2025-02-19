update tbl_barang 
set 
    nama = :name, 
    kategori = :category, 
    berat = :weight 
where id = :id
