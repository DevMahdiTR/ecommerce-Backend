package com.ecommerce.ecommerce.service.token;


import com.ecommerce.ecommerce.model.token.ConfirmationToken;
import com.ecommerce.ecommerce.model.user.UserEntity;
import org.jetbrains.annotations.NotNull;

public interface ConfirmationTokenService {


    public ConfirmationToken fetchTokenByToken(final String token);
    public String generateConfirmationToken(@NotNull UserEntity userEntity);
    public void setConfirmedAt(final String token);
    public void saveConfirmationToken(@NotNull ConfirmationToken confirmationToken);

    public String getConfirmationPage();
    public String getAlreadyConfirmedPage();

}
