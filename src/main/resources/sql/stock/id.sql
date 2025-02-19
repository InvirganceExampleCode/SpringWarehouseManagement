select 
    id,
    barang_id as "productId",
    stok as stock
from tbl_stok
where barang_id = :id