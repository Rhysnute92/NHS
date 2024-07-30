package uk.ac.cf.spring.nhs.UserWidget.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

public interface JpaUserWidgetRepository extends JpaRepository<Widget, String> {

}
