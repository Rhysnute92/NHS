package uk.ac.cf.spring.nhs.Diary.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinFormDTO;
import uk.ac.cf.spring.nhs.Diary.DTO.PhotoDTO;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Entity.Photo;
import uk.ac.cf.spring.nhs.Diary.Service.DiaryEntryService;
import uk.ac.cf.spring.nhs.Diary.Service.PhotoService;
import uk.ac.cf.spring.nhs.Security.AuthenticationInterface;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    DiaryEntryService diaryEntryService;

    @Autowired
    PhotoService photoService;

    @Autowired
    private AuthenticationInterface authenticationFacade;

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


    @GetMapping("/photos")
    public String photos(Model model,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        List<Photo> photos = photoService.getPhotosByUserId(userId);
        model.addAttribute("photos", photos);
        return "diary/photos";
    }


    @PostMapping("/photos")
    public ResponseEntity<?> uploadPhoto(@ModelAttribute PhotoDTO photo,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Long userId = userDetails.getUserId();

            if (photo != null && photo.getFile() != null) {
                Photo savedPhoto = photoService.savePhoto(photo, userId);
                return ResponseEntity.ok(savedPhoto);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Uploaded photo is empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }


    @ModelAttribute("navMenuItems")
    public List<NavMenuItem> navMenuItems() {
        return List.of(
            new NavMenuItem("Diary", "/diary", "fa-solid fa-book"),
            new NavMenuItem("Check-in", "/diary/checkin", "fa-solid fa-user-check"),
            new NavMenuItem("Photos", "/diary/photos", "fa-solid fa-camera")
        );
    }
}