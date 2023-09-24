package com.ecommerce.ecommerce.service.auth;


import com.ecommerce.ecommerce.dto.auth.*;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public ResponseEntity<Object> register(@NotNull final RegisterDTO registerDto) ;
    public ResponseEntity<Object>  login(@NotNull final LoginDTO loginDto);
    public ResponseEntity<Object> renewAccessToken(final String refreshToken, final String expiredToken);
    public String confirmToken(final String token);

}
