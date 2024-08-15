package uk.ac.cf.spring.nhs.Diary.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Diary.Entity.Event;
import uk.ac.cf.spring.nhs.Diary.Repository.EventRepository;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getEventsByUserId(Long userId) {
        return eventRepository.findByUserId(userId);
    }
}