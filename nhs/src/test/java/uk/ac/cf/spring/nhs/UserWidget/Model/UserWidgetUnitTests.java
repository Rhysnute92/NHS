package uk.ac.cf.spring.nhs.UserWidget.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UserWidgetUnitTests {

    /**
     * Test case for the get and set methods of the UserWidgetID field in the
     * UserWidget class.
     *
     * This test verifies that the setUserWidgetID method correctly sets the
     * UserWidgetID field in the UserWidget class,
     * and that the getUserWidgetID method correctly retrieves the value of the
     * UserWidgetID field.
     *
     * @throws AssertionError if the retrieved UserWidgetID does not match the set
     *                        value
     */
    @Test
    void testGetAndSetUserWidgetID() {
        UserWidgets userWidget = new UserWidgets();
        Long id = 1L;
        userWidget.setUserWidgetID(id);
        assertEquals(id, userWidget.getUserWidgetID());
    }

    /**
     * Test case for the get and set methods of the UserID field in the UserWidget
     * class.
     *
     * This test verifies that the setUserID method correctly sets the UserID field
     * in the UserWidget class,
     * and that the getUserID method correctly retrieves the value of the UserID
     * field.
     *
     * @throws AssertionError if the retrieved UserID does not match the set value
     */
    @Test
    void testGetAndSetUserID() {
        UserWidgets userWidget = new UserWidgets();
        Long userId = 2L;
        userWidget.setUserID(userId);
        assertEquals(userId, userWidget.getUserID());
    }

    /**
     * Test case for the get and set methods of the widgetName field in the
     * UserWidget class.
     *
     * This test verifies that the setWidgetName method correctly sets the
     * widgetName field in the UserWidget class,
     * and that the getWidgetName method correctly retrieves the value of the
     * widgetName field.
     *
     * @throws AssertionError if the retrieved widgetName does not match the set
     *                        value
     */
    @Test
    void testGetAndSetWidgetName() {
        UserWidgets userWidget = new UserWidgets();
        String widgetName = "Test Widget";
        userWidget.setWidgetName(widgetName);
        assertEquals(widgetName, userWidget.getWidgetName());
    }

    /**
     * Test case for the get and set methods of the position field in the UserWidget
     * class.
     *
     * This test verifies that the setPosition method correctly sets the position
     * field in the UserWidget class,
     * and that the getPosition method correctly retrieves the value of the position
     * field.
     *
     * @throws AssertionError if the retrieved position does not match the set value
     */
    @Test
    void testGetAndSetPosition() {
        UserWidgets userWidget = new UserWidgets();
        Integer position = 1;
        userWidget.setPosition(position);
        assertEquals(position, userWidget.getPosition());
    }
}
