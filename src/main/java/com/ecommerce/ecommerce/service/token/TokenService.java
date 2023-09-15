package com.ecommerce.ecommerce.service.token;

import com.ecommerce.ecommerce.model.token.Token;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public interface TokenService {



    public Token getTokenByToken(final String token);
    public List<Token> fetchAllValidTokenByUserId(final UUID userId);
    public Token save(@NotNull final Token token);
    public List<Token> saveAll(List<Token> tokens);

}
