package uk.ac.cf.spring.nhs.Calendar.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.ac.cf.spring.nhs.Calendar.Model.Calendar;
import uk.ac.cf.spring.nhs.Calendar.Repositories.CalendarRepository;
import uk.ac.cf.spring.nhs.Calendar.Service.CalendarService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CalendarServiceTest {

    @Mock
    private CalendarRepository calendarRepository;

    @InjectMocks
    private CalendarService calendarService;

    private Calendar sampleCalendar;

    @BeforeEach
    void setUp() {
        // Initializes the mocks
        MockitoAnnotations.openMocks(this);

        // Set up a sample Calendar entry
        sampleCalendar = new Calendar();
        sampleCalendar.setCalendarID(1);
        sampleCalendar.setCalendarName("Sample Appointment");
    }

    @Test
    void testGetAllCalendarsReturnsNotEmptyList() {
        // Mock the repository response
        List<Calendar> calendars = new ArrayList<>();
        calendars.add(sampleCalendar);
        when(calendarRepository.findAll()).thenReturn(calendars);

        // Call the service method
        List<Calendar> calendarEntries = calendarService.getAllCalendars();

        // Verify the results
        assertFalse(calendarEntries.isEmpty(), "The calendar entries list should not be empty.");
        verify(calendarRepository, times(1)).findAll();
    }
}

