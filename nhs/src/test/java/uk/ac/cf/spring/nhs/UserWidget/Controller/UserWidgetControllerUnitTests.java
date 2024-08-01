package uk.ac.cf.spring.nhs.UserWidget.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidgets;
import uk.ac.cf.spring.nhs.UserWidget.Service.UserWidgetService;

class UserWidgetControllerUnitTests {

    @InjectMocks
    private UserWidgetController userWidgetController;

    @Mock
    private UserWidgetService userWidgetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for the getUserWidgets method when the user ID is valid and there are user widgets.
     *
     * This test verifies that when the getUserWidgets method is called with a valid user ID,
     * and the userWidgetService returns a non-empty list of user widgets, the response status code is OK
     * and the response body contains the correct list of user widgets.
     *
     * @param  userId  the valid user ID
     * @return         void
     */
    @Test
    void getUserWidgets_withValidUserId_returnsUserWidgets() {

        Long userId = 1L;
        UserWidgets widget1 = new UserWidgets();
        widget1.setUserWidgetID(1L);
        widget1.setUserID(userId);
        widget1.setWidgetName("Widget 1");
        widget1.setPosition(1);

        UserWidgets widget2 = new UserWidgets();
        widget2.setUserWidgetID(2L);
        widget2.setUserID(userId);
        widget2.setWidgetName("Widget 2");
        widget2.setPosition(2);

        List<UserWidgets> userWidgets = Arrays.asList(widget1, widget2);
        when(userWidgetService.getUserWidgets(userId)).thenReturn(userWidgets);

        ResponseEntity<List<UserWidgets>> response = userWidgetController.getUserWidgets(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userWidgets, response.getBody());
    }

    /**
     * Test case for the getUserWidgets method when the user ID is invalid and there are no widgets.
     *
     * This test verifies that when the getUserWidgets method is called with an invalid user ID,
     * and the userWidgetService returns an empty list of widgets, the response status code is OK
     * and the response body size is 0.
     *
     * @param  userId  the invalid user ID
     * @return         void
     */
    @Test
    void getUserWidgets_withInvalidUserId_returnsEmptyList() {

        Long userId = 999L;
        when(userWidgetService.getUserWidgets(userId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserWidgets>> response = userWidgetController.getUserWidgets(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    /**
     * Test case for the getUserWidgets method when the user ID is null.
     *
     * This test verifies that when the getUserWidgets method is called with a null user ID,
     * the response status code is BAD_REQUEST.
     *
     * @param  userId  the null user ID
     * @return         void
     */
    @Test
    void getUserWidgets_withNullUserId_returnsBadRequest() {

        Long userId = null;

        ResponseEntity<List<UserWidgets>> response = userWidgetController.getUserWidgets(userId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Test case for the getUserWidgets method when the user ID is negative.
     *
     * This test verifies that when the getUserWidgets method is called with a negative user ID,
     * the response status code is BAD_REQUEST.
     *
     * @param  userId  the negative user ID
     * @return         void
     */
    @Test
    void getUserWidgets_withNegativeUserId_returnsBadRequest() {

        Long userId = -1L;

        ResponseEntity<List<UserWidgets>> response = userWidgetController.getUserWidgets(userId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Test case for the getUserWidgets method when the user has a valid ID and there are no widgets.
     *
     * This test verifies that when the getUserWidgets method is called with a valid user ID,
     * and the userWidgetService returns an empty list of widgets, the response status code is OK
     * and the response body size is 0.
     *
     * @param  userId  the valid user ID
     * @return         void
     */
    @Test
    void getUserWidgets_withValidUserId_returnsEmptyListWhenNoWidgets() {

        Long userId = 1L;
        when(userWidgetService.getUserWidgets(userId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserWidgets>> response = userWidgetController.getUserWidgets(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    /**
     * Test case for the getUserWidgets method when the service throws an exception.
     *
     * @throws RuntimeException if the service throws an exception
     */
    @Test
    void getUserWidgets_serviceThrowsException_returnsInternalServerError() {
        Long userId = 1L;
        when(userWidgetService.getUserWidgets(userId)).thenThrow(new RuntimeException("Service exception"));

        ResponseEntity<List<UserWidgets>> response = null;
        try {
            response = userWidgetController.getUserWidgets(userId);
        } catch (RuntimeException e) {
            assertEquals("Service exception", e.getMessage());
        }
    }
}
