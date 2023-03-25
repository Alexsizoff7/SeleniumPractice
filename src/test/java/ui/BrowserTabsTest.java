package ui;

import baseclasses.BaseUITestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static utils.TestUtils.explicitWait;

public class BrowserTabsTest extends BaseUITestClass {

    @BeforeMethod
    public void verifyBrowserTabsPageLoaded() {
        System.out.println("BrowserTabsTest.class @BeforeMethod execution");
        driver.findElement(By.xpath("//*[@id=\"menu\"]//a[@href=\"browserTabs.html\"]")).click();

        String url = driver.getCurrentUrl();
        WebElement browserTabsInstructions = driver.findElement(By.xpath("//h3[contains(text(), \"Open a new tab\")]"));

        assertEquals(url, "https://automationtesting.co.uk/browserTabs.html");
        assertTrue(browserTabsInstructions.isDisplayed());
    }

    @Test
    public void openTabButtonOpensNewWindowTab() {
        WebElement openTabButton = driver.findElement(By.xpath("//form/input[@value=\"Open Tab\"]"));

        openTabButton.click();
        explicitWait(500);

        assertEquals(driver.getWindowHandles().size(), 2);
    }

    @Test
    public void assureNewTabOpensGoogleWebsite() {
        WebElement openTabButton = driver.findElement(By.xpath("//form/input[@value=\"Open Tab\"]"));

        openTabButton.click();
        explicitWait(500);

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

        driver.switchTo().window(tabs.get(1));
        assertEquals(driver.getCurrentUrl(),"https://www.google.com/");
    }
}
