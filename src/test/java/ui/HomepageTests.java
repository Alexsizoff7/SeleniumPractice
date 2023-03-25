package ui;

import app.CoursesDataProvider;
import baseclasses.BaseUITestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class HomepageTests extends BaseUITestClass {

    @Test(description = "Verify that h1 text is visible on the homepage")
    public void pageTitleIsDisplayed() {
        WebElement homepageTitle = driver.findElement(By.xpath("//h1"));

        assertTrue(homepageTitle.isDisplayed(), "Homepage title is not visible");
    }

    @Test(description = "Verify that links to Udemy courses are present on the homepage")
    public void udemyLinksArePresent() {
        List<WebElement> udemyLinks = driver.findElements(By.xpath("//h3/a[contains(@href, 'udemy.com')]"));

        assertTrue(udemyLinks.get(0).isDisplayed(), "Udemy links were not found");
        assertEquals(udemyLinks.size(),7, "Quantity of Udemy links has changed");
    }

    @Test(dataProvider = "courses", dataProviderClass = CoursesDataProvider.class,
            description = "Verify that homepage contains links to selected courses")
    public void pageContainsLinkToSpecificCourses(String course) {
        List<WebElement> courseLinks = driver.findElements(By.xpath("//h3/a[contains(@href, 'udemy.com')]"));
        List<String> courseTitles = courseLinks.stream()
                        .map(WebElement::getText)
                                .collect(Collectors.toList());

        assertThat(courseTitles).containsOnlyOnce(course);
    }

}
