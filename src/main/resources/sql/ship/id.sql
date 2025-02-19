select 
    id,
    barang_id as "productId",
    shipper_id as "shipperId",
    stok as stock,
    tanggal as date,
    tipe as type
from tbl_ship 
where barang_id = :id