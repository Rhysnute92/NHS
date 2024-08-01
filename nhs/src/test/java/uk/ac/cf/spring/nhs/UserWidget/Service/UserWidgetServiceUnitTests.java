package uk.ac.cf.spring.nhs.UserWidget.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidget;
import uk.ac.cf.spring.nhs.UserWidget.Repository.JpaUserWidgetRepository;

class UserWidgetServiceUnitTests {

    @Mock
    private JpaUserWidgetRepository userWidgetRepository;

    @InjectMocks
    private UserWidgetService userWidgetService;

    /**
     * Initializes the test environment before each test case.
     *
     * This method is annotated with `@BeforeEach` which means it will be
     * executed before each test case. It initializes the test environment by
     * calling `MockitoAnnotations.openMocks(this)`.
     *
     * @return void
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for the getUserWidgets method when the user ID is valid.
     *
     * This test verifies that when the getUserWidgets method is called with a valid
     * user ID,
     * the correct list of user widgets is returned. It also checks that the
     * repository method
     * findAllByUserID is called with the correct user ID.
     *
     * @return void
     */
    @Test
    void getUserWidgets_validUserId_returnsUserWidgets() {
        Long userId = 1L;
        UserWidget widget1 = new UserWidget();
        widget1.setUserID(userId);
        widget1.setWidgetName("Widget 1");

        UserWidget widget2 = new UserWidget();
        widget2.setUserID(userId);
        widget2.setWidgetName("Widget 2");

        when(userWidgetRepository.findAllByUserID(userId)).thenReturn(Arrays.asList(widget1, widget2));

        List<UserWidget> result = userWidgetService.getUserWidgets(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Widget 1", result.get(0).getWidgetName());
        assertEquals("Widget 2", result.get(1).getWidgetName());
        verify(userWidgetRepository).findAllByUserID(userId);
    }

    /**
     * Test case for the getUserWidgets method when the user ID is invalid.
     *
     * This test verifies that when the getUserWidgets method is called with an
     * invalid user ID,
     * the correct empty list of user widgets is returned. It also checks that the
     * repository method
     * findAllByUserID is called with the correct user ID.
     *
     * @param userId the invalid user ID
     * @return void
     */
    @Test
    void getUserWidgets_invalidUserId_returnsEmptyList() {
        Long userId = 999L;
        when(userWidgetRepository.findAllByUserID(userId)).thenReturn(Arrays.asList());

        List<UserWidget> result = userWidgetService.getUserWidgets(userId);

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(userWidgetRepository).findAllByUserID(userId);
    }

    /**
     * Test case for the saveUserWidget method when the user widget is valid.
     *
     * This test verifies that when the saveUserWidget method is called with a valid
     * user widget,
     * the correct saved widget is returned. It also checks that the repository
     * method
     * save is called with the correct user widget.
     *
     * @return void
     */
    @Test
    void saveUserWidget_validUserWidget_returnsSavedWidget() {
        UserWidget widget = new UserWidget();
        widget.setUserID(1L);
        widget.setWidgetName("Widget 1");

        when(userWidgetRepository.save(any(UserWidget.class))).thenReturn(widget);

        UserWidget result = userWidgetService.saveUserWidget(widget);

        assertNotNull(result);
        assertEquals("Widget 1", result.getWidgetName());
        verify(userWidgetRepository).save(widget);
    }

    /**
     * Test case for the getUserWidgets method when the user ID is null.
     *
     * This test verifies that when the getUserWidgets method is called with a null
     * user ID,
     * an IllegalArgumentException is thrown with the message "User ID cannot be
     * null".
     *
     * @return void
     */
    @Test
    void getUserWidgets_nullUserId_throwsException() {
        try {
            userWidgetService.getUserWidgets(null);
        } catch (IllegalArgumentException e) {
            assertEquals("User ID cannot be null", e.getMessage());
        }
    }

    /**
     * Test case for the saveUserWidget method when the user widget is null.
     *
     * This test verifies that when the saveUserWidget method is called with a null
     * user widget,
     * an IllegalArgumentException is thrown with the message "UserWidget cannot be
     * null".
     *
     * @return void
     */
    @Test
    void saveUserWidget_nullUserWidget_throwsException() {
        try {
            userWidgetService.saveUserWidget(null);
        } catch (IllegalArgumentException e) {
            assertEquals("UserWidget cannot be null", e.getMessage());
        }
    }
}
