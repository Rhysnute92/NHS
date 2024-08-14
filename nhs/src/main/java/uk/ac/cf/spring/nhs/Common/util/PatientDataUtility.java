package uk.ac.cf.spring.nhs.Common.util;

import java.time.LocalDate;
import java.time.Period;

public class PatientDataUtility {
    
    public int calculateAge(LocalDate dob){
        LocalDate now = LocalDate.now();
        Period period = Period.between(dob, now); 
        return period.getYears();
    }
}
