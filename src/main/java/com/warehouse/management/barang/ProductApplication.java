package com.warehouse.management.barang;

import com.invirgance.convirgance.dbms.Query;
import com.invirgance.convirgance.source.ClasspathSource;
import com.warehouse.management.barang.service.DatabaseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductApplication 
{
    public static void main(String[] args) 
    {
        SpringApplication.run(ProductApplication.class, args);
    }
    
    @Bean
    CommandLineRunner commandLineRunner(DatabaseService service) 
    {
        return (args) -> {
            service.update(new Query(new ClasspathSource("/sql/initialize.sql")));
        };
    }

//    @Bean
//    CommandLineRunner commandLineRunner(BarangRepository barangRepository, StockRepository stockRepository) 
//    {
//        return (args) -> {
//            
//            Barang barang1 = Barang.builder()
//                            .id(1l)
//                            .nama("Pakaian Anak")
//                            .berat(1)
//                            .kategori(TipeBarangConstant.TIPE_PAKAIAN)
//                            .build();
//
//            Barang barang2 = Barang.builder()
//                            .id(2l)
//                            .nama("Setrika")
//                            .berat(2)
//                            .kategori(TipeBarangConstant.TIPE_ELEKTRONIK)
//                            .build();
//
//            Stock stock1 = Stock.builder()
//                            .barangId(1l)
//                            .stok(0)
//                            .build();
//
//            Stock stock2 = Stock.builder()
//                            .barangId(2l)
//                            .stok(0)
//                            .build();
//
//            barangRepository.saveAll(List.of(barang1, barang2));
//            stockRepository.saveAll(List.of(stock1, stock2));
//        };
//    }
}
