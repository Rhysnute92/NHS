package uk.ac.cf.spring.nhs.Calendar.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import uk.ac.cf.spring.nhs.Calendar.Model.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    Calendar findByUserID(Integer UserID);
}
