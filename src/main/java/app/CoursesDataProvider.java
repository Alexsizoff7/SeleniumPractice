package app;

import org.testng.annotations.DataProvider;

public class CoursesDataProvider {

    @DataProvider
    public static Object[][] courses() {
        return new Object[][]{
                {"Selenium Webdriver & Java"},
                {"Cucumber BDD with Selenium & Java"},
                {"Cypress.io 10 using Javascript"},
                {"Webdriver IO using Javascript"},
                {"Mastering Selectors/Locators"},
                {"Selenium Webdriver 4 - New Features"},
                {"Intro to Cucumber BDD, Selenium & Java (Free - 2.5hrs)"}
        };
    }
}
