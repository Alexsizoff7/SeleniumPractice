package app;

import org.testng.annotations.DataProvider;

public class ContactFormDataProvider {

    @DataProvider
    public Object[][] invalidEmails() {
        return new Object[][]{
                {"test"},
                {"test@example"},
                {"test@examplecom"},
                {"@example.com"},
                {".@example.com"},
                {"test@example.com.comp"}
        };
    }
}
