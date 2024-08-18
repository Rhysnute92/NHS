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

import java.util.Date;
import java.util.List;
import java.util.Set;
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

        // Save the event first to generate the ID
        event = eventRepository.save(event);
        long eventId = event.getId();

        // Symptoms
        if (!eventDTO.getSymptoms().isEmpty()) {
            Set<Symptom> symptoms = eventDTO.getSymptoms().stream()
                    .filter(symptomDTO -> symptomDTO.getSeverity() != null)
                    .map(symptomDTO -> new Symptom(
                            symptomDTO.getName(),
                            symptomDTO.getSeverity(),
                            userId,
                            eventId,
                            "Event"))
                    .collect(Collectors.toSet());

            event.setSymptoms(symptoms);

            // Save symptoms in batch
            symptomService.saveSymptoms(symptoms);
        }

        // Treatments
        if (!eventDTO.getTreatments().isEmpty()) {
            Set<Treatment> treatments = eventDTO.getTreatments().stream()
                    .filter(treatmentDTO -> treatmentDTO.getType() != null)
                    .map(treatmentDTO -> new Treatment(
                            treatmentDTO.getType(),
                            treatmentDTO.getDetails(),
                            userId,
                            eventId,
                            "Event"))
                    .collect(Collectors.toSet());

            event.setTreatments(treatments);

            // Save treatments in batch
            treatmentService.saveTreatments(treatments);
        }

        // Save the event again to ensure relationships are persisted
        return eventRepository.save(event);
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
