package uk.ac.cf.spring.nhs.UserWidget.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidgets;

public interface JpaUserWidgetRepository extends JpaRepository<UserWidgets, Long> {
    List<UserWidgets> findAllByUserID(Long userID);
}
