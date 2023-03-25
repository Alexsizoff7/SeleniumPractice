package ui;

import app.DatePicker.CalendarType;
import app.DatePicker.ClockType;
import app.DatePicker.DayPeriod;
import baseclasses.BaseUITestClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import static utils.TestUtils.explicitWait;

public class DatePickerTest extends BaseUITestClass {

    public static final String XPATH_PREFIX = "(//div[contains(@class, \"flatpickr-calendar\")])[";
    public static final String XPATH_ARROW_UP_POSTFIX = "]//span[@class=\"arrowUp\"]";
    public static final String XPATH_ARROW_DOWN_POSTFIX = "]//span[@class=\"arrowDown\"]";

    private WebElement basicDateTimeInput;

    private WebElement rangeDateInput;

    private WebElement dateWithResetInput;

    private WebElement timerPickerInput;

    private WebElement nextYear;

    private WebElement prevYear;

    private WebElement nextMonth;

    private WebElement prevMonth;

    private WebElement nextHour;

    private WebElement prevHour;

    private WebElement nextFiveMinutes;

    private WebElement prevFiveMinutes;

    private WebElement dayPeriod;

    @BeforeMethod
    public void verifyDatePickerPageLoaded() {
        System.out.println("datePickerTest.class @BeforeMethod execution");
        driver.findElement(By.xpath("//*[@id=\"menu\"]//a[@href=\"datepicker.html\"]")).click();

        basicDateTimeInput = driver.findElement(By.xpath("//input[@id=\"basicDate\"]"));
        rangeDateInput = driver.findElement(By.xpath("//input[@id=\"rangeDate\"]"));
        dateWithResetInput = driver.findElement(By.xpath("//div[@class=\"resetDate\"]/input"));
        timerPickerInput = driver.findElement(By.xpath("//input[@id=\"timePicker\"]"));

        String url = driver.getCurrentUrl();
        WebElement datePickerContext = driver.findElement(By.xpath("//h3[contains(text(), \"Basic DateTime\")]"));

        assertEquals(url, "https://automationtesting.co.uk/datepicker.html");
        assertTrue(datePickerContext.isDisplayed());
    }

    @Test
    public void dateWithResetCalendar_ValidDateInput_OutputMatchesInput() {
        openDateWithResetCalendar();

        setDate(CalendarType.DATE_WITH_RESET, 8, "May", 2025);
        explicitWait(2000);

        assertEquals(output(dateWithResetInput), "2025-05-08",
                "Output date doesn't match input value");
    }

    @Test
    public void dateWithResetCalendar_ResetButtonClick_InputFieldEmpty() {
        openDateWithResetCalendar();

        setDate(CalendarType.DATE_WITH_RESET, 8, "May", 2025);
        WebElement resetButton = driver.findElement(By.xpath("//button[text()=\"RESET\"]"));
        resetButton.click();

        assertTrue(output(dateWithResetInput).isEmpty());
    }

    @Test
    public void dateWithResetCalendar_InputValidDateAfterReset_OutputMatchesInput() {
        openDateWithResetCalendar();

        setDate(CalendarType.DATE_WITH_RESET, 8, "May", 2025);
        WebElement resetButton = driver.findElement(By.xpath("//button[text()=\"RESET\"]"));
        resetButton.click();
        dateWithResetInput.click();
        explicitWait(300);
        setDate(CalendarType.DATE_WITH_RESET, 27, "April", 2000);

        assertEquals(output(dateWithResetInput), "2000-04-27",
                "Output date doesn't match input value");
    }

    @Test
    public void dateWithResetCalendar_NegativeYearInput_OutputReturnCurrentYear() {
        openDateWithResetCalendar();

        setDate(CalendarType.DATE_WITH_RESET, 10, "June", -2027);
        explicitWait(3);

        assertEquals(output(dateWithResetInput), "2023-06-10",
                "Output date doesn't match input value");
    }

    @Test
    public void dateWithResetCalendar_ManualMonthChange_DateChangesAccordingly() {
        openDateWithResetCalendar();

        setYear(CalendarType.DATE_WITH_RESET, 2025);
        setMonth(CalendarType.DATE_WITH_RESET, "May");
        prevMonth.click(); // month: April
        prevMonth.click(); // month: March
        prevMonth.click(); // month: February
        nextMonth.click(); // month: March
        prevMonth.click(); // month: February
        explicitWait(500);

        assertEquals(getCurrentMonth(CalendarType.DATE_WITH_RESET), "February",
                "Output month doesn't match input value");
    }

    @Test
    public void dateWithResetCalendar_ManualYearChange_DateChangesAccordingly() {
        openDateWithResetCalendar();

        setYear(CalendarType.DATE_WITH_RESET, 2025);
        nextYear.click(); // 2026
        nextYear.click(); // 2027
        nextYear.click(); // 2028
        prevYear.click(); // 2027
        explicitWait(500);

        assertEquals(getCurrentYear(CalendarType.DATE_WITH_RESET), "2027",
                "Output year doesn't match input value");
    }

    @Test
    public void basicDateCalendar_ValidDateInput_OutputMatchesInput() {
        openBasicDateTimeCalendar();

        setDate(CalendarType.BASIC_DATE, 8, "March", 2018);
        explicitWait(500);
        setTime(11, 3, DayPeriod.AM);

        assertEquals(output(basicDateTimeInput), "March, 08 2018 11:03");
    }

    @Test
    public void basicDateCalendar_24HourDateInput_OutputConvertedTo24HourClock() {
        openBasicDateTimeCalendar();

        setDate(CalendarType.BASIC_DATE, 9, "March", 2018);
        explicitWait(500);
        setTime(ClockType.BASIC_DATE_TIME, 22, 55);
        explicitWait(300);

        assertEquals(output(basicDateTimeInput), "March, 09 2018 22:55");
    }

    @Test
    public void basicDateCalendar_DayPeriodIsPM_OutputConvertedTo24HourClock() {
        openBasicDateTimeCalendar();

        setDate(CalendarType.BASIC_DATE, 10, "March", 2018);
        explicitWait(500);
        setTime(8, 30, DayPeriod.PM);
        explicitWait(300);

        assertEquals(output(basicDateTimeInput), "March, 10 2018 20:30");
    }

    @Test
    public void basicDateCalendar_NewInputAddedOverExisting_OutputContainsLastInput() {
        openBasicDateTimeCalendar();
        setDate(CalendarType.BASIC_DATE, 10, "March", 2018);
        explicitWait(500);
        setTime(8, 30, DayPeriod.PM);
        explicitWait(1000);

        openBasicDateTimeCalendar();
        setDate(CalendarType.BASIC_DATE, 30, "September", 2044);
        explicitWait(500);
        setTime(5, 8, DayPeriod.AM);

        assertEquals(output(basicDateTimeInput), "September, 30 2044 05:08");
    }

    @Test
    public void basicDateCalendar_ManualClockChange_TimeChangesAccordingly() {
        openBasicDateTimeCalendar();

        setDate(CalendarType.BASIC_DATE, 10, "March", 2018);
        explicitWait(500);
        setTime(8, 30, DayPeriod.PM);
        explicitWait(300);
        nextHour.click(); // 21:30
        nextHour.click(); // 22:30
        nextHour.click(); // 23:30
        prevHour.click(); // 22:30
        explicitWait(300);
        prevFiveMinutes.click(); // 22:25
        prevFiveMinutes.click(); // 22:20
        prevFiveMinutes.click(); // 22:15
        nextFiveMinutes.click(); // 22:20

        assertEquals(output(basicDateTimeInput), "March, 10 2018 22:20");
    }

    @Test
    public void rangeDateCalendar_ValidDateInput_OutputMatchesInput() {
        openRangeDateCalendar();

        setDate(CalendarType.RANGE_DATE, 1, "June", 2024); // range start date
        explicitWait(300);
        setDate(CalendarType.RANGE_DATE, 29, "December", 2033); // range end date
        explicitWait(300);

        assertEquals(output(rangeDateInput), "2024-06-01 to 2033-12-29");
    }

    @Test
    public void rangeDateCalendar_SameStartAndEndDate_OutputMatchesInput() {
        openRangeDateCalendar();

        setDate(CalendarType.RANGE_DATE, 5, "May", 2025); // range start date
        explicitWait(300);
        setDate(CalendarType.RANGE_DATE, 5, "May", 2025); // range end date
        explicitWait(300);

        assertEquals(output(rangeDateInput), "2025-05-05 to 2025-05-05");
    }

    @Test
    public void rangeDateCalendar_EndDateIsBeforeStartDate_RangeStartWithEarliestDate() {
        openRangeDateCalendar();

        setDate(CalendarType.RANGE_DATE, 10, "May", 2020); // range start date
        explicitWait(300);
        setDate(CalendarType.RANGE_DATE, 15, "May", 2015); // range end date
        explicitWait(300);

        assertEquals(output(rangeDateInput), "2015-05-15 to 2020-05-10");
    }

    @Test
    public void rangeDateCalendar_NewInputAddedOverExisting_OutputContainsLastInput() {
        openRangeDateCalendar();
        setDate(CalendarType.RANGE_DATE, 5, "May", 2025); // range start date
        explicitWait(300);
        setDate(CalendarType.RANGE_DATE, 5, "May", 2025); // range end date
        explicitWait(2000);

        openRangeDateCalendar();
        setDate(CalendarType.RANGE_DATE, 1, "June", 2024); // range start date
        explicitWait(300);
        setDate(CalendarType.RANGE_DATE, 29, "December", 2033); // range end date
        explicitWait(300);

        assertEquals(output(rangeDateInput), "2024-06-01 to 2033-12-29");
    }

    @Test
    public void timePickerClock_ValidTimeInput_OutputMatchesInput() {
        openTimePickerClock();

        setTime(ClockType.TIME_PICKER, 23, 40);
        explicitWait(1000);

        assertEquals(output(timerPickerInput), "23:40");
    }

    @Test
    public void timePickerClock_TimeInputOverLimit_ResetClockToZero() {
        openTimePickerClock();

        setTime(ClockType.TIME_PICKER, 34, 60);
        explicitWait(1000);

        assertEquals(output(timerPickerInput), "00:00");
    }

    @Test
    public void timePickerClock_ManualTimeChange_TimeChangesAccordingly() {
        openTimePickerClock();

        // hour timer default value is 12
        for (int i = 0; i < 6; i++) { // increases hour timer to 18
            nextHour.click();
            explicitWait(100);
        }
        // minute timer default value is 00
        for (int i = 0; i < 7; i++) { // increases minute timer to 35
            nextFiveMinutes.click();
            explicitWait(100);
        }
        for (int i = 0; i < 3; i++) { // decreases hour timer to 15
            prevHour.click();
            explicitWait(100);
        }
        for (int i = 0; i < 4; i++) { // decreases minute timer to 15
            prevFiveMinutes.click();
            explicitWait(100);
        }
        explicitWait(300);

        assertEquals(output(timerPickerInput), "15:15");
    }

    @Test
    public void timePickerClock_ManualClickOnMinuteValuedAtOver55_HourTimeIncreasesByOneHour() {
        openTimePickerClock();

        setTime(ClockType.TIME_PICKER, 23, 55);
        explicitWait(1000);

        nextFiveMinutes.click();

        assertEquals(output(timerPickerInput), "00:00");
    }


        private void openBasicDateTimeCalendar() {
        setCalendarButtonsSuite(CalendarType.BASIC_DATE);
        setClockButtonsSuite(ClockType.BASIC_DATE_TIME);
        basicDateTimeInput.click();
        explicitWait(300);
    }

    private void openRangeDateCalendar() {
        setCalendarButtonsSuite(CalendarType.RANGE_DATE);
        rangeDateInput.click();
        explicitWait(300);
    }

    private void openDateWithResetCalendar() {
        setCalendarButtonsSuite(CalendarType.DATE_WITH_RESET);
        dateWithResetInput.click();
        explicitWait(300);
    }

    private void openTimePickerClock() {
        setClockButtonsSuite(ClockType.TIME_PICKER);
        timerPickerInput.click();
        explicitWait(300);
    }

