package uk.ac.cf.spring.nhs.UserWidget.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class UserWidgetUnitTests {

    @Test
    public void testUserWidgetConstructor() {
        UserWidget userWidget = new UserWidget("1", "user1", "widget1", 1);
        assertNotNull(userWidget);
        assertEquals("1", userWidget.getUserId());
        assertEquals("user1", userWidget.getWidgetId());
        assertEquals("widget1", userWidget.getWidgetName());
        assertEquals(1, userWidget.getWidgetOrder());
    }

    @Test
    public void testSetterAndGetters() {
        UserWidget userWidget = new UserWidget();
        userWidget.setUserId("2");
        userWidget.setWidgetId("user2");
        userWidget.setWidgetName("widget2");
        userWidget.setWidgetOrder(2);
        assertEquals("2", userWidget.getUserId());
        assertEquals("user2", userWidget.getWidgetId());
        assertEquals("widget2", userWidget.getWidgetName());
        assertEquals(2, userWidget.getWidgetOrder());
    }
}
