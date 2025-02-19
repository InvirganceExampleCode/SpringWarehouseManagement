package com.warehouse.management.barang.domain.dto;

import com.invirgance.convirgance.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData<T> 
{
    private boolean status;
    private List<String> messages = new ArrayList<>();
    private T payload;
    
    /**
     * Common handler for errors
     * 
     * @param responseData
     * @param errors
     * @return 
     */
    public static ResponseEntity<ResponseData<JSONObject>> handleErrors(ResponseData<JSONObject> responseData, Errors errors)
    {
        for (ObjectError error : errors.getAllErrors()) 
        {
            responseData.getMessages().add(error.getDefaultMessage());
        }

        responseData.setStatus(false);
        responseData.setPayload(null);

        return ResponseEntity.badRequest().body(responseData);
    }
}
