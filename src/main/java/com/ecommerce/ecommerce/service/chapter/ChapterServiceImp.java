package com.ecommerce.ecommerce.service.chapter;

import com.ecommerce.ecommerce.model.chapter.Chapter;
import com.ecommerce.ecommerce.repository.ChapterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImp implements ChapterService{

    private final ChapterRepository chapterRepository;

    public ChapterServiceImp(ChapterRepository chapterRepository) {
        this.chapterRepository = chapterRepository;
    }

    @Override
    public void deleteAllChapters(final List<Chapter> chapters) {
        chapterRepository.deleteAllChapters(chapters);
    }
}
