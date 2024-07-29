package uk.ac.cf.spring.nhs.UserWidget.Repository;

import java.util.List;
import java.util.Optional;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

public interface UserWidgetRepositoryInterface {

    List<Widget> findAll();

    Optional<Widget> findById(String widgetId);

    void save(Widget widget);

    void deleteByUserIdAndWidgetId(String userId, String widgetId);

}
