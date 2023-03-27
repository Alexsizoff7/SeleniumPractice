package app.datepicker;

// Applicable only for ClockType.BASIC_DATE_TIME
public enum DayPeriod {
    AM("AM"),
    PM("PM");

    private final String text;

    DayPeriod(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
