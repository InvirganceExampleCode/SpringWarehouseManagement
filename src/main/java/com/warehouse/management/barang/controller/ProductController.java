package com.warehouse.management.barang.controller;

import com.invirgance.convirgance.dbms.Query;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.ClasspathSource;
import com.warehouse.management.barang.domain.dto.ResponseData;
import com.warehouse.management.barang.service.DatabaseService;
import com.warehouse.management.barang.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ProductController 
{
    @Autowired
    private DatabaseService database;

    @GetMapping("product")
    public Iterable<JSONObject> getAllProducts() 
    {
        return database.query(new Query(new ClasspathSource("/sql/product/all.sql")));
    }

    @GetMapping("product/{id}")
    public JSONObject getProductById(@PathVariable Long id) 
    {
        Query query = new Query(new ClasspathSource("/sql/product/id.sql"));
        
        query.setBinding("id", id);
        
        return database.first(query);
    }
    
    @PostMapping("product")
    public ResponseEntity<ResponseData<JSONObject>> saveProduct(@Valid @RequestBody JSONObject record, Errors errors) 
    {
        ResponseData<JSONObject> responseData = new ResponseData<>();
        Query barang = new Query(new ClasspathSource("/sql/product/insert.sql"), record);
        Query stok = new Query(new ClasspathSource("/sql/stock/insert.sql"));
        
        long id = database.generateId();
        
        new ProductValidator().validate(record, errors);

        if(errors.hasErrors()) return ResponseData.handleErrors(responseData, errors);
        
        barang.setBinding("id", id);
        stok.setBinding("id", database.generateId());
        stok.setBinding("productId", id);
        
        database.update(barang, stok);
        
        responseData.setStatus(true);
        responseData.setPayload(getProductById(id));
        
        return ResponseEntity.ok(responseData);
    }
    
    @PutMapping("product")
    public ResponseEntity<ResponseData<JSONObject>> updateProduct(@Valid @RequestBody JSONObject record, Errors errors) 
    {
        ResponseData<JSONObject> responseData = new ResponseData<>();
        Query query = new Query(new ClasspathSource("/sql/product/update.sql"), record);

        new ProductValidator().validate(record, errors);
        
        if(errors.hasErrors()) return ResponseData.handleErrors(responseData, errors);
        
        database.updateById(query, record.getLong("id"));

        responseData.setStatus(true);
        responseData.setPayload(getProductById(record.getLong("id")));
        
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("product/{id}")
    public void deleteProduct(@PathVariable Long id) 
    {
        database.updateById(new Query(new ClasspathSource("/sql/stock/delete.sql")), id);
        database.deleteById("tbl_barang", id);
    }

    @GetMapping("product/{id}/ship")
    public Iterable<JSONObject> getProductShip(@PathVariable Long id) 
    {
        return database.queryById(new Query(new ClasspathSource("/sql/ship/id.sql")), id);
    }
}
