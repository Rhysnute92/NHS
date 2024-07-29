package uk.ac.cf.spring.nhs.Widget.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

public interface JpaWidgetRepository extends JpaRepository<Widget, String> {

}
