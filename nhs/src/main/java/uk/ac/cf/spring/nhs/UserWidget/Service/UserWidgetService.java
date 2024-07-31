package uk.ac.cf.spring.nhs.UserWidget.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidget;
import uk.ac.cf.spring.nhs.UserWidget.Repository.JpaUserWidgetRepository;

@Service
public class UserWidgetService {

    @Autowired
    private JpaUserWidgetRepository userWidgetRepository;

    public List<UserWidget> getUserWidgets(Long userID) {
        return userWidgetRepository.findAllByUserID(userID);
    }

    public UserWidget saveUserWidget(UserWidget userWidget) {
        return userWidgetRepository.save(userWidget);
    }
}
