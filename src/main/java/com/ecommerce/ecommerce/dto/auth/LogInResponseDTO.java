package com.ecommerce.ecommerce.dto.auth;

import com.ecommerce.ecommerce.dto.user.UserEntityDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogInResponseDTO {
    private UserEntityDTO userEntityDTO;
    private String accessToken;
    private String refreshToken;
}
