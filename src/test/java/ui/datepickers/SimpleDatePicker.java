package ui.datepickers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static utils.TestUtils.explicitWait;

public class SimpleDatePicker {

    public static final String DATE_PICKER_PAGE = "https://demos.telerik.com/kendo-ui/datetimepicker/index";

    private WebElement dateTimeInput;

    private WebDriver driver;

    @BeforeMethod
    public void browserStartUp() {
        System.out.println("SimpleDatePicker.class @BeforeMethod execution");
        System.setProperty("webdriver.chrome.driver", "E:\\Java\\chromedriver.exe");

        driver = new ChromeDriver(new ChromeOptions().addArguments("--remote-allow-origins=*"));
        driver.get(DATE_PICKER_PAGE);
        driver.manage().window().maximize();
        dateTimeInput = driver.findElement(By.id("datetimepicker"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(dateTimeInput));
    }

    @AfterMethod(alwaysRun = true)
    public void browserClose() {
        System.out.println("Closing down the browser");

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (String tab : tabs) {
            driver.switchTo().window(tab);
            driver.close();
        }
    }

    @Test
    public void validDateTimeInput_() {
        setDateTime(11, 22, 2024, 22, 44);

        assertEquals(getDateTimePickerOutput(), "11/22/2024 10:44 PM");
    }

    private void setDateTime(int month, int day, int year, int hour, int minute) {
        dateTimeInput.click();
        dateTimeInput.clear();

        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
        String formattedDateTime = dateTime.format(formatter);

        dateTimeInput.sendKeys(formattedDateTime);
        explicitWait(500);
    }

    private String getDateTimePickerOutput() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        return jse.executeScript("return arguments[0].value;", dateTimeInput).toString();
    }

}
