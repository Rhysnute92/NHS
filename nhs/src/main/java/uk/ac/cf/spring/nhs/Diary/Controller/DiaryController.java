package uk.ac.cf.spring.nhs.Diary.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Common.util.NavMenuItem;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Service.DiaryEntryService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    DiaryEntryService diaryEntryService;

    @GetMapping("")
    public ModelAndView diary(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<DiaryEntry> diaryEntries = diaryEntryService.getAllDiaryEntries();

        modelAndView.addObject("diaryEntries", diaryEntries);
        modelAndView.setViewName("diary/diary");
        return modelAndView;
    }

    @GetMapping("/checkin")
    public ModelAndView checkin(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("diary/checkin");
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