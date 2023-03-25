package app.DatePicker;

// Choosing one of three available calendars on Date Picker page
// String values tied to enums serve as indexes to one of 3 available calendars
// "(//div[contains(@class, "flatpickr-calendar")])[ CalendarType ]"
public enum CalendarType {
    BASIC_DATE("1"),
    RANGE_DATE("2"),
    DATE_WITH_RESET("4");

    private final String text;

    CalendarType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}