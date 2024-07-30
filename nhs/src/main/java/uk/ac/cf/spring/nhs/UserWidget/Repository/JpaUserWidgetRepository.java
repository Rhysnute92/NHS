package uk.ac.cf.spring.nhs.UserWidget.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidget;

public interface JpaUserWidgetRepository extends JpaRepository<UserWidget, Long> {
    List<UserWidget> findAllByUserId(Long userId);
}
