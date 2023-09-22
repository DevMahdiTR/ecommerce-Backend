package com.ecommerce.ecommerce.service.detail;

import com.ecommerce.ecommerce.model.detail.Detail;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DetailService {

    public void deleteAllDetails(final List<Detail> details);
}
