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
}
