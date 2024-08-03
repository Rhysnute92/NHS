package uk.ac.cf.spring.nhs.Diary.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.cf.spring.nhs.Diary.Entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    // You can define custom query methods here if needed
}
