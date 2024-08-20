package uk.ac.cf.spring.nhs.Provider.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.cf.spring.nhs.Measurement.DTO.MeasurementDTO;
import uk.ac.cf.spring.nhs.Measurement.Entity.Measurement;
import uk.ac.cf.spring.nhs.Measurement.Service.MeasurementService;

import java.awt.image.RescaleOp;
import java.util.List;

@Controller
@RequestMapping("/patientprofile/statistics")
public class PatientStatisticsController {

    private long userId = 1;
    @Autowired
    MeasurementService measurementService;

    @GetMapping
    public String patientStatistics() {
        return "patientprofile/patientStatistics";
    }

    @GetMapping("/measurements")
    @RequestMapping("/measurements")
    public ResponseEntity<List<Measurement>> patientMeasurements() {
        List<Measurement> measurements = measurementService.getMeasurementsByUserId(userId);

        return ResponseEntity.ok(measurements);
    }
}
