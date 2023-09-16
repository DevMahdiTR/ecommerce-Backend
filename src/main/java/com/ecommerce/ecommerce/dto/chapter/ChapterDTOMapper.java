package com.ecommerce.ecommerce.dto.chapter;

import com.ecommerce.ecommerce.model.chapter.Chapter;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ChapterDTOMapper implements Function<Chapter, ChapterDTO> {
    @Override
    public ChapterDTO apply(Chapter chapter) {
        return new ChapterDTO(
                chapter.getId(),
                chapter.getTitle(),
                chapter.getDescription()
        );
    }
}
