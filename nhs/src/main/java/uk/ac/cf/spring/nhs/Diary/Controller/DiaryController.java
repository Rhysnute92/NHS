package uk.ac.cf.spring.nhs.Diary.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.cf.spring.nhs.Common.util.DeviceDetector;
import uk.ac.cf.spring.nhs.Diary.Entity.DiaryEntry;
import uk.ac.cf.spring.nhs.Diary.Service.DiaryService;

import java.util.List;

@Controller
public class DiaryController {

    @Autowired
    DiaryService diaryService;

    @GetMapping("/diary")
    public ModelAndView diary(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<DiaryEntry> diaryEntries = diaryService.getDiaryEntries();

        modelAndView.addObject("diaryEntries", diaryEntries);

        if (DeviceDetector.isMobile(request)) {
            modelAndView.setViewName("mobile/diary");
        } else {
            modelAndView.setViewName("desktop/diary");
        }

        return modelAndView;
    }
}