package com.warehouse.management.barang.validator;

import com.invirgance.convirgance.json.JSONObject;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class ProductValidator implements Validator
{
    @Override
    public boolean supports(Class<?> clazz)
    {
        return JSONObject.class.equals(clazz);
    }
    
    private boolean validateNotEmpty(JSONObject record, String field)
    {
        return (!record.isNull(field) && record.getString(field).trim().length() > 0);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        if(!validateNotEmpty((JSONObject)target, "name")) errors.reject("field.required", "Name is required.");
        if(!validateNotEmpty((JSONObject)target, "category")) errors.reject("field.required", "Category is required.");
        if(!validateNotEmpty((JSONObject)target, "weight")) errors.reject("field.required", "Weight is required.");
        if(((JSONObject)target).getLong("weight") < 1) errors.reject("min.value", "Weight must be greater than 0");
    }
}
