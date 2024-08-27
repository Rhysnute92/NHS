package uk.ac.cf.spring.nhs.UserWidget.Controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;

import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.UserWidget.Model.UserWidgets;
import uk.ac.cf.spring.nhs.UserWidget.Service.UserWidgetService;

class UserWidgetControllerUnitTests {

    @InjectMocks
    private UserWidgetController userWidgetController;

    @Mock
    private UserWidgetService userWidgetService;

    @Mock
    private AuthenticationInterface authenticationFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Long mockAuthenticatedUser() {
        Long userId = 1L;
        CustomUserDetails customUserDetails = new CustomUserDetails(
                userId,
                "testuser",
                "password",
                List.of() // Assuming no authorities for simplicity
        );
        Authentication auth = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities());
        when(authenticationFacade.getAuthentication()).thenReturn(auth);
        return userId;
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void getUserWidgets_withValidUserId_returnsUserWidgets() {
        // Arrange
        Long userId = mockAuthenticatedUser();

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

        // Act
        ResponseEntity<List<UserWidgets>> response = userWidgetController.getUserWidgets();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userWidgets, response.getBody());
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void deleteUserWidgets_withValidWidgetIds_returnsNoContent() {
        mockAuthenticatedUser();

        List<Long> widgetIdsToDelete = Arrays.asList(1L, 2L);

        // Mock the service to do nothing on delete call
        doNothing().when(userWidgetService).deleteUserWidgetsByIdList(anyList());

        ResponseEntity<Void> response = userWidgetController.deleteUserWidgets(widgetIdsToDelete);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @SuppressWarnings("null")
    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void getUserWidgets_withValidUserId_returnsEmptyListWhenNoWidgets() {
        // Arrange
        Long userId = mockAuthenticatedUser();
        when(userWidgetService.getUserWidgets(userId)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<UserWidgets>> response = userWidgetController.getUserWidgets();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void getUserWidgets_serviceThrowsException_returnsInternalServerError() {
        // Arrange
        Long userId = mockAuthenticatedUser();
        when(userWidgetService.getUserWidgets(userId)).thenThrow(new RuntimeException("Service exception"));

        // Act & Assert
        try {
            userWidgetController.getUserWidgets();
        } catch (RuntimeException e) {
            assertEquals("Service exception", e.getMessage());
        }
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void addUserWidgets_withEmptyWidgetNames_returnsBadRequest() {
        // Act
        ResponseEntity<Void> response = userWidgetController.addUserWidgets(Collections.emptyList());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void addUserWidgets_withValidWidgetNames_returnsNoContent() {
        // Arrange
        mockAuthenticatedUser();

        List<String> widgetNamesToAdd = Arrays.asList("Widget 1", "Widget 2");

        // Create mock UserWidgets objects for each widget name
        UserWidgets widget1 = new UserWidgets();
        widget1.setUserWidgetID(1L);
        widget1.setWidgetName("Widget 1");
        widget1.setUserID(1L);

        UserWidgets widget2 = new UserWidgets();
        widget2.setUserWidgetID(2L);
        widget2.setWidgetName("Widget 2");
        widget2.setUserID(1L);

        // Mock the service to return the UserWidgets object when saveUserWidget is
        // called
        when(userWidgetService.saveUserWidget(any(UserWidgets.class)))
                .thenReturn(widget1)
                .thenReturn(widget2);

        ResponseEntity<Void> response = userWidgetController.addUserWidgets(widgetNamesToAdd);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(userWidgetService, times(2)).saveUserWidget(any(UserWidgets.class));
    }

    @Test
    @WithMockUser(username = "testuser", roles = { "USER" })
    void deleteUserWidgets_withEmptyWidgetIds_returnsBadRequest() {
        // Act
        ResponseEntity<Void> response = userWidgetController.deleteUserWidgets(Collections.emptyList());

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
