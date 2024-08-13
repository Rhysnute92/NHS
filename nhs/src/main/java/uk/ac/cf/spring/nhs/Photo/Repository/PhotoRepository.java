package uk.ac.cf.spring.nhs.Photo.Repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findByUserId(long userId, Sort date);
}
