package uk.ac.cf.spring.nhs.Diary.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Service.DiaryService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/diary")
public class DiaryController {

    @Autowired
    DiaryService diaryService;

    @GetMapping("")
    public ModelAndView diary(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<DiaryEntry> diaryEntries = diaryService.getDiaryEntries();

        modelAndView.addObject("diaryEntries", diaryEntries);
//        modelAndView.setViewName("diary/diary");
        if (DeviceDetector.isMobile(request)) {
            modelAndView.setViewName("mobile/diary");
        } else {
            modelAndView.setViewName("desktop/diary");
        }

        return modelAndView;
    }

    @GetMapping("/checkin")
    public ModelAndView addDiaryEntry(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("diary/checkin");
        if (DeviceDetector.isMobile(request)) {
            modelAndView.setViewName("mobile/checkin");
        } else {
            modelAndView.setViewName("desktop/checkin");
        }
        return modelAndView;
    }
}