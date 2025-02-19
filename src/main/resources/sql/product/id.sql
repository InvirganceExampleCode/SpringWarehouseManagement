SELECT
    b.id,
    b.nama as name,
    b.kategori as category,
    b.berat as weight,
    s.stok as stock
FROM tbl_barang b
INNER JOIN tbl_stok s on s.barang_id = b.id
WHERE b.id = :id
