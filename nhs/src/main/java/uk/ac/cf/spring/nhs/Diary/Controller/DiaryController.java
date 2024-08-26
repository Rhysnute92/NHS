package uk.ac.cf.spring.nhs.Diary.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinFormDTO;
import uk.ac.cf.spring.nhs.Diary.DTO.DiaryEntryDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;

import uk.ac.cf.spring.nhs.Diary.Service.DiaryEntryService;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    DiaryEntryService diaryEntryService;

    @GetMapping
    public String diary(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        Long userId = user.getUserId();
        List<DiaryEntry> diaryEntries = diaryEntryService.getDiaryEntriesByUserId(userId);

        // Create a list of DTOs to hold the diary entries with grouped measurements
        List<DiaryEntryDTO> diaryEntryDTOs = new ArrayList<>();

        for (DiaryEntry entry : diaryEntries) {
            Map<String, Map<String, Measurement>> groupedMeasurementsByLocation = new HashMap<>();

            // Group measurements by location and side
            for (Measurement measurement : entry.getMeasurements()) {
                String location = Optional.ofNullable(measurement.getLocation()).orElse("Unknown");

                groupedMeasurementsByLocation.putIfAbsent(location, new HashMap<>());

                // Store the measurement based on side
                groupedMeasurementsByLocation.get(location).put(measurement.getSide(), measurement);
            }

            // Create a DTO with the diary entry and its grouped measurements
            DiaryEntryDTO dto = new DiaryEntryDTO(
                    entry.getDate(),
                    groupedMeasurementsByLocation,
                    entry.getMood(),
                    entry.getNotes(),
                    entry.getMeasurements(),
                    entry.getSymptoms(),
                    entry.getPhotos()
            );

            diaryEntryDTOs.add(dto);
        }

        model.addAttribute("diaryEntryDTOs", diaryEntryDTOs);
        return "diary/diary";
    }



    @GetMapping("/checkin")
    public String checkin(Model model) {
        return "diary/checkin";
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkin(HttpServletRequest request,
                                     @ModelAttribute CheckinFormDTO checkinForm,
                                     @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        try {
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String paramName = parameterNames.nextElement();
                System.out.println(paramName + ": " + request.getParameter(paramName));
            }
            System.out.println(checkinForm);
            Long userId = userDetails.getUserId();
            DiaryEntry savedEntry = diaryEntryService.saveDiaryEntry(checkinForm, userId);

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