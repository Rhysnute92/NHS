package uk.ac.cf.spring.nhs.Event.Service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.ac.cf.spring.nhs.Event.DTO.EventDTO;
import uk.ac.cf.spring.nhs.Event.Entity.Event;
import uk.ac.cf.spring.nhs.Event.Repository.EventRepository;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Symptom.Service.SymptomService;
import uk.ac.cf.spring.nhs.Treatment.Entity.Treatment;
import uk.ac.cf.spring.nhs.Treatment.Service.TreatmentService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private SymptomService symptomService;

    @Mock
    private TreatmentService treatmentService;

    @InjectMocks
    private EventService eventService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        closeable.close();
    }

    @Test
    void testSaveEventWithSymptomsAndTreatmentsReturnsSavedEvent() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setDate(LocalDate.now());
        eventDTO.setDuration(10);

        long userId = 1L;

        Set<Symptom> symptoms = new HashSet<>();
        symptoms.add(new Symptom("Redness", 3, userId));

        Set<Treatment> treatments = new HashSet<>();
        treatments.add(new Treatment("Antibiotics", "Dicloxacillin", userId));

        Event event = new Event(LocalDate.now(), 10, userId);
        event.setSymptoms(symptoms);
        event.setTreatments(treatments);

        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(symptomService.saveSymptom(any(), anyLong())).thenReturn(new Symptom("Redness", 3, userId));
        when(treatmentService.saveTreatment(any(), anyLong())).thenReturn(new Treatment("Antibiotics", "Dicloxacillin", userId));

        Event savedEvent = eventService.saveEvent(eventDTO, 1L);

        assertNotNull(savedEvent);
        assertEquals(1, savedEvent.getSymptoms().size());
        assertEquals(1, savedEvent.getTreatments().size());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void testSaveEventWithoutSymptomsAndTreatmentsReturnsSavedEvent() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setDate(LocalDate.now());
        eventDTO.setDuration(20);

        long userId = 1L;

        Event event = new Event(LocalDate.now(), 20, userId);

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event savedEvent = eventService.saveEvent(eventDTO, userId);

        assertNotNull(savedEvent);
        assertTrue(savedEvent.getSymptoms().isEmpty());
        assertTrue(savedEvent.getTreatments().isEmpty());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void testGetEventsByUserIdReturnsListOfEvents() {
        Event event1 = new Event(LocalDate.now(), 60, 1L);
        Event event2 = new Event(LocalDate.now(), 90, 1L);

        when(eventRepository.findByUserId(1L)).thenReturn(List.of(event1, event2));

        List<Event> events = eventService.getEventsByUserId(1L);

        assertNotNull(events);
        assertEquals(2, events.size());
        verify(eventRepository, times(1)).findByUserId(1L);
    }

    @Test
    void testSaveEventWithNoSymptomsButWithTreatmentsReturnsSavedEvent() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setDate(LocalDate.now());
        eventDTO.setDuration(10);

        long userId = 1L;

        Set<Treatment> treatments = new HashSet<>();
        treatments.add(new Treatment("Antibiotics", "Dicloxacillin", userId));

        Event event = new Event(LocalDate.now(), 60, userId);
        event.setTreatments(treatments);

        when(eventRepository.save(any(Event.class))).thenReturn(event);
        when(treatmentService.saveTreatment(any(), anyLong())).thenReturn(new Treatment("Antibiotics", "Dicloxacillin", userId));

        Event savedEvent = eventService.saveEvent(eventDTO, 1L);

        assertNotNull(savedEvent);
        assertEquals(1, savedEvent.getTreatments().size());
        verify(eventRepository, times(1)).save(any(Event.class));
    }
}
