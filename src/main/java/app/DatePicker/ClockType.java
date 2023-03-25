package app.DatePicker;

// Choosing one of three available calendars on Date Picker page
// String values tied to enums serve as indexes to one of 3 available calendars
// "(//div[contains(@class, "flatpickr-calendar")])[ CalendarType ]"
public enum ClockType {
    BASIC_DATE_TIME("1"),
    TIME_PICKER("3");

    private final String text;

    ClockType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
