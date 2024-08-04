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
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Diary.DTO.CheckinForm;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Service.DiaryEntryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    DiaryEntryService diaryEntryService;

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
            @RequestParam(value = "checkin-photos-upload", required = false) List<MultipartFile> photos
    ) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            diaryEntryService.createAndSaveDiaryEntry(checkinForm, photos);

            modelAndView.setViewName("redirect:/diary"); // Redirect to the desired view
        } catch (Exception e) {
            System.out.println(e.getMessage());
            modelAndView.setViewName("diary/checkin"); // Redirect to the check-in view if there's an error
        }
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