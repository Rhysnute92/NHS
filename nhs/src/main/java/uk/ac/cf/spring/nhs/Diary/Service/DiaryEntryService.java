package uk.ac.cf.spring.nhs.Diary.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Repository.DiaryEntryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DiaryEntryService {

    private final DiaryEntryRepository diaryEntryRepository;

    @Autowired
    public DiaryEntryService(DiaryEntryRepository diaryEntryRepository) {
        this.diaryEntryRepository = diaryEntryRepository;
    }

    @Transactional
    public DiaryEntry saveDiaryEntry(DiaryEntry diaryEntry) {
        return diaryEntryRepository.save(diaryEntry);
    }

    @Transactional(readOnly = true)
    public List<DiaryEntry> getAllDiaryEntries() {
        return diaryEntryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<DiaryEntry> getDiaryEntryById(int id) {
        return diaryEntryRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<DiaryEntry> getDiaryEntriesByUserId(int userId) {
        return diaryEntryRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteDiaryEntryById(int id) {
        diaryEntryRepository.deleteById(id);
    }
}
