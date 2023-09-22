package com.ecommerce.ecommerce.service.detail;

import com.ecommerce.ecommerce.model.detail.Detail;
import com.ecommerce.ecommerce.repository.DetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DetailServiceImpl implements  DetailService{

    private final DetailRepository detailRepository;

    public DetailServiceImpl(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    @Override
    public void deleteAllDetails(final List<Detail> details) {
        detailRepository.deleteAllDetails(details);
    }
}
