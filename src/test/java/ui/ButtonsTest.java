package ui;

import baseclasses.BaseUITestClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ButtonsTest extends BaseUITestClass {

    @BeforeMethod
    public void verifyButtonsPageLoaded() {
        System.out.println("ButtonsTest.class @BeforeMethod execution");
        driver.findElement(By.xpath("//*[@id=\"menu\"]//a[@href=\"buttons.html\"]")).click();

        String url = driver.getCurrentUrl();
        WebElement buttonsTabTitle = driver.findElement(By.xpath("//h2[text()=\"Testing Buttons\"]"));

        assertEquals(url, "https://automationtesting.co.uk/buttons.html");
        assertTrue(buttonsTabTitle.isDisplayed());
    }

    @Test
    public void buttonClickWithXpathIsSuccessful() {
        WebElement firstButton = driver.findElement(By.xpath("//button[@id=\"btn_one\"]"));

        firstButton.click();

        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "You clicked the first button!");
        alert.accept();
    }

    @Test
    public void buttonJavaScriptClickIsSuccessful() {
        WebElement secondButton = driver.findElement(By.cssSelector("#btn_two"));

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", secondButton);

        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "You clicked the second button!");
        alert.accept();
    }

    @Test
    public void buttonClickWithActionIsSuccessful() {
        WebElement thirdButton = driver.findElement(By.id("btn_three"));

        thirdButton.click();

        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "You clicked the third button!");
        alert.accept();
    }

    @Test
    public void buttonIsDisabled() {
        WebElement fourthButton = driver.findElement(By.xpath("//button[@id=\"btn_four\"]"));

        assertFalse(fourthButton.isEnabled());
    }
}
