package com.ecommerce.ecommerce.utility;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Data
public class CustomResponseEntity <T> {
    private HttpStatus statusString;
    private long status ;
    private LocalDateTime timestamp;
    private T Data;


    public CustomResponseEntity(@NotNull HttpStatus statusString, T data) {
        this.statusString = statusString;
        this.status = statusString.value();
        this.timestamp = LocalDateTime.now();
        Data = data;
    }
}
