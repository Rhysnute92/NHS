package uk.ac.cf.spring.nhs.Event.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Event.DTO.EventDTO;
import uk.ac.cf.spring.nhs.Event.Entity.Event;
import uk.ac.cf.spring.nhs.Treatment.Entity.Treatment;
import uk.ac.cf.spring.nhs.Event.Repository.EventRepository;
import uk.ac.cf.spring.nhs.Symptom.Entity.Symptom;
import uk.ac.cf.spring.nhs.Symptom.Service.SymptomService;
import uk.ac.cf.spring.nhs.Treatment.Service.TreatmentService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SymptomService symptomService;

    @Autowired
    private TreatmentService treatmentService;

    public Event saveEvent(EventDTO eventDTO, long userId) {
        Event event = new Event(
                eventDTO.getDate(),
                eventDTO.getDuration(),
                userId
        );

        // Symptoms
        if (!eventDTO.getSymptoms().isEmpty()) {
            Set<Symptom> symptoms = eventDTO.getSymptoms().stream()
                    .filter(symptomDTO -> symptomDTO.getSeverity() != null)
                    .map(symptomDTO -> symptomService.saveSymptom(symptomDTO, userId))
                    .collect(Collectors.toSet());
            event.setSymptoms(symptoms);
        }

        // Treatments
        if (!eventDTO.getTreatments().isEmpty()) {
            Set<Treatment> treatments = eventDTO.getTreatments().stream()
                    .filter(treatmentDTO -> treatmentDTO.getType() != null)
                    .map(treatmentDTO -> treatmentService.saveTreatment(treatmentDTO, userId))
                    .collect(Collectors.toSet());
            event.setTreatments(treatments);
        }
        return eventRepository.save(event);
    }

    public List<Event> getEventsByUserId(Long userId) {
        return eventRepository.findByUserId(userId);
    }

    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }
}