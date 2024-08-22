package uk.ac.cf.spring.nhs.Event.Service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import uk.ac.cf.spring.nhs.Event.DTO.EventDTO;
import uk.ac.cf.spring.nhs.Event.Entity.Event;
import uk.ac.cf.spring.nhs.Event.Repository.EventRepository;
import uk.ac.cf.spring.nhs.Symptom.DTO.SymptomDTO;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Symptom.Service.SymptomService;
import uk.ac.cf.spring.nhs.Treatment.DTO.TreatmentDTO;
import uk.ac.cf.spring.nhs.Treatment.Entity.Treatment;
import uk.ac.cf.spring.nhs.Treatment.Service.TreatmentService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    void testSaveEvent_WithSymptomsAndTreatments() {
        // Prepare mock data
        EventDTO eventDTO = new EventDTO();
        eventDTO.setDate(LocalDate.now());
        eventDTO.setDuration(120);
        eventDTO.setSymptoms(Collections.singletonList(new SymptomDTO("Headache", 5)));
        eventDTO.setTreatments(Collections.singletonList(new TreatmentDTO("Medication", "Ibuprofen")));

        Event event = new Event(eventDTO.getDate(), eventDTO.getDuration(), 1L);
        event.setId(1L);

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Test the service
        Event savedEvent = eventService.saveEvent(eventDTO, 1L);

        // Verify everything is properly saved
        verify(eventRepository, times(1)).save(any(Event.class));
        verify(symptomService, times(1)).saveAll(anyList());
        verify(treatmentService, times(1)).saveAll(anyList());

        assertNotNull(savedEvent);
        assertEquals(1L, savedEvent.getId());
    }

    @Test
    void testSaveEvent_WithoutSymptomsOrTreatments() {
        // Prepare mock data
        EventDTO eventDTO = new EventDTO();
        eventDTO.setDate(LocalDate.now());
        eventDTO.setDuration(60);
        eventDTO.setSymptoms(Collections.emptyList());
        eventDTO.setTreatments(Collections.emptyList());

        Event event = new Event(eventDTO.getDate(), eventDTO.getDuration(), 1L);
        event.setId(1L);

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        // Test the service
        Event savedEvent = eventService.saveEvent(eventDTO, 1L);

        // Verify only the event is saved
        verify(eventRepository, times(1)).save(any(Event.class));
        verify(symptomService, never()).saveAll(anyList());
        verify(treatmentService, never()).saveAll(anyList());

        // Assertions
        assertNotNull(savedEvent);
        assertEquals(1L, savedEvent.getId());
    }


    @Test
    void testDeleteEventById() {
        eventService.deleteEventById(1L);

        verify(eventRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetEventById_Found() {
        // Prepare mock data
        Event event = new Event(LocalDate.now(), 60, 1L);
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        // Test the service
        Event foundEvent = eventService.getEventById(1L);

        verify(eventRepository, times(1)).findById(1L);
        assertNotNull(foundEvent);
        assertEquals(1L, foundEvent.getUserId());
    }

    @Test
    void testGetEventById_NotFound() {
        // Mock the case where the event is not found
        when(eventRepository.findById(1L)).thenReturn(Optional.empty());

        // Test the service
        Event foundEvent = eventService.getEventById(1L);

        verify(eventRepository, times(1)).findById(1L);
        assertNull(foundEvent);
    }

    @Test
    void testGetAllEvents() {
        // Prepare mock data
        List<Event> events = Collections.singletonList(new Event(LocalDate.now(), 120, 1L));
        when(eventRepository.findAll(any(Sort.class))).thenReturn(events);

        // Test the service
        List<Event> result = eventService.getAllEvents();

        // Verify interactions and assertions
        verify(eventRepository, times(1)).findAll(any(Sort.class));
        assertEquals(1, result.size());
    }

    @Test
    void testGetEventsByUserId() {
        // Prepare mock data
        List<Event> events = Collections.singletonList(new Event(LocalDate.now(), 90, 1L));
        when(eventRepository.findByUserId(eq(1L), any(Sort.class))).thenReturn(events);

        // Test the service
        List<Event> result = eventService.getEventsByUserId(1L);

        // Verify interactions and assertions
        verify(eventRepository, times(1)).findByUserId(eq(1L), any(Sort.class));
        assertEquals(1, result.size());
    }
}
