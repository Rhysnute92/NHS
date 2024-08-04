package uk.ac.cf.spring.nhs.Diary.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;

import java.util.List;

@Repository
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Integer> {
    List<DiaryEntry> findByUserId(int userId);
}
