package uk.ac.cf.spring.nhs.Widget.Repository;

import java.util.List;
import java.util.Optional;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

public interface WidgetRepositoryInterface {

    Optional<Widget> findById(String id);

    List<Widget> findAll();

    void save(Widget widget);

    void delete(Widget widget);

    void update(Widget widget);

}
