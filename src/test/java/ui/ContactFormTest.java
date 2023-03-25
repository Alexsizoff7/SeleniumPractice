package ui;

import app.ContactFormDataProvider;
import baseclasses.BaseUITestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static utils.TestUtils.explicitWait;


public class ContactFormTest extends BaseUITestClass {

    WebElement firstName;
    WebElement lastName;
    WebElement emailName;
    WebElement message;
    WebElement resetButton;
    WebElement submitButton;

    public static final String SUCCESS_URL = HOMEPAGE + "/contact-form-thank-you.html";
    public static final String ERROR_URL = HOMEPAGE + "/contact_us.php";

    @BeforeMethod
    public void verifyContactFormPageLoaded() {
        System.out.println("ContactForm.class @BeforeMethod execution");
        driver.findElement(By.xpath("//*[@id=\"menu\"]//a[@href=\"contactForm.html\"]")).click();

        setInputFields();
        setButtons();

        String url = driver.getCurrentUrl();
        WebElement contactForm = driver.findElement(By.xpath("//form[@id=\"contact_form\"]"));

        assertEquals(url, "https://automationtesting.co.uk/contactForm.html");
        assertTrue(contactForm.isDisplayed());
    }

    @Test
    public void emptyFormReturnsErrorPage() {
        submitButton.click();

        assertEquals(driver.getCurrentUrl(), ERROR_URL);
    }

    @Test
    public void formFilledWithValidDataSubmitsSuccessfully() {
        firstName.sendKeys("John");
        lastName.sendKeys("Smith");
        emailName.sendKeys("test@example.com");
        message.sendKeys("Hello World!");
        explicitWait(500);

        submitButton.click();

        assertEquals(driver.getCurrentUrl(), SUCCESS_URL);
    }

    @Test
    public void formFilledWithoutMessageReturnsErrorPage() {
        firstName.sendKeys("John");
        lastName.sendKeys("Smith");
        emailName.sendKeys("test@example.com");
        explicitWait(500);

        submitButton.click();

        assertEquals(driver.getCurrentUrl(), ERROR_URL);
    }

    @Test
    public void resetButtonClickShouldEraseAllInputsSuccessfully() {
        firstName.sendKeys("John");
        lastName.sendKeys("Smith");
        emailName.sendKeys("test@example.com");
        message.sendKeys("Hello World!");
        explicitWait(500);

        resetButton.click();

        assertEquals(firstName.getText(), "");
        assertEquals(lastName.getText(), "");
        assertEquals(emailName.getText(), "");
        assertEquals(message.getText(), "");
    }

    @Test(dataProvider = "invalidEmails", dataProviderClass = ContactFormDataProvider.class)
    public void invalidEmailInputShouldReturnsErrorPage(String email) {
        firstName.sendKeys("John");
        lastName.sendKeys("Smith");
        emailName.sendKeys(email);
        message.sendKeys("Hello World!");

        submitButton.click();

        assertEquals(driver.getCurrentUrl(), ERROR_URL);
    }

    private void setInputFields() {
        firstName = driver.findElement(By.name("first_name"));
        lastName = driver.findElement(By.name("last_name"));
        emailName = driver.findElement(By.name("email"));
        message = driver.findElement(By.name("message"));
    }

    private void setButtons() {
        resetButton = driver.findElement(By.xpath("//input[@value=\"RESET\"]"));
        submitButton = driver.findElement(By.xpath("//input[@value=\"SUBMIT\"]"));
    }

}
