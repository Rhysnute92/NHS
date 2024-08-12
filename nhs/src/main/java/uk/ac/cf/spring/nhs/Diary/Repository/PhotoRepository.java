package uk.ac.cf.spring.nhs.Diary.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Entity.Photo;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    List<Photo> findByUserId(long userId, Sort date);
}
