package uk.ac.cf.spring.nhs.Task.Model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.cf.spring.nhs.Task.Model.Enum.Periodicity;

class TaskUnitTests {

    private Task task;

    /**
     * Sets up the test environment by creating a new instance of the Task class.
     * This method is called before each test case is executed.
     */
    @BeforeEach
    void setUp() {
        task = new Task();
    }

    /**
     * Tests the functionality of the setId and getId methods in the Task class.
     * 
     * @param None
     * @return None
     */
    @Test
    void testSetAndGetId() {
        Long id = 1L;
        task.setId(id);
        assertEquals(id, task.getId());
    }

    /**
     * Test the set and get name functionality of the Task class.
     *
     * This test case sets a name for the task using the setName() method and then
     * checks if the getName() method returns the same name.
     *
     * @return void
     */
    @Test
    void testSetAndGetName() {
        String name = "Daily Exercise";
        task.setName(name);
        assertEquals(name, task.getName());
    }

    /**
     * Test case for setting and getting the description of a task.
     *
     * This test verifies that the setDescription() method correctly sets the
     * description of a task
     * and that the getDescription() method returns the same description.
     *
     * @throws AssertionError if the expected description does not match the actual
     *                        description
     */
    @Test
    void testSetAndGetDescription() {
        String description = "Perform daily exercises for improved mobility.";
        task.setDescription(description);
        assertEquals(description, task.getDescription());
    }

    /**
     * Test case for setting and getting the type of a task.
     *
     * This test verifies that the setType() method correctly sets the type of a
     * task
     * and that the getType() method returns the same type.
     *
     * @throws AssertionError if the expected type does not match the actual type
     */
    @Test
    void testSetAndGetType() {
        String type = "Exercise";
        task.setType(type);
        assertEquals(type, task.getType());
    }

    /**
     * Tests the functionality of the setPeriodicity and getPeriodicity methods in
     * the Task class.
     * 
     * This test sets the periodicity of a Task object and then checks if the
     * getPeriodicity method
     * returns the same value. The test uses the Periodicity.DAILY constant as the
     * input value.
     *
     * @return void
     */
    @Test
    void testSetAndGetPeriodicity() {
        Periodicity periodicity = Periodicity.DAILY;
        task.setPeriodicity(periodicity);
        assertEquals(periodicity, task.getPeriodicity());
    }

    // Edge Case Tests

    /**
     * Test case for setting the name of a task to null.
     *
     * This test verifies that the setName() method correctly handles a null input
     * and that the getName() method returns the expected value.
     *
     * @return void
     */
    @Test
    void testSetNameWithNull() {
        task.setName(null);
        assertNull(task.getName());
    }

    /**
     * Test case for setting the description of a task to an empty string.
     *
     * This test verifies that the setDescription() method correctly sets the
     * description of a task
     * to an empty string and that the getDescription() method returns the same
     * empty string.
     *
     * @return void
     */
    @Test
    void testSetDescriptionWithEmptyString() {
        task.setDescription("");
        assertEquals("", task.getDescription());
    }

    /**
     * Test case for setting the type of a task to null.
     *
     * This test verifies that the setType() method correctly handles a null input
     * and that the getType() method returns the expected value.
     *
     * @return void
     */
    @Test
    void testSetTypeWithNull() {
        task.setType(null);
        assertNull(task.getType());
    }

    /**
     * Test case for setting the periodicity of a task to null.
     *
     * This test verifies that the setPeriodicity() method correctly handles a null
     * input
     * and that the getPeriodicity() method returns the expected value.
     *
     * @return void
     */
    @Test
    void testSetPeriodicityWithNull() {
        task.setPeriodicity(null);
        assertNull(task.getPeriodicity());
    }
}
