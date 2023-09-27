package com.ecommerce.ecommerce.utility.responses;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(Object responseObj, HttpStatus status)
    {
        Map<String , Object> map = new HashMap<>();
        map.put("status" , status.value());
        map.put("message" , "success");
        map.put("timestamp" , LocalDateTime.now());
        map.put("data" , responseObj);
        return new ResponseEntity<>(map , status);
    }

    public static ResponseEntity<Object> generateResponse(Object responseObj , HttpHeaders headers, HttpStatus status)
    {
        Map<String , Object> map = new HashMap<>();
        map.put("status" , status.value());
        map.put("message" , "success");
        map.put("timestamp" , LocalDateTime.now());
        map.put("data" , responseObj);
        return new ResponseEntity<>(map , headers , status);
    }

    public static ResponseEntity<Object> generateResponse(Object responseObj ,HttpStatus status ,  long currentLength , long total )
    {
        Map<String , Object> map = new HashMap<>();
        map.put("status" , status.value());
        map.put("message" , "success");
        map.put("timestamp" , LocalDateTime.now());
        map.put("data" , responseObj);
        map.put("currentLength" , currentLength);
        map.put("total" , total);
        return new ResponseEntity<>(map , status);
    }
}
