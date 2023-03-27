package ui.datepickers;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

public class BirthdayDatePicker {

    public static final String DATE_PICKER_PAGE = "https://demo.guru99.com/test/";

    private WebElement dateTimeInput;

    private WebElement dateTimeSubmit;

    private WebDriver driver;

    @BeforeMethod
    public void browserStartUp() {
        System.out.println("BirthdayDatePicker.class @BeforeMethod execution");
        System.setProperty("webdriver.chrome.driver", "E:\\Java\\chromedriver.exe");

        driver = new ChromeDriver(new ChromeOptions().addArguments("--remote-allow-origins=*"));
        driver.get(DATE_PICKER_PAGE);
        driver.manage().window().maximize();
        dateTimeInput = driver.findElement(By.xpath("//input[@type=\"datetime-local\"]"));
        dateTimeSubmit = driver.findElement(By.xpath("//input[@type=\"submit\"]"));
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
    public void check() {
        setDateTime(22, 11, 2024, 22, 44);
        dateTimeSubmit.click();
        explicitWait(2000);
        String resultMessage = driver.findElement(By.xpath("/html/body/div[2]")).getText();

        assertEquals(resultMessage, "Your Birth Date is 2024-11-22\nYour Birth Time is 22:44");
    }

    private void setDateTime(int day, int month, int year, int hour, int minute) {
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
        String formattedDate = dateTime.format(formatterDate);
        String formattedTime = dateTime.format(formatterTime);

        dateTimeInput.sendKeys(formattedDate);
        dateTimeInput.sendKeys(Keys.TAB);
        dateTimeInput.sendKeys(formattedTime);
        explicitWait(500);
    }


}
