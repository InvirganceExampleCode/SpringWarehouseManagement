package com.warehouse.management.barang.controller;

import com.invirgance.convirgance.dbms.Query;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.ClasspathSource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.warehouse.management.barang.domain.dto.ResponseData;
import com.warehouse.management.barang.service.DatabaseService;
import com.warehouse.management.barang.validator.ShipperValidator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ShipperController 
{
    @Autowired
    private DatabaseService database;

    @GetMapping("shipper")
    public Iterable<JSONObject> getAll() 
    {
        return database.query(new Query(new ClasspathSource("/sql/shipper/all.sql")));
    }

    @GetMapping("shipper/{id}")
    public JSONObject getShipperById(@PathVariable Long id) 
    {
        Query query = new Query(new ClasspathSource("/sql/shipper/id.sql"));
        
        return database.firstById(query, id);
    }

    @PostMapping(value="shipper", consumes="application/json", produces="application/json")
    public ResponseEntity<ResponseData<JSONObject>> saveShipper(@Valid @RequestBody JSONObject record, Errors errors) 
    {
        ResponseData<JSONObject> responseData = new ResponseData<>();
        Query shipper = new Query(new ClasspathSource("/sql/shipper/insert.sql"), record);
        long id = database.generateId();

        new ShipperValidator().validate(record, errors);

        if(errors.hasErrors()) return ResponseData.handleErrors(responseData, errors);
        
        database.updateById(shipper, id);
        
        responseData.setStatus(true);
        responseData.setPayload(getShipperById(id));
        
        return ResponseEntity.ok(responseData);
    }

    @PutMapping(value="shipper", consumes="application/json", produces="application/json")
    public ResponseEntity<ResponseData<JSONObject>> updateShipper(@Valid @RequestBody JSONObject record, Errors errors) 
    {
        ResponseData<JSONObject> responseData = new ResponseData<>();
        Query shipper = new Query(new ClasspathSource("/sql/shipper/update.sql"), record);
        long id = record.getLong("id");

        new ShipperValidator().validate(record, errors);
        
        if(errors.hasErrors()) return ResponseData.handleErrors(responseData, errors);
        
        database.updateById(shipper, id);
        
        responseData.setStatus(true);
        responseData.setPayload(getShipperById(id));
        
        return ResponseEntity.ok(responseData);
    }
    
    @DeleteMapping("shipper/{id}")
    public void deleteShipper(@PathVariable Long id) 
    {
        database.deleteById("tbl_shipper", id);
    }

    @GetMapping("shipper/{id}/shipping")
    public Iterable<JSONObject> getShipByShipper(@PathVariable Long id) 
    {
        var query = new Query(new ClasspathSource("/sql/shipper/shipping.sql"));
        
        return database.queryById(query, id);
    }
}
