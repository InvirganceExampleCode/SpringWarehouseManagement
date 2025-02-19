/*
 * Copyright 2024 INVIRGANCE LLC

Permission is hereby granted, free of charge, to any person obtaining a copy 
of this software and associated documentation files (the “Software”), to deal 
in the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE.
 */
package com.warehouse.management.barang.service;

import com.invirgance.convirgance.CloseableIterator;
import com.invirgance.convirgance.ConvirganceException;
import com.invirgance.convirgance.dbms.DBMS;
import com.invirgance.convirgance.dbms.Query;
import com.invirgance.convirgance.dbms.QueryOperation;
import com.invirgance.convirgance.dbms.TransactionOperation;
import com.invirgance.convirgance.json.JSONObject;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jbanes
 */
@Service
public class DatabaseService
{
    private final DBMS dbms;

    @Autowired
    public DatabaseService(DataSource source)
    {
        this.dbms = new DBMS(source);
    }
    
    public DBMS getDBMS()
    {
        return this.dbms;
    }
    
    public Iterable<JSONObject> query(Query query)
    {
        return dbms.query(query);
    }
    
    public Iterable<JSONObject> queryById(Query query, long id)
    {
        query.setBinding("id", id);
        
        return dbms.query(query);
    }
    
    public JSONObject first(Query query)
    {
        try(var iterator = (CloseableIterator<JSONObject>)dbms.query(query).iterator())
        {
            return iterator.next();
        }
        catch(Exception e)
        {
            throw new ConvirganceException("Unable to retrieve requested object", e);
        }
    }
    
    public JSONObject firstById(Query query, long id)
    {
        query.setBinding("id", id);
        
        return first(query);
    }
    
    public void updateById(Query query, Long id)
    {
        query.setBinding("id", id);
        
        dbms.update(new QueryOperation(query));
    }
    
    public void deleteById(String table, Long id)
    {
        updateById(new Query("delete from " + table + " where id = :id"), id);
    }
    
    public void update(Query ...queries)
    {
        TransactionOperation transaction = new TransactionOperation();
        
        for(Query query : queries) transaction.add(new QueryOperation(query));
        
        dbms.update(transaction);
    }
    
    public long generateId()
    {
        return first(new Query("select nextval('hibernate_sequence') as \"id\"")).getLong("id");
    }
}
