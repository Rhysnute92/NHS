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
import uk.ac.cf.spring.nhs.Measurement.DTO.MeasurementGroupDTO;
import uk.ac.cf.spring.nhs.Measurement.DTO.MeasurementLocationDTO;
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

        List<DiaryEntryDTO> diaryEntryDTOs = new ArrayList<>();

        for (DiaryEntry entry : diaryEntries) {
            List<MeasurementGroupDTO> twoSidedMeasurementGroups = new ArrayList<>();
            List<Measurement> nonSidedMeasurements = new ArrayList<>();

            // Group measurements by type
            Map<String, List<Measurement>> measurementsByType = entry.getMeasurements().stream()
                    .collect(Collectors.groupingBy(Measurement::getType));

            for (Map.Entry<String, List<Measurement>> typeEntry : measurementsByType.entrySet()) {
                String type = typeEntry.getKey();
                List<Measurement> measurements = typeEntry.getValue();

                Map<String, MeasurementLocationDTO> locationMap = new HashMap<>();

                for (Measurement measurement : measurements) {
                    // If the measurement has no side add it to the non-sided measurements list
                    if (measurement.getSide() == null || measurement.getSide().isEmpty()) {
                        nonSidedMeasurements.add(measurement);
                    } else {
                        String location = Optional.ofNullable(measurement.getLocation()).orElse("Unknown");
                        locationMap.putIfAbsent(location, new MeasurementLocationDTO(location, null, null));
                        MeasurementLocationDTO locationDTO = locationMap.get(location);

                        if ("Left".equals(measurement.getSide())) {
                            locationDTO.setLeftMeasurement(measurement);
                        } else if ("Right".equals(measurement.getSide())) {
                            locationDTO.setRightMeasurement(measurement);
                        }
                    }
                }

                if (!locationMap.isEmpty()) {
                    MeasurementGroupDTO groupDTO = new MeasurementGroupDTO();
                    groupDTO.setType(type);
                    groupDTO.setLocations(new ArrayList<>(locationMap.values()));
                    twoSidedMeasurementGroups.add(groupDTO);
                }
            }

            // Create DiaryEntryDTO and add two-sided and non-sided measurements
            DiaryEntryDTO dto = new DiaryEntryDTO(
                    entry.getDate(),
                    twoSidedMeasurementGroups,
                    nonSidedMeasurements,
                    entry.getMood(),
                    entry.getNotes(),
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
            System.out.println(e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteDiaryEntry(@PathVariable Long id) {
        boolean deleted = diaryEntryService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
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