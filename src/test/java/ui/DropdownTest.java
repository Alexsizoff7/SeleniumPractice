package ui;

import baseclasses.BaseUITestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static utils.TestUtils.explicitWait;

public class DropdownTest extends BaseUITestClass {

    public static final String DROPDOWN_MENU_XPATH = "//select[@id=\"cars\"]";

    private final List<String> cars = List.of("Audi", "BMW", "Ford", "Honda", "Jeep", "Mercedes", "Suzuki", "Volkswagen");

    @BeforeMethod
    public void verifyDropdownPageLoaded() {
        System.out.println("DropdownTest.class @BeforeMethod execution");
        driver.findElement(By.xpath("//*[@id=\"menu\"]//a[@href=\"dropdown.html\"]")).click();

        String url = driver.getCurrentUrl();
        WebElement buttonsTabTitle = driver.findElement(By.xpath("//h2[text()=\"Dropdown Menus, Radio Buttons & Checkboxes\"]"));

        assertEquals(url, "https://automationtesting.co.uk/dropdown.html");
        assertTrue(buttonsTabTitle.isDisplayed());
    }

    @Test
    public void verifyDropdownMenuOptionsAreCorrect() {
        Select select = new Select(driver.findElement(By.xpath(DROPDOWN_MENU_XPATH)));
        select.getOptions();

        assertThat(select.getOptions())
                .extracting(WebElement::getText)
                .containsExactlyInAnyOrderElementsOf(cars);
    }

    @Test
    public void verifySelectedOptionIsSelected() {
        Select select = new Select(driver.findElement(By.xpath(DROPDOWN_MENU_XPATH)));
        select.selectByIndex(4);

        assertTrue(driver.findElements(By.xpath(DROPDOWN_MENU_XPATH + "/option")).get(4).isSelected());
    }

    @Test
    public void verifyDropdownMenuFunctionalityWithKeyboardNavigation() {
        openDropdownMenu();

        for (int i = 0; i < 4; i++) {
            driver.findElement(By.xpath(DROPDOWN_MENU_XPATH)).sendKeys(Keys.ARROW_DOWN);
        }
        driver.findElement(By.xpath(DROPDOWN_MENU_XPATH)).sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.attributeToBe(By.xpath(DROPDOWN_MENU_XPATH), "value", "jeep"));

        assertEquals(dropdownMenuCurrentValue(), "jeep");
    }

    // Blocker: unable to verify if the dropdown menu is closed
    @Test(enabled = false)
    public void verifyDropdownMenuDisappearsOnMouseClickOutside() {
    }

    @Test(enabled = false)
    public void verifyDropdownMenuDisappearsOnEscapeKeyPress() {
    }

    @Test(enabled = false)
    public void verifyDropdownMenuClosesAfterSelection() {
    }


    private void openDropdownMenu() {
        driver.findElement(By.xpath(DROPDOWN_MENU_XPATH)).click();
        explicitWait(300);
    }

    private String dropdownMenuCurrentValue() {
        return ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].value;", driver.findElement(By.xpath(DROPDOWN_MENU_XPATH))).toString();
    }

}
