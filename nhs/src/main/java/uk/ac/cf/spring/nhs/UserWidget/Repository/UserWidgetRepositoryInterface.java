package uk.ac.cf.spring.nhs.UserWidget.Repository;

import java.util.List;

import uk.ac.cf.spring.nhs.Widget.Model.Widget;

public interface UserWidgetRepositoryInterface {

    List<Widget> findByAllByUserId(String userId);

}
