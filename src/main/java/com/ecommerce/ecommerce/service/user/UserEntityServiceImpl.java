package com.ecommerce.ecommerce.service.user;


import com.ecommerce.ecommerce.dto.user.UserEntityDTO;
import com.ecommerce.ecommerce.dto.user.UserEntityDTOMapper;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.user.UserEntity;
import com.ecommerce.ecommerce.repository.UserEntityRepository;
import com.ecommerce.ecommerce.utility.responses.ResponseHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserEntityServiceImpl implements UserEntityService {
    private final UserEntityRepository userEntityRepository;
    private final UserEntityDTOMapper userEntityDTOMapper;

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository, UserEntityDTOMapper userEntityDTOMapper)
    {
        this.userEntityRepository = userEntityRepository;
        this.userEntityDTOMapper = userEntityDTOMapper;
    }

    @Override
    public  ResponseEntity<Object> fetchUserById(final UUID userId) {
        final UserEntity user = getUserEntityById(userId);
        final UserEntityDTO userEntityDto = userEntityDTOMapper.apply(user);

        return ResponseHandler.generateResponse(userEntityDto , HttpStatus.OK);
    }


    @Override
    public  ResponseEntity<Object> fetchAllUsers(final long pageNumber)
    {
        final Pageable pageable = PageRequest.of((int) pageNumber - 1, 10);


        final List<UserEntityDTO> userEntityFullDTOList = userEntityRepository.fetchAllUsers(pageable).stream().map(userEntityDTOMapper).toList();

        if(userEntityFullDTOList.isEmpty() && pageNumber > 1)
        {
            return fetchAllUsers(1);
        }

        return ResponseHandler.generateResponse(userEntityFullDTOList,HttpStatus.OK,userEntityFullDTOList.size(),userEntityRepository.getTotalUserEntityCount());

    }
    @Override
    public ResponseEntity<Object> fetchCurrentUser(@NotNull final UserDetails userDetails)
    {
        final UserEntity currentUser = getUserEntityByEmail(userDetails.getUsername());
        final UserEntityDTO currentUserDto = userEntityDTOMapper.apply(currentUser);

        return ResponseHandler.generateResponse(currentUserDto , HttpStatus.OK);

    }

    @Override
    public  ResponseEntity<Object> enableOrDisableUser(@NotNull final UUID userId , final boolean enabled)
    {
        UserEntity currentUser = getUserEntityById(userId);
        currentUser.setEnabled(enabled);
        userEntityRepository.save(currentUser);
        final String successResponse = String.format("The user with email : %s  enabled = %s", currentUser.getEmail(),enabled? "true" :"false");

        return ResponseHandler.generateResponse(successResponse, HttpStatus.OK);
    }
    @Override
    public void enableUserById(final UUID userId)
    {
        UserEntity userEntity = getUserEntityById(userId);
        userEntity.setEnabled(true);
        userEntityRepository.save(userEntity);
    }

    @Override
    public boolean isEmailRegistered(final String email)
    {
        return userEntityRepository.isEmailRegistered(email);
    }
    @Override
    public boolean isPhoneNumberRegistered(final String phoneNumber)
    {
        return userEntityRepository.isPhoneNumberRegistered(phoneNumber);
    }
    @Override
    public UserEntity saveUser(@NotNull final UserEntity userEntity)
    {
        return userEntityRepository.save(userEntity);
    }

    @Override
    public UserEntity getUserEntityById(final UUID userId)
    {
        return userEntityRepository.fetchUserWithId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with ID : %s could not be found.", userId)));
    }

    @Override
    public UserEntity getUserEntityByEmail(@NotNull final String userEmail)
    {
        return userEntityRepository.fetchUserWithEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("The user with email : %s could not be found.", userEmail)));
    }

}