    private String output(WebElement inputField) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        return jse.executeScript("return arguments[0].value;", inputField).toString();
    }

    // Sets up buttons for a manual increment or decrement of the year and month
    private void setCalendarButtonsSuite(CalendarType calendarType) {
        nextYear = driver.findElements(By.xpath(
                XPATH_PREFIX + calendarType + XPATH_ARROW_UP_POSTFIX)).get(0);
        prevYear = driver.findElements(By.xpath(
                XPATH_PREFIX + calendarType + XPATH_ARROW_DOWN_POSTFIX)).get(0);
        nextMonth = driver.findElement(By.xpath(
                XPATH_PREFIX + calendarType + "]//span[@class=\"flatpickr-next-month\"]"));
        prevMonth = driver.findElement(By.xpath(
                XPATH_PREFIX + calendarType + "]//span[@class=\"flatpickr-prev-month\"]"));
    }

    // Sets up buttons for a manual increment or decrement of the hour and minute in clock
    private void setClockButtonsSuite(ClockType clockType) {

        // ClockType.BASIC_DATE_TIME has an additional element with the same Xpath compared to ClockType.TIME_PICKER
        // element index should be incremented by 1 if @param is ClockType.BASIC_DATE_TIME
        int occurrenceIndex = 0;
        if (clockType == ClockType.BASIC_DATE_TIME) {
            occurrenceIndex += 1;
        }

        nextHour = driver.findElements(By.xpath(
                XPATH_PREFIX + clockType + XPATH_ARROW_UP_POSTFIX)).get(occurrenceIndex);
        prevHour = driver.findElements(By.xpath(
                XPATH_PREFIX + clockType + XPATH_ARROW_DOWN_POSTFIX)).get(occurrenceIndex);
        nextFiveMinutes = driver.findElements(By.xpath(
                XPATH_PREFIX + clockType + XPATH_ARROW_UP_POSTFIX)).get(occurrenceIndex + 1);
        prevFiveMinutes = driver.findElements(By.xpath(
                XPATH_PREFIX + clockType + XPATH_ARROW_DOWN_POSTFIX)).get(occurrenceIndex + 1);
        dayPeriod = driver.findElement(By.xpath("//span[@class=\"flatpickr-am-pm\"]"));
    }

    // Selecting day, month and year in any CalendarType
    // If used in CalendarType.RANGE_DATE should be called twice for range start and end dates
    private void setDate(CalendarType calendarType, int day, String month, int year) {
        setMonth(calendarType, month);
        setYear(calendarType, year);
        setDay(calendarType, day);
    }

    // Selecting hour and minute in any ClockType
    // If used in ClockType.BASIC_DATE_TIME 24-hour timer automatically being transformed into 12-hour with dayPeriod (AM or PM)
    private void setTime(ClockType clockType, int hour, int minute) {
        setHour(clockType, hour);
        setMinute(clockType, minute);
    }

    // Selecting hour, minute and dayPeriod (AM or PM)
    // Applicable only for ClockType.BASIC_DATE_TIME
    private void setTime(int hour, int minute, DayPeriod dayPeriod) {
        setHour(ClockType.BASIC_DATE_TIME, hour);
        setMinute(ClockType.BASIC_DATE_TIME, minute);
        setPeriod(dayPeriod);
    }

    // Selecting dayPeriod (AM or PM)
    // Applicable only for ClockType.BASIC_DATE_TIME
    private void setPeriod(DayPeriod dayPeriod) {
        if (!dayPeriod.toString().equals(getCurrentDayPeriod())) {
            this.dayPeriod.click();
        }
    }

    // Only finds days of the currently selected month in any CalendarType
    // Current's month class name structure is "flatpickr-day" with different postfixes except for keyword "Month"
    // Previous and next month's class names are: "flatpickr-day prevMonthDay" and "flatpickr-day nextMonthDay"
    private void setDay(CalendarType calendarType, int day) {
        WebElement selectDay = driver.findElement(By.xpath(
                XPATH_PREFIX + calendarType + "]//div[@class=\"dayContainer\"]" +
                        "//span[not(contains(@class, \"Month\"))][text()=\"" + day + "\"]"));

        selectDay.click();
    }

    // Selecting a month in any CalendarType
    // By manually clicking nextMonth arrow button, waiting 0.5 seconds between actions
    private void setMonth(CalendarType calendarType, String month) {
        while (!month.equals(getCurrentMonth(calendarType))) {
            nextMonth.click();
            explicitWait(500);
        }
    }

    // Typing in year value after erasing previous value in any CalendarType
    private void setYear(CalendarType calendarType, int year) {
        WebElement currentYearInput = driver.findElements(By.xpath(
                XPATH_PREFIX + calendarType + "]//input")).get(0);

        if (!currentYearInput.getText().equals(String.valueOf(year))) {
            currentYearInput.clear();
            currentYearInput.sendKeys(String.valueOf(year));
        }
    }

    // Typing in hour value after erasing previous value in any ClockType
    private void setHour(ClockType clockType, int hour) {
        WebElement currentHourInput = driver.findElement(By.xpath(
                XPATH_PREFIX + clockType + "]//input[@class=\"numInput flatpickr-hour\"]"));

        if (!currentHourInput.getText().equals(String.valueOf(hour))) {
            currentHourInput.clear();
            explicitWait(500);
            currentHourInput.sendKeys(String.valueOf(hour));
        }
    }

    // Typing in minute value after erasing previous value in any ClockType
    private void setMinute(ClockType clockType, int minute) {
        WebElement currentHourInput = driver.findElement(By.xpath(
                XPATH_PREFIX + clockType + "]//input[@class=\"numInput flatpickr-minute\"]"));

        if (!currentHourInput.getText().equals(String.valueOf(minute))) {
            currentHourInput.clear();
            explicitWait(500);
            currentHourInput.sendKeys(String.valueOf(minute));
        }
    }

    // Return either AM or PM value
    // Applicable only for ClockType.BASIC_DATE_TIME
    private String getCurrentDayPeriod() {
        return driver.findElement(By.xpath("//span[@class=\"flatpickr-am-pm\"]")).getText();
    }

    private String getCurrentMonth(CalendarType calendarType) {
        return driver.findElement(By.xpath(
                XPATH_PREFIX + calendarType + "]//span[@class=\"cur-month\"]")).getText();
    }

    private String getCurrentYear(CalendarType calendarType) {
        WebElement currentYearInput = driver.findElements(By.xpath(
                XPATH_PREFIX + calendarType + "]//input")).get(0);

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        return jse.executeScript("return arguments[0].value;", currentYearInput).toString();
    }

}
