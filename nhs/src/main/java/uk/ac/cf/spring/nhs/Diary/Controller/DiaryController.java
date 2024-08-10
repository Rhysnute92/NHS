package uk.ac.cf.spring.nhs.Diary.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinForm;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Entity.Photo;
import uk.ac.cf.spring.nhs.Diary.Service.DiaryEntryService;
import uk.ac.cf.spring.nhs.Diary.Service.PhotoService;

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

    @GetMapping("")
    public String diary(Model model) {
        List<DiaryEntry> diaryEntries = diaryEntryService.getDiaryEntriesByUserId(1);
        model.addAttribute("diaryEntries", diaryEntries);
        return "diary/diary";
    }

    @GetMapping("/checkin")
    public String checkin(Model model) {
        return "diary/checkin";
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkin(
            @ModelAttribute CheckinForm checkinForm,
            RedirectAttributes redirectAttributes
    ) {
        System.out.println(checkinForm);
        try {
            diaryEntryService.createAndSaveDiaryEntry(checkinForm);

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("status", "success");
            responseBody.put("message", "Check-in successful. Redirecting to diary...");

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            System.out.println(e.getMessage());

            // Create a response body with error details
            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("status", "error");
            responseBody.put("message", "Error during check-in: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }




    @GetMapping("/photos")
    public String photos(Model model) {
        List<Photo> photos = photoService.getPhotosByUserId(1);
        model.addAttribute("photos", photos);
        return "diary/photos";
    }


    @PostMapping("/photos")
    public ResponseEntity<?> uploadPhoto(
            @RequestParam("photo") MultipartFile photo,
            RedirectAttributes redirectAttributes
    ) {
        try {
            if (photo != null && !photo.isEmpty()) {
                Photo savedPhoto = photoService.savePhoto(photo, 1);
                return ResponseEntity.ok(savedPhoto);  // Return the saved Photo object with a 200 OK status
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Uploaded photo is empty");  // Return a 400 Bad Request status with an error message
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());  // Return a 500 Internal Server Error status with the error message
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