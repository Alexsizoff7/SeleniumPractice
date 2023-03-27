package ui;

import baseclasses.BaseUITestClass;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static utils.TestUtils.explicitWait;

public class ActionsTest extends BaseUITestClass {

    @BeforeMethod
    public void verifyActionsPageLoaded() {
        System.out.println("ActionsTest.class @BeforeMethod execution");
        driver.findElement(By.xpath("//*[@id=\"menu\"]//a[@href=\"actions.html\"]")).click();

        String url = driver.getCurrentUrl();
        WebElement actionsTitle = driver.findElement(By.xpath("//h2[@id=\"content\"]"));

        assertEquals(url, "https://automationtesting.co.uk/actions.html");
        assertTrue(actionsTitle.isDisplayed());
    }

    @AfterMethod
    public void refreshPage() {
        System.out.println("Refreshing the page");
        driver.navigate().refresh();
    }

    @Test
    public void doubleClickButtonSuccessful() {
        WebElement doubleClickButton = driver.findElements(By.xpath("//*[@id=\"doubClickStartText\"]")).get(0);

        Actions ac = new Actions(driver);
        ac.doubleClick(doubleClickButton).build().perform();
        explicitWait(500);

        assertEquals(doubleClickButton.getText(), "Well Done!");
    }

    @Test
    public void holdAndClickButtonSuccessful() {
        WebElement holdAndClickButton = driver.findElements(By.xpath("//*[@id=\"doubClickStartText\"]")).get(1);

        Actions ac = new Actions(driver);
        ac.keyDown(Keys.SHIFT).click(holdAndClickButton).build().perform();
        explicitWait(500);

        Alert alert = driver.switchTo().alert();

        assertEquals(alert.getText(), "The SHIFT key was pressed!");
        alert.accept();
    }

    @Test
    public void clickAndHoldButtonSuccessful() {
        WebElement buttonToHold = driver.findElement(By.xpath("//div[@id=\"click-box\"]"));

        Actions action = new Actions(driver);
        action.clickAndHold(buttonToHold).build().perform();
        explicitWait(500);

        assertEquals(buttonToHold.getText(), "Keep holding down!");
    }
}
