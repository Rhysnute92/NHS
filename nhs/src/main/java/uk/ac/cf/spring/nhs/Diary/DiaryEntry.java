package uk.ac.cf.spring.nhs.Diary;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class DiaryEntry {
    private Date date;
    private String formattedDate;
    private String content;


    public DiaryEntry(Date date, String formattedDate, String content) {
        this.date = date;
        this.formattedDate = formattedDate;
        this.content = content;
    }

}
