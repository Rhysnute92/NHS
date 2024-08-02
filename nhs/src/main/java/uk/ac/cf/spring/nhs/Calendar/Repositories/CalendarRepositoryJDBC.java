package uk.ac.cf.spring.nhs.Calendar.Repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
        String sql = "INSERT INTO appointments(ApptTime, ApptProvider) VALUES (?, ?)";
        jdbc.update(sql, calendar.getAppointmentTime(), calendar.getAppointmentProvider());
    }
}
