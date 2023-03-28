package ui;

import baseclasses.BaseUITestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class FileInputTest extends BaseUITestClass {

    @BeforeMethod
    public void verifyFileUploadPageLoaded() {
        System.out.println("FileUploadTest.class @BeforeMethod execution");
        driver.findElement(By.xpath("//*[@id=\"menu\"]//a[@href=\"fileupload.html\"]")).click();

        String url = driver.getCurrentUrl();
        WebElement buttonsTabTitle = driver.findElement(By.xpath("//h2[text()=\"File Upload\"]"));

        assertEquals(url, "https://automationtesting.co.uk/fileupload.html");
        assertTrue(buttonsTabTitle.isDisplayed());
    }

    @Test
    public void verifyFileIsSent() {
        WebElement fileInput = driver.findElement(By.xpath("//input[@type=\"file\"]"));
        WebElement fileSubmit = driver.findElement(By.xpath("//input[@type=\"submit\"]"));
        String filePath = "D:\\Downloads\\tiled.png";
        fileInput.sendKeys(filePath);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        fileSubmit.submit();
    }
}
