package uk.ac.cf.spring.nhs.Diary.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.ac.cf.spring.nhs.Diary.Entity.Photo;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    List<Photo> findByUserId(int userId);
}
