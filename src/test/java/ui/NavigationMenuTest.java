package ui;

import baseclasses.BaseUITestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class NavigationMenuTest extends BaseUITestClass {

    @BeforeMethod
    public void verifyDropdownPageLoaded() {
        System.out.println("NavigationMenuTest.class @BeforeMethod execution");
        driver.findElement(By.xpath("//*[@id=\"menu\"]//a[@href=\"dropdown.html\"]")).click();

        String url = driver.getCurrentUrl();
        WebElement buttonsTabTitle = driver.findElement(By.xpath("//h2[text()=\"Dropdown Menus, Radio Buttons & Checkboxes\"]"));

        assertEquals(url, "https://automationtesting.co.uk/dropdown.html");
        assertTrue(buttonsTabTitle.isDisplayed());
    }

        // TODO: add tests when functionality will be fixed
    @Test
    public void test() {
    }

    private String getOutputMessage() {
        return driver.findElement(By.id("outputMessage")).getText();
    }

    private String assertOutputMessage(String tabName) {
        return "You clicked on menu option '" + tabName + "'";
    }
}
