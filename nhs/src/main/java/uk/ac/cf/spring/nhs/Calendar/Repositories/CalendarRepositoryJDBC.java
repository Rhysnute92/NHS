//package uk.ac.cf.spring.nhs.Calendar.Repositories;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class CalendarRepositoryJDBC implements CalendarRepository {
//    private JdbcTemplate jdbc;
//    private RowMapper<Calendar> calendarMapper;
//
//    public CalendarRepositoryJDBC(JdbcTemplate aJdbc) {
//        this.jdbc = aJdbc;
//        setCalendarMapper();
//    }
//
//    private void setCalendarMapper() {
//        calendarMapper = (rs, i) -> new Calendar(
//                rs.getString("appt_date"),
//                rs.getString("app_time"),
//                rs.getString("appt_type"),
//                rs.getString("appt_provider"),
//                rs.getString("appt_info")
//        );
//    }
//
//    @Override
//    public List<Calendar> getAllAppointments() {
//        String sql = "SELECT * FROM appointments";
//        return jdbc.query(sql, calendarMapper);
//    }
//
//    @Override
//    public void addAppointment(Calendar calendar) {
//        String sql = "INSERT INTO appointments(appt_date, appt_time, appt_type, appt_provider, appt_info) VALUES (?, ?, ?, ?, ?)";
//        jdbc.update(sql, calendar.getAppointmentDate(), calendar.getAppointmentTime(), calendar.getAppointmentType(), calendar.getAppointmentProvider(), calendar.getAppointmentInfo());
//    }
//
//    @Override
//    public void addAppointment(String testAppt_Date, String testAppt_Time, String testAppt_Type, String testAppt_Provider, String testAppt_Info) {
//
//    }
//}
