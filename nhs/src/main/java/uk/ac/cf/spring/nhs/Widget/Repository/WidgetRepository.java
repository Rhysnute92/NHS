package uk.ac.cf.spring.nhs.Widget.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

@Repository
public interface WidgetRepository extends JpaRepository <Widget, String> {
    
}
