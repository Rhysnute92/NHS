package uk.ac.cf.spring.nhs.Diary.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinFormDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;

import uk.ac.cf.spring.nhs.Diary.Service.DiaryEntryService;
import uk.ac.cf.spring.nhs.Photo.Service.PhotoService;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;
import uk.ac.cf.spring.nhs.Event.Service.EventService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    private AuthenticationInterface authenticationFacade;
    @Autowired
    DiaryEntryService diaryEntryService;

    @Autowired
    PhotoService photoService;

    @Autowired
    EventService eventService;

    @GetMapping("")
    public String diary(Model model,
                        @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getUserId();
        List<DiaryEntry> diaryEntries = diaryEntryService.getDiaryEntriesByUserId(userId);
        model.addAttribute("diaryEntries", diaryEntries);
        return "diary/diary";
    }

    @GetMapping("/checkin")
    public String checkin(Model model) {
        return "diary/checkin";
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkin(@ModelAttribute CheckinFormDTO checkinForm,
                                     @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            Long userId = userDetails.getUserId();
            DiaryEntry savedEntry = diaryEntryService.createAndSaveDiaryEntry(checkinForm, userId);

            // Create a response object
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Check-in successful");
            response.put("entryId", savedEntry.getId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
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