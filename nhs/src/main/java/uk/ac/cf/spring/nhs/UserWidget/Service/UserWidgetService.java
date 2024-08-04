package uk.ac.cf.spring.nhs.UserWidget.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidgets;
import uk.ac.cf.spring.nhs.UserWidget.Repository.JpaUserWidgetRepository;

@Service
public class UserWidgetService {

    @Autowired
    private JpaUserWidgetRepository userWidgetRepository;

    /**
     * Retrieves a list of user widgets for a given user ID.
     *
     * @param userID the ID of the user
     * @return a list of UserWidgets associated with the given user ID
     */
    public List<UserWidgets> getUserWidgets(Long UserID) {
        return userWidgetRepository.findAllByUserID(UserID);
    }

    /**
     * Saves a UserWidget object to the repository.
     *
     * @param userWidget the UserWidget object to be saved
     * @return the saved UserWidget object
     */
    public UserWidgets saveUserWidget(UserWidgets UserWidget) {
        return userWidgetRepository.save(UserWidget);
    }
}
