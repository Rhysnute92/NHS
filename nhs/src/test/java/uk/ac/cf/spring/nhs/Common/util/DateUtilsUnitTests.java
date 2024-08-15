package uk.ac.cf.spring.nhs.Common.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

public class DateUtilsUnitTests {

    @Test
    public void testGetCurrentDayOfMonth() {
        // Mock LocalDate.now() to return a fixed date
        LocalDate fixedDate = LocalDate.of(2024, 8, 15);
        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(fixedDate);

            int currentDay = DateUtils.getCurrentDayOfMonth();
            assertThat(currentDay).isEqualTo(15);
        }
    }

    @Test
    public void testGetDaysInCurrentMonth() {
        // Mock LocalDate.now() to return a fixed date in a month with 31 days
        LocalDate fixedDate = LocalDate.of(2024, 8, 15); // August 2024
        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(fixedDate);

            int daysInMonth = DateUtils.getDaysInCurrentMonth();
            assertThat(daysInMonth).isEqualTo(31);
        }

        // Mock LocalDate.now() to return a fixed date in a month with 30 days
        fixedDate = LocalDate.of(2024, 9, 15); // September 2024
        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(fixedDate);

            int daysInMonth = DateUtils.getDaysInCurrentMonth();
            assertThat(daysInMonth).isEqualTo(30);
        }

        // Mock LocalDate.now() to return a fixed date in February on a leap year
        fixedDate = LocalDate.of(2024, 2, 15); // February 2024
        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(fixedDate);

            int daysInMonth = DateUtils.getDaysInCurrentMonth();
            assertThat(daysInMonth).isEqualTo(29);
        }

        // Mock LocalDate.now() to return a fixed date in February on a non-leap year
        fixedDate = LocalDate.of(2023, 2, 15); // February 2023
        try (MockedStatic<LocalDate> mockedLocalDate = mockStatic(LocalDate.class)) {
            mockedLocalDate.when(LocalDate::now).thenReturn(fixedDate);

            int daysInMonth = DateUtils.getDaysInCurrentMonth();
            assertThat(daysInMonth).isEqualTo(28);
        }
    }
}
