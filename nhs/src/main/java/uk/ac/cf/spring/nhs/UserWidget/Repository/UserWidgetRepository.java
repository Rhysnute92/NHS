package uk.ac.cf.spring.nhs.UserWidget.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidget;

public interface UserWidgetRepository extends JpaRepository<UserWidget, String> {
    List<UserWidget> findByAllByUserId(String userId);
}
