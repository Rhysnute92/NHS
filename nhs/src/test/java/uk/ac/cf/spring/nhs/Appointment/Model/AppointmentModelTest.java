package uk.ac.cf.spring.nhs.Appointment.Model;

import org.junit.jupiter.api.Test;
import uk.ac.cf.spring.nhs.Appointments.Model.Appointment;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AppointmentModelTest {

    /**
     * Test the appointment model with some dummy data
     * Test all the getters
     * Test all the setters
     */
    @Test
    public void testAppointmentModel() {
        Appointment appointment = new Appointment();
        appointment.setApptID(1);
        appointment.setApptDateTime(LocalDateTime.of(2024, 8, 13, 14, 30));
        appointment.setApptType("Consultation");
        appointment.setApptProvider("Dr. Smith");
        appointment.setApptLocation("Room 101");
        appointment.setApptInfo("Initial consultation");
        appointment.setUserID(123L);

        Integer id = appointment.getApptID();
        LocalDateTime dateTime = appointment.getApptDateTime();
        String type = appointment.getApptType();
        String provider = appointment.getApptProvider();
        String location = appointment.getApptLocation();
        String info = appointment.getApptInfo();
        Long userID = appointment.getUserID();

        assertThat(id).isEqualTo(1);
        assertThat(dateTime).isEqualTo(LocalDateTime.of(2024, 8, 13, 14, 30));
        assertThat(type).isEqualTo("Consultation");
        assertThat(provider).isEqualTo("Dr. Smith");
        assertThat(location).isEqualTo("Room 101");
        assertThat(info).isEqualTo("Initial consultation");
        assertThat(userID).isEqualTo(123L);
    }
}

