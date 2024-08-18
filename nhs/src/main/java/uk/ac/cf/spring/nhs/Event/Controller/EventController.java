package uk.ac.cf.spring.nhs.Event.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Event.Service.EventService;
import uk.ac.cf.spring.nhs.Event.DTO.EventDTO;
import uk.ac.cf.spring.nhs.Event.Entity.Event;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/diary")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/events")
    public String events(Model model,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        long userId = userDetails.getUserId();
        List<Event> events = eventService.getEventsByUserId(userId);
        model.addAttribute("events", events);

        // Add ObjectMapper to model so Thymeleaf can use it to convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        model.addAttribute("objectMapper", objectMapper);

        return "diary/events";
    }

    @PostMapping("/events")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> createEvent(
            @ModelAttribute EventDTO eventDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        long userId = userDetails.getUserId();
        Event savedEvent = eventService.saveEvent(eventDTO, userId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Event created successfully");
        response.put("event", savedEvent);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @ModelAttribute("navMenuItems")
    public List<NavMenuItem> navMenuItems() {
        return List.of(
                new NavMenuItem("Diary", "/diary", "fa-solid fa-book"),
                new NavMenuItem("Check-in", "/diary/checkin", "fa-solid fa-user-check"),
                new NavMenuItem("Photos", "/diary/photos", "fa-solid fa-camera"),
                new NavMenuItem("Events", "/diary/events", "fa-solid fa-receipt")
        );
    }
}
