package com.ecommerce.ecommerce.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiError {
    private int status;
    private LocalDateTime timestamp;
    private String message;
    private List<String> errors;
}
