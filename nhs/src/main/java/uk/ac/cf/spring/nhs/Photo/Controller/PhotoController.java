package uk.ac.cf.spring.nhs.Photo.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Photo.DTO.PhotoDTO;
import uk.ac.cf.spring.nhs.Photo.DTO.PhotoListDTO;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Photo.Service.PhotoService;
import uk.ac.cf.spring.nhs.Security.CustomUserDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/diary/photos")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    public String photos(Model model,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        List<Photo> photos = photoService.getPhotosByUserId(userId);
        model.addAttribute("objectMapper", objectMapper);
        model.addAttribute("photos", photos);
        return "diary/photos";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePhoto(@PathVariable Long id) {
        try {
            boolean deleted = photoService.deletePhotoById(id);
            if (deleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Photo not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the photo");
        }
    }


    @PostMapping
    public ResponseEntity<?> uploadPhoto(@ModelAttribute PhotoListDTO photos,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {

        List<Object> results = new ArrayList<>();

        try {
            Long userId = userDetails.getUserId();

            for (PhotoDTO photo : photos.getPhotos()) {
                if (photo != null && photo.getFile() != null) {
                    Photo savedPhoto = photoService.savePhoto(photo, userId);
                    results.add(savedPhoto); // Add each successfully saved photo to the results list
                }
            }

            if (results.isEmpty()) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "No valid photos to process");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            } else {
                return ResponseEntity.ok(results);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
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
