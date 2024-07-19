package uk.ac.cf.spring.nhs.Diary.Service;

import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.Diary.DiaryEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DiaryService {
    public DiaryService() {
    }

    public List<DiaryEntry> getDiaryEntries() {
        List<DiaryEntry> diaryEntries = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM");

        // Generate some dummy data for now until we have a database
        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -i);
            Date date = calendar.getTime();
            String formattedDate = formatter.format(date);
            diaryEntries.add(new DiaryEntry(date, formattedDate, "Content for entry " + (i + 1)));
        }

        return diaryEntries;
    }
}
