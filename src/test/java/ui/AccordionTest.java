package ui;

import baseclasses.BaseUITestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static utils.TestUtils.explicitWait;

public class AccordionTest extends BaseUITestClass {

    List<WebElement> headers;
    List<WebElement> contents;

    @BeforeMethod
    public void verifyAccordionPageLoaded() {
        System.out.println("AccordionTest.class @BeforeMethod execution");
        driver.findElement(By.xpath("//*[@id=\"menu\"]//a[@href=\"accordion.html\"]")).click();

        setAccordionLinks();

        String url = driver.getCurrentUrl();
        WebElement accordion = driver.findElement(By.xpath("//div[@class=\"accordion\"]"));

        assertEquals(url, "https://automationtesting.co.uk/accordion.html");
        assertTrue(accordion.isDisplayed());
    }

    @AfterMethod
    public void refreshPage() {
        System.out.println("Refreshing the page");
        driver.navigate().refresh();
    }

    @Test
    public void accordionOpensContentWhenClicked() {
        clickHeader(1);

        WebElement content = contents.get(1);

        assertTrue(content.isDisplayed());
    }

    @Test
    public void accordionHidesContentWhenClickedIfOpen() {
        clickHeader(2);
        clickHeader(2);

        WebElement content = contents.get(2);
        assertFalse(content.isDisplayed());
    }

    @Test
    public void multipleAccordionTabsCanStayOpenInSameTime() {
        clickAllHeaders();

        for (WebElement content : contents) {
            assertTrue(content.isDisplayed());
        }
    }

    @Test
    public void allSectionsSuccessfullyExpandedAndClosedAfter() {
        clickAllHeaders();
        clickAllHeaders();

        for (WebElement content : contents) {
            assertFalse(content.isDisplayed());
        }
    }

    private void setAccordionLinks() {
        headers = driver.findElements(By.xpath("//div[contains(@class, \"accordion-header\")]"));
        contents = driver.findElements(By.xpath("//div[contains(@class, \"accordion-content\")]"));
    }

    private void clickHeader(int headerIndex) {
        headers.get(headerIndex).click();
        explicitWait(500);
    }

    private void clickAllHeaders() {
        for (WebElement header : headers) {
            header.click();
        }
        explicitWait(500);
    }
}
