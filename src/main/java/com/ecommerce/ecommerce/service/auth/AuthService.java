package com.ecommerce.ecommerce.service.auth;


import com.ecommerce.ecommerce.dto.auth.*;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    public CustomResponseEntity<RegisterResponseDTO> register(@NotNull final RegisterDTO registerDto) ;
    public CustomResponseEntity<LogInResponseDTO>  login(@NotNull final LoginDTO loginDto);
    public CustomResponseEntity<RefreshTokenResponseDTO> renewAccessToken(final String refreshToken, final String expiredToken);
    public String confirmToken(final String token);

}
