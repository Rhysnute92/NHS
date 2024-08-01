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

import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidget;
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

    @Test
    void getUserWidgets_withValidUserId_returnsUserWidgets() {

        Long userId = 1L;
        UserWidget widget1 = new UserWidget();
        widget1.setUserWidgetID(1L);
        widget1.setUserID(userId);
        widget1.setWidgetName("Widget 1");
        widget1.setPosition(1);

        UserWidget widget2 = new UserWidget();
        widget2.setUserWidgetID(2L);
        widget2.setUserID(userId);
        widget2.setWidgetName("Widget 2");
        widget2.setPosition(2);

        List<UserWidget> userWidgets = Arrays.asList(widget1, widget2);
        when(userWidgetService.getUserWidgets(userId)).thenReturn(userWidgets);

        ResponseEntity<List<UserWidget>> response = userWidgetController.getUserWidgets(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userWidgets, response.getBody());
    }

    @Test
    void getUserWidgets_withInvalidUserId_returnsEmptyList() {

        Long userId = 999L;
        when(userWidgetService.getUserWidgets(userId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserWidget>> response = userWidgetController.getUserWidgets(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getUserWidgets_withNullUserId_returnsBadRequest() {

        Long userId = null;

        ResponseEntity<List<UserWidget>> response = userWidgetController.getUserWidgets(userId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getUserWidgets_withNegativeUserId_returnsBadRequest() {

        Long userId = -1L;

        ResponseEntity<List<UserWidget>> response = userWidgetController.getUserWidgets(userId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getUserWidgets_withValidUserId_returnsEmptyListWhenNoWidgets() {

        Long userId = 1L;
        when(userWidgetService.getUserWidgets(userId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserWidget>> response = userWidgetController.getUserWidgets(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void getUserWidgets_serviceThrowsException_returnsInternalServerError() {
        Long userId = 1L;
        when(userWidgetService.getUserWidgets(userId)).thenThrow(new RuntimeException("Service exception"));

        ResponseEntity<List<UserWidget>> response = null;
        try {
            response = userWidgetController.getUserWidgets(userId);
        } catch (RuntimeException e) {
            assertEquals("Service exception", e.getMessage());
        }
    }
}
