package baseclasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


import java.time.Duration;
import java.util.ArrayList;

public class BaseUITestClass {

    public static final String HOMEPAGE = "https://automationtesting.co.uk";

    protected WebDriver driver;

    protected WebDriverWait wait;

    @BeforeMethod
    public void browserStartUp() {
        System.out.println("Base class @BeforeMethod execution");
        System.setProperty("webdriver.chrome.driver", "E:\\Java\\chromedriver.exe");

        driver = new ChromeDriver(new ChromeOptions().addArguments("--remote-allow-origins=*"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        driver.get(HOMEPAGE);
        driver.manage().window().maximize();

        // Preventing tests to run if general functionality breaks
        doBasicCheck();
    }

    @AfterMethod(alwaysRun = true)
    public void browserClose() {
        System.out.println("Closing down the browser");

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (String tab : tabs) {
            driver.switchTo().window(tab);
            driver.close();
        }
    }



    // Do basic check that page has loaded
    private void doBasicCheck() {
        driver.findElement(By.xpath("//*[@id=\"header\"]/a[@class=\"logo\"]"));
    }

}
