package uk.ac.cf.spring.nhs.Diary.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.nhs.Diary.DTO.MoodDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Entity.Mood;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiaryEntryRepository extends JpaRepository<DiaryEntry, Long> {
    List<DiaryEntry> findByUserId(long userId, Sort sort);

    List<DiaryEntry> findByUserIdAndDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT new uk.ac.cf.spring.nhs.Diary.DTO.MoodDTO(d.mood, d.date) " +
            "FROM DiaryEntry d " +
            "WHERE d.userId = :userId AND d.date BETWEEN :startDate AND :endDate")
    List<MoodDTO> findMoodByUserIdAndDateBetween(@Param("userId") Long userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
