package com.ecommerce.ecommerce.dto.detail;

import com.ecommerce.ecommerce.model.detail.Detail;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class DetailDTOMapper implements Function<Detail , DetailDTO> {
    @Override
    public DetailDTO apply(Detail detail) {
        return new DetailDTO(
                detail.getId(),
                detail.getTitle(),
                detail.getDescription()
        );
    }
}
