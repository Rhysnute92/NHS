package uk.ac.cf.spring.nhs.Event.Service;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Event.DTO.EventDTO;
import uk.ac.cf.spring.nhs.Event.Entity.Event;
import uk.ac.cf.spring.nhs.Event.Repository.EventRepository;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Symptom.Service.SymptomService;
import uk.ac.cf.spring.nhs.Treatment.Entity.Treatment;
import uk.ac.cf.spring.nhs.Treatment.Service.TreatmentService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final SymptomService symptomService;
    private final TreatmentService treatmentService;

    public EventService(EventRepository eventRepository,
                        SymptomService symptomService,
                        TreatmentService treatmentService) {
        this.eventRepository = eventRepository;
        this.symptomService = symptomService;
        this.treatmentService = treatmentService;
    }

    @Transactional
    public Event saveEvent(EventDTO eventDTO, long userId) {
        // Create the event
        Event event = new Event(eventDTO.getDate(), eventDTO.getDuration(), userId);

        List<Symptom> symptoms = eventDTO.getSymptoms().stream()
                .filter(symptomDTO -> symptomDTO.getSeverity() != null)
                .map(symptomDTO -> new Symptom(
                        symptomDTO.getName(),
                        symptomDTO.getSeverity(),
                        userId,
                        LocalDate.now()))
                .collect(Collectors.toList());

        List<Treatment> treatments = eventDTO.getTreatments().stream()
                .filter(treatmentDTO -> treatmentDTO.getType() != null)
                .map(treatmentDTO -> new Treatment(
                        treatmentDTO.getType(),
                        treatmentDTO.getDetails(),
                        userId,
                        LocalDate.now()))
                .collect(Collectors.toList());

        // Set symptoms and treatments in the event
        if (!symptoms.isEmpty()) {
            event.setSymptoms(symptoms);
        }
        if (!treatments.isEmpty()) {
            event.setTreatments(treatments);
        }

        Event savedEvent = eventRepository.save(event);
        long eventId = savedEvent.getId();

        if (!symptoms.isEmpty()) {
            symptoms.forEach(symptom -> symptom.setEvent(savedEvent));
            symptomService.saveAll(symptoms);
        }

        if (!treatments.isEmpty()) {
            treatments.forEach(treatment -> treatment.setEvent(savedEvent));
            treatmentService.saveAll(treatments);
        }

        return savedEvent;
    }

    public void deleteEventById(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
    }

    public List<Event> getEventsByUserId(Long userId) {
        return eventRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, "date"));
    }
}
