package uk.ac.cf.spring.nhs.Calendar.Repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CalendarRepositoryJDBC implements CalendarRepository {
    private JdbcTemplate jdbc;
    private RowMapper<Calendar> calendarMapper;

    public CalendarRepositoryJDBC(JdbcTemplate aJdbc) {
        this.jdbc = aJdbc;
        setCalendarMapper();
    }

    private void setCalendarMapper() {
        calendarMapper = (rs, i) -> new Calendar(
                rs.getString("ApptTime"),
                rs.getString("ApptType")
        );
    }

    @Override
    public List<Calendar> getAllAppointments() {
        String sql = "SELECT * FROM appointments";
        return jdbc.query(sql, calendarMapper);
    }

    @Override
    public void addAppointment(Calendar calendar) {
        String sql = "INSERT INTO appointments(ApptTime, ApptType, ApptProvider, ApptInfo, UserID) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql, calendar.getAppointmentTime(), calendar.getAppointmentType(), calendar.getAppointmentProvider(), calendar.getAppointmentInfo());
    }
}
