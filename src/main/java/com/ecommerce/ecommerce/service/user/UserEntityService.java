package com.ecommerce.ecommerce.service.user;


import com.ecommerce.ecommerce.dto.user.UserEntityDTO;
import com.ecommerce.ecommerce.model.user.UserEntity;
import com.ecommerce.ecommerce.utility.CustomResponseEntity;
import com.ecommerce.ecommerce.utility.CustomResponseList;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.UUID;

public interface UserEntityService {
    public CustomResponseEntity<UserEntityDTO> fetchUserById(final UUID userId);
    public CustomResponseList<UserEntityDTO> fetchAllUsers(final long pageNumber);
    public CustomResponseEntity<UserEntityDTO> fetchCurrentUser(final UserDetails userDetails);
    public CustomResponseEntity<String> enableOrDisableUser(@NotNull final UUID userId , final boolean enabled);
    public void enableUserById(final UUID userId);
    public UserEntity getUserEntityById(final UUID userId);
    public UserEntity getUserEntityByEmail(final String email);
    public boolean isEmailRegistered(final String email);
    public boolean isPhoneNumberRegistered(final String phoneNumber);
    public UserEntity saveUser(@NotNull final UserEntity userEntity);
}
