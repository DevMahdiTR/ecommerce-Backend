package com.ecommerce.ecommerce.dto.article;

import com.ecommerce.ecommerce.dto.chapter.ChapterDTO;
import com.ecommerce.ecommerce.dto.detail.DetailDTO;
import com.ecommerce.ecommerce.dto.file.FileDataDTO;
import java.util.List;

public record  ArticleDTO (
        long id,
        float price,
        long quantity,
        String layoutDescription,
        String reference,
        List<ChapterDTO> chapters,
        List<DetailDTO> details,
        List<FileDataDTO> files
){
}
