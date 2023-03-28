package ui;

import baseclasses.BaseUITestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CheckboxesTest extends BaseUITestClass {

    private WebElement cbRed;

    private WebElement cbGreen;

    private WebElement cbBlue;

    @BeforeMethod
    public void verifyDropdownPageLoaded() {
        System.out.println("CheckboxesTest.class @BeforeMethod execution");
        driver.findElement(By.xpath("//*[@id=\"menu\"]//a[@href=\"dropdown.html\"]")).click();

        cbRed = driver.findElements(By.xpath("//input[@type=\"checkbox\"]")).get(0);
        cbGreen = driver.findElements(By.xpath("//input[@type=\"checkbox\"]")).get(1);
        cbBlue = driver.findElements(By.xpath("//input[@type=\"checkbox\"]")).get(2);
        String url = driver.getCurrentUrl();
        WebElement buttonsTabTitle = driver.findElement(By.xpath("//h2[text()=\"Dropdown Menus, Radio Buttons & Checkboxes\"]"));

        assertEquals(url, "https://automationtesting.co.uk/dropdown.html");
        assertTrue(buttonsTabTitle.isDisplayed());
    }

    @Test
    public void verifyCheckboxIsSelectedByDefault() {
        assertTrue(cbRed.isSelected());
    }

    @Test
    public void verifyCheckboxGroupIsNotSelectedByDefault() {
        assertFalse(cbGreen.isSelected());
        assertFalse(cbBlue.isSelected());
    }

    @Test
    public void verifyCheckboxCanBeUnselected() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = false;", cbRed);

        assertFalse(cbRed.isSelected());
    }

    @Test
    public void verifyCheckboxGroupCanHaveMultipleSelections() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", cbBlue);
        wait.until(ExpectedConditions.elementToBeSelected(cbBlue));
        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", cbGreen);
        wait.until(ExpectedConditions.elementToBeSelected(cbGreen));

        assertTrue(cbRed.isSelected());
        assertTrue(cbBlue.isSelected());
        assertTrue(cbGreen.isSelected());
    }
}
