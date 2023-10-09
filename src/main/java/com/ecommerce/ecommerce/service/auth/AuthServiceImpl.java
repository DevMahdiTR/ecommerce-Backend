package com.ecommerce.ecommerce.service.auth;

import com.ecommerce.ecommerce.dto.auth.*;
import com.ecommerce.ecommerce.dto.user.UserEntityDTOMapper;
import com.ecommerce.ecommerce.model.role.Role;
import com.ecommerce.ecommerce.model.token.ConfirmationToken;
import com.ecommerce.ecommerce.model.token.RefreshToken;
import com.ecommerce.ecommerce.model.token.Token;
import com.ecommerce.ecommerce.model.token.TokenType;
import com.ecommerce.ecommerce.model.user.UserEntity;
import com.ecommerce.ecommerce.service.email.EmailSenderService;
import com.ecommerce.ecommerce.service.role.RoleService;
import com.ecommerce.ecommerce.service.token.ConfirmationTokenService;
import com.ecommerce.ecommerce.service.token.RefreshTokenService;
import com.ecommerce.ecommerce.service.token.TokenService;
import com.ecommerce.ecommerce.service.user.UserEntityService;
import com.ecommerce.ecommerce.security.jwt.JWTService;
import com.ecommerce.ecommerce.utility.responses.ResponseHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;


@Service
public class AuthServiceImpl  implements  AuthService{

    private final UserEntityService userEntityService;
    private final UserEntityDTOMapper userEntityDTOMapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final RefreshTokenService refreshTokenService;
    private final TokenService tokenService;
    private final EmailSenderService emailSenderService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;



    public AuthServiceImpl(UserEntityService userEntityService, UserEntityDTOMapper userEntityDTOMapper, RoleService roleService, PasswordEncoder passwordEncoder, ConfirmationTokenService confirmationTokenService, RefreshTokenService refreshTokenService, TokenService tokenService, EmailSenderService emailSenderService, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userEntityService = userEntityService;
        this.userEntityDTOMapper = userEntityDTOMapper;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.refreshTokenService = refreshTokenService;
        this.tokenService = tokenService;
        this.emailSenderService = emailSenderService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseEntity<Object> register(@NotNull final RegisterDTO registerDto) {
        if (userEntityService.isEmailRegistered(registerDto.getEmail())) {
            throw new IllegalArgumentException("Sorry, that email is already taken. Please choose a different one.");
        }

        if (userEntityService.isPhoneNumberRegistered(registerDto.getPhoneNumber())) {
            throw new IllegalArgumentException("Sorry, that phone number is already taken. Please choose a different one.");
        }
        Role role = roleService.fetchRoleByName("CLIENT");
        UserEntity user = new UserEntity();
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail().toLowerCase());
        user.setAddress(registerDto.getAddress());
        user.setPhoneNumber(registerDto.getPhoneNumber());
        user.setCreatingDate(new Date());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(role);

        UserEntity savedUser = userEntityService.saveUser(user);

        String confirmationToken = confirmationTokenService.generateConfirmationToken(savedUser);
        String refreshToken = refreshTokenService.generateRefreshToken(savedUser);
        String link = "http://localhost:8080/api/v1/auth/confirm?token=" + confirmationToken;
        emailSenderService.sendEmail(savedUser.getEmail(),"Confirmation email" , emailSenderService.emailTemplateConfirmation(savedUser.getFirstName(),link));

        final RegisterResponseDTO registerResponse = RegisterResponseDTO
                .builder()
                .confirmationToken(confirmationToken)
                .refreshToken(refreshToken)
                .userEntityDTO(userEntityDTOMapper.apply(savedUser))
                .build();

        return ResponseHandler.generateResponse(registerResponse , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> login(@NotNull LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userEntityService.getUserEntityByEmail(loginDto.getEmail());
        revokeAllUserAccessTokens(user);
        revokeAllUserRefreshToken(user);
        String jwtAccessToken = revokeGenerateAndSaveToken(user);
        String jwtRefreshToken = refreshTokenService.generateRefreshToken(user);


        final LogInResponseDTO logInResponse = LogInResponseDTO
                .builder()
                .userEntityDTO(userEntityDTOMapper.apply(user))
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();
        return ResponseHandler.generateResponse( logInResponse,HttpStatus.OK);
    }

    @Override
    @Transactional
    public String confirmToken(String token)
    {
        ConfirmationToken confirmationToken = confirmationTokenService.fetchTokenByToken(token);
        if(confirmationToken.getConfirmedAt() != null)
        {
            return confirmationTokenService.getAlreadyConfirmedPage();
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if(expiredAt.isBefore(LocalDateTime.now()))
        {
            throw new  IllegalStateException("Confirmation token expired.");
        }

        confirmationTokenService.setConfirmedAt(token);
        userEntityService.enableUserById(confirmationToken.getUserEntity().getId());
        return confirmationTokenService.getConfirmationPage();
    }

    @Override
    public ResponseEntity<Object> renewAccessToken(final String refreshToken , final String expiredToken)
    {
        final Token currentToken = tokenService.getTokenByToken(expiredToken);
        final UserEntity currentUser = currentToken.getUserEntity();
        final RefreshToken currentRefreshToken = refreshTokenService.fetchRefreshTokenByToken(refreshToken);
        final boolean  isRefreshTokenValid = refreshTokenService.validateRefreshToken(refreshToken);
        if(currentRefreshToken.getUserEntity().getId() != currentUser.getId())
        {
            throw new IllegalStateException("The access token and refresh token u provided are not compatible.");
        }
        revokeAllUserAccessTokens(currentUser);
        String jwtAccessToken = revokeGenerateAndSaveToken(currentUser);
        final RefreshTokenResponseDTO refreshTokenResponse = RefreshTokenResponseDTO
                .builder()
                .accessToken(jwtAccessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseHandler.generateResponse(refreshTokenResponse , HttpStatus.OK);
    }

    private String revokeGenerateAndSaveToken(UserEntity user) {
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserAccessTokens(user);
        saveUserAccessToken(user, jwtToken);

        return jwtToken;
    }
    private void saveUserAccessToken(@NotNull UserEntity userEntity, @NotNull String jwtToken) {
        var token = Token.builder()
                .userEntity(userEntity)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenService.save(token);
    }

    private void revokeAllUserAccessTokens(@NotNull UserEntity userEntity) {
        var validUserTokens = tokenService.fetchAllValidTokenByUserId(userEntity.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenService.saveAll(validUserTokens);
    }
    private void revokeAllUserRefreshToken(@NotNull UserEntity userEntity)
    {
        var validRefreshTokens = refreshTokenService.fetchAllRefreshTokenByUserId(userEntity.getId());
        if(validRefreshTokens.isEmpty())
        {
            return;
        }
        validRefreshTokens.forEach(refreshToken -> {
            refreshToken.setRevoked(true);
            refreshToken.setExpired(true);
        });
        refreshTokenService.saveAll(validRefreshTokens);
    }
}
