package ui;

import baseclasses.BaseUITestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.*;

public class RadioButtonsTest extends BaseUITestClass {

    public static final String RADIO_BUTTONS_XPATH = "//input[@type=\"radio\"]";

    private WebElement rbOne;

    private WebElement rbTwo;

    private WebElement rbThree;

    @BeforeMethod
    public void verifyDropdownPageLoaded() {
        System.out.println("RadioButtonsTest.class @BeforeMethod execution");
        driver.findElement(By.xpath("//*[@id=\"menu\"]//a[@href=\"dropdown.html\"]")).click();

        String url = driver.getCurrentUrl();
        WebElement buttonsTabTitle = driver.findElement(By.xpath("//h2[text()=\"Dropdown Menus, Radio Buttons & Checkboxes\"]"));
        rbOne = driver.findElements(By.xpath("//input[@type='radio']")).get(0);
        rbTwo = driver.findElements(By.xpath("//input[@type='radio']")).get(1);
        rbThree = driver.findElements(By.xpath("//input[@type='radio']")).get(2);

        assertEquals(url, "https://automationtesting.co.uk/dropdown.html");
        assertTrue(buttonsTabTitle.isDisplayed());
    }

    @Test
    public void verifyRadioButtonGroupIsNotSelectedByDefault() {
        List<WebElement> radioButtonGroup = driver.findElements(By.xpath(RADIO_BUTTONS_XPATH));

        assertThat(radioButtonGroup)
                .extracting(WebElement::isSelected)
                .containsOnly(false);
    }

    @Test
    public void selectRadioButtonOption() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", rbOne);
        wait.until(ExpectedConditions.elementToBeSelected(rbOne));

        assertTrue(rbOne.isSelected());
    }

    @Test
    public void verifyRadioButtonUnselectsAfterAnotherIsSelected() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", rbOne);
        wait.until(ExpectedConditions.elementToBeSelected(rbOne));

        ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true;", rbTwo);

        assertFalse(rbOne.isSelected());
        assertTrue(rbTwo.isSelected());
    }

    @Test
    public void verifyRadioButtonGroupFunctionalityWithKeyboardNavigation() {
        WebElement radioButtonOneInput = driver.findElement(By.xpath("//label[@for='demo-priority-low']"));
        radioButtonOneInput.click();
        wait.until(ExpectedConditions.elementToBeSelected(rbOne));
        WebElement rbg = driver.findElement(By.xpath("//input[@type=\"radio\"]"));
        rbg.sendKeys(Keys.ARROW_LEFT);
        wait.until(ExpectedConditions.elementToBeSelected(rbThree));

        assertFalse(rbOne.isSelected());
        assertTrue(rbThree.isSelected());
    }


}
