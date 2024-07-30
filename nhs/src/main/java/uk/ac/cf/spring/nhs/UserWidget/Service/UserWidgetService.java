package uk.ac.cf.spring.nhs.UserWidget.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidget;
import uk.ac.cf.spring.nhs.UserWidget.Repository.UserWidgetRepositoryInterface;
@Service
public class UserWidgetService {

    @Autowired
    private UserWidgetRepository userWidgetRepository;

    public List<UserWidget> getUserWidgets(String userId) {
        return userWidgetRepository.findByAllByUserId(userId);
    }

    public UserWidget addUserWidget(UserWidget userWidget) {
        return userWidgetRepository.save(userWidget);
    }

    public void removeUserWidget(String userWidgetId) {
        userWidgetRepository.deleteById(userWidgetId);
    }
}
