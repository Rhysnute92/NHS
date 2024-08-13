package uk.ac.cf.spring.nhs.Appointment.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.ac.cf.spring.nhs.Appointments.DTO.AppointmentDTO;
import uk.ac.cf.spring.nhs.Appointments.Model.Appointment;
import uk.ac.cf.spring.nhs.Appointments.Service.AppointmentService;
import uk.ac.cf.spring.nhs.Appointments.Repository.AppointmentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    private Appointment appointment1;
    private Appointment appointment2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        appointment1 = new Appointment();
        appointment1.setApptID(1);
        appointment1.setApptDateTime(LocalDateTime.of(2024, 8, 13, 10, 0));
        appointment1.setApptType("Consultation");
        appointment1.setApptProvider("Dr. Smith");
        appointment1.setApptLocation("Clinic");
        appointment1.setApptInfo("Discuss test results");
        appointment1.setUserID(1L);

        appointment2 = new Appointment();
        appointment2.setApptID(2);
        appointment2.setApptDateTime(LocalDateTime.of(2024, 8, 14, 11, 0));
        appointment2.setApptType("Follow-up");
        appointment2.setApptProvider("Dr. Johnson");
        appointment2.setApptLocation("Hospital");
        appointment2.setApptInfo("Review progress");
        appointment2.setUserID(1L);
    }

    @Test
    public void testGetAllAppointments() {
        List<Appointment> appointments = Arrays.asList(appointment1, appointment2);
        when(appointmentRepository.findAll()).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAllAppointments();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(appointment1, appointment2);
    }

    @Test
    public void testGetAppointmentById() {
        when(appointmentRepository.findById(1)).thenReturn(Optional.of(appointment1));

        Appointment result = appointmentService.getAppointmentById(1);

        assertThat(result).isNotNull();
        assertThat(result.getApptID()).isEqualTo(1);
        assertThat(result.getApptType()).isEqualTo("Consultation");
    }

    @Test
    public void testGetAppointmentsByUserId() {
        List<Appointment> appointments = Arrays.asList(appointment1, appointment2);
        when(appointmentRepository.findByUserID(1)).thenReturn(appointments);

        List<Appointment> result = appointmentService.getAppointmentsByUserId(1);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(appointment1, appointment2);
    }

    @Test
    public void testSaveAppointment() {
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment1);

        Appointment result = appointmentService.saveAppointment(appointment1);

        assertThat(result).isNotNull();
        assertThat(result.getApptID()).isEqualTo(1);
        assertThat(result.getApptType()).isEqualTo("Consultation");
        verify(appointmentRepository, times(1)).save(appointment1);
    }

    @Test
    public void testDeleteAppointment() {
        doNothing().when(appointmentRepository).deleteById(1);

        appointmentService.deleteAppointment(1);

        verify(appointmentRepository, times(1)).deleteById(1);
    }

    @Test
    public void testSaveAppointmentWithDTO() {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setDate("2024-08-13");
        appointmentDTO.setTime("10:00");
        appointmentDTO.setAppointmentType("Consultation");
        appointmentDTO.setProvider("Dr. Smith");
        appointmentDTO.setDescription("Discuss test results");

        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment1);

        Appointment result = appointmentService.saveAppointment(appointmentDTO);

        assertThat(result).isNotNull();
        assertThat(result.getApptID()).isEqualTo(1);
        assertThat(result.getApptType()).isEqualTo("Consultation");
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }
}
