package uk.ac.cf.spring.nhs.EndtoEnd.Dashboard_widget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import io.github.bonigarcia.wdm.WebDriverManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DashboardWidgetFunctionalTest {

    @Value("${local.server.port}")
    private int port;

    private WebDriver webDriver;
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        webDriver = new ChromeDriver(options);
    }

    @Test
    public void testDashboardWidget() {

        // Step 1: Login
        webDriver.get("http://localhost:" + port + "/login");

        // Find the username and password fields
        WebElement usernameField = webDriver.findElement(By.name("username"));
        WebElement passwordField = webDriver.findElement(By.name("password"));

        // Enter username and password
        usernameField.sendKeys("testUser");
        passwordField.sendKeys("userPW");

        // Submit the login form
        WebElement loginButton = webDriver.findElement(By.cssSelector("input[type='submit']"));
        loginButton.click();

        // Step 2: Navigate to Dashboard
        webDriver.get("http://localhost:" + port + "/dashboard");

        // Wait for the task widget to be present
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement taskWidget = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("task-widget-body")));

        assertTrue(taskWidget.isDisplayed(), "Task widget should be displayed on the dashboard.");

        // Step 5: Interact with the "Update Actions" button to open the popup
        WebElement updateActionsButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.className("complete-task-button")));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", updateActionsButton);

        // Step 6: Verify the popup is displayed
        WebElement taskPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("taskPopup")));
        assertTrue(taskPopup.isDisplayed(),
                "Task popup should be displayed after clicking the 'Update Actions' button.");

        // Step 7: Interact with tasks in the popup
        WebElement taskListContainer = taskPopup.findElement(By.id("taskListContainer"));

        // Locate task cards within the popup
        WebElement taskCard = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("popup-task-card")));

        // Locate the clickable check circle inside the task card
        WebElement checkCircle = taskCard.findElement(By.className("check-circle-wrapper"));

        // Wait until checkCircle is clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(checkCircle));
        checkCircle.click();

        // Step 8: Wait for the 'checked' class to be added to the check-circle-wrapper
        // element
        wait.until(ExpectedConditions.attributeContains(checkCircle, "class", "checked"));

        // Step 9: Wait for the 'completed' class to be added to the popup-task-card
        // element
        wait.until(ExpectedConditions.attributeContains(taskCard, "class", "completed"));

        // Verify the class attributes directly to confirm the completion
        assertTrue(checkCircle.getAttribute("class").contains("checked"),
                "Check circle should have the 'checked' class.");
        assertTrue(taskCard.getAttribute("class").contains("completed"),
                "Task card should have the 'completed' class.");

        ((JavascriptExecutor) webDriver).executeAsyncScript(
                "window.setTimeout(arguments[arguments.length - 1], 3000);");

        // Step 9: Close the popup
        WebElement doneButton = taskPopup.findElement(By.id("DonePopupButton"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", doneButton);

        // Step 10: Verify the popup is closed
        Boolean isTaskPopupOverlayInvisible = wait
                .until(ExpectedConditions.invisibilityOfElementLocated(By.id("taskPopupOverlay")));
        assertTrue(isTaskPopupOverlayInvisible, "Task popup overlay should not be displayed after closing the popup.");

        // Step 11: Verify that the progress circle updates
        WebElement completedTasks = taskWidget.findElement(By.id("completedTasks"));
        WebElement totalTasks = taskWidget.findElement(By.id("totalTasks"));

        WebElement progressCircle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".progress-circle .progress-circle")));

        // Verify that the progress circle stroke-dashoffset has changed
        String strokeDashoffset = progressCircle.getCssValue("stroke-dashoffset");
        assertTrue(!strokeDashoffset.equals("565.5"), "Progress circle stroke-dashoffset should be updated.");

        // Verify that the completed tasks count is updated
        assertEquals("1", completedTasks.getText(), "Completed tasks count should be updated to 1.");
        assertEquals(totalTasks.getText(), "5", "Total tasks count should remain 5.");

    }

    @AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}
