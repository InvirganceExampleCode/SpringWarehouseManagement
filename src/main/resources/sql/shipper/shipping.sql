SELECT 
    b.nama as name, 
    s.stok as stock, 
    s.tanggal as date, 
    s.tipe as type
FROM tbl_ship s 
INNER JOIN tbl_barang b ON b.id = s.barang_id 
WHERE s.shipper_id = :id 
