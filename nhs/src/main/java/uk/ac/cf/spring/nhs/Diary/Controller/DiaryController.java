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
    public ModelAndView checkin(
            @ModelAttribute CheckinForm checkinForm,
            RedirectAttributes redirectAttributes
    ) {
        System.out.println(checkinForm);
        ModelAndView modelAndView = new ModelAndView();
        try {
            diaryEntryService.createAndSaveDiaryEntry(checkinForm);

            modelAndView.setViewName("redirect:/diary"); // Redirect to the diary view
        } catch (Exception e) {
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            modelAndView.setViewName("diary/checkin"); // Redirect to the check-in view if there's an error
        }
        return modelAndView;
    }


    @GetMapping("/photos")
    public String photos(Model model) {
        List<Photo> photos = photoService.getPhotosByUserId(1);
        model.addAttribute("photos", photos);
        return "diary/photos";
    }

    @PostMapping("/photos")
    public ModelAndView uploadPhotos(
            @RequestParam("photo") MultipartFile photo,
            RedirectAttributes redirectAttributes
    ) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (photo != null && !photo.isEmpty()) {
                photoService.savePhoto(photo, 1);
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        modelAndView.setViewName("redirect:/diary/photos");
        return modelAndView;
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