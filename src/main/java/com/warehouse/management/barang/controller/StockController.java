package com.warehouse.management.barang.controller;

import com.invirgance.convirgance.dbms.Query;
import com.invirgance.convirgance.dbms.QueryOperation;
import com.invirgance.convirgance.dbms.TransactionOperation;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.ClasspathSource;
import com.warehouse.management.barang.constant.ShipTypeConstant;
import com.warehouse.management.barang.service.DatabaseService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class StockController 
{   
    @Autowired
    private DatabaseService database;

    public JSONObject getStockByBarang(Long id)
    {
        return database.firstById(new Query(new ClasspathSource("/sql/stock/id.sql")), id);
    }
    
    @PostMapping("stock")
    public JSONObject saveStock(@RequestBody JSONObject ship)
    {
        JSONObject stock = getStockByBarang(ship.getLong("productId"));
        TransactionOperation transaction = new TransactionOperation();
        
        ship.put("id", database.generateId());
        ship.put("date", new Date());

        switch(ship.getString("type")) 
        {
            case ShipTypeConstant.TYPE_IN:
                stock.put("stock", stock.getLong("stock") + ship.getLong("stock"));
                break;
                
            case ShipTypeConstant.TYPE_OUT:
                if(stock.getLong("stock") < ship.getLong("stock")) 
                {
                    throw new RuntimeException("Stock not enough");
                }
    
                stock.put("stok", stock.getLong("stock") - ship.getLong("stock"));
                break;
                
            default:
                throw new RuntimeException("Type not found: " + ship.getString("type"));
        }

        transaction.add(new QueryOperation(new Query(new ClasspathSource("/sql/ship/insert.sql"), ship)));
        transaction.add(new QueryOperation(new Query(new ClasspathSource("/sql/stock/update.sql"), stock)));
        
        database.getDBMS().update(transaction);
        
        return stock;
    }
}
