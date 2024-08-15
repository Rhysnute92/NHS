package uk.ac.cf.spring.nhs.AddPatient.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uk.ac.cf.spring.nhs.AddPatient.DTO.RegisterRequest;
import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;
import uk.ac.cf.spring.nhs.AddPatient.Repository.PatientRepository;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentialsRepository;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

@Service
@EnableWebSecurity
public class PatientService {

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // Generating a key for the patient
    public SecretKey generatePatientKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    // Encrypting Patient data
    public String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Decrypting Patient data
    public String decrypt(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public String registerPatient(RegisterRequest request) {
        try {
            SecretKey secretKey = generatePatientKey();
            String encryptedEmail = encrypt(request.getPatientEmail(), secretKey);
            String encryptedMobile = encrypt(request.getPatientMobile(), secretKey);
            String encryptedName = encrypt(request.getPatientName(), secretKey);
            String encryptedLastName = encrypt(request.getPatientLastName(), secretKey);

            String genericPassword = generateGenericPassword();
            String encodedPassword = passwordEncoder.encode(genericPassword);

            // Generate a token for password setup
            String passwordSetupToken = generatePasswordSetupToken();

            // Create UserCredentials
            UserCredentials userCredentials = new UserCredentials();
            userCredentials.setUserName(request.getPatientEmail());
            userCredentials.setUserPassword(encodedPassword);
            userCredentials.setUserRole("ROLE_PATIENT");
            userCredentials.setPasswordSetupToken(passwordSetupToken);
            userCredentialsRepository.save(userCredentials);

            Long userId = userCredentials.getUserId();

            // Create Patient
            Patient patient = new Patient();
            patient.setUserId(userId);
            patient.setPatientEmail(encryptedEmail);
            patient.setPatientMobile(encryptedMobile);
            patient.setPatientName(encryptedName);
            patient.setPatientLastName(encryptedLastName);
            patient.setNhsNumber(request.getNhsNumber());
            patient.setPatientDOB(request.getPatientDOB());
            patient.setPatientTitle(request.getPatientTitle());
            patient.setPatientClinic(request.getPatientClinic());
            patient.setEncryptionKey(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            patientRepository.save(patient);

            // Send password setup email with unencrypted email from form submission
            sendPasswordSetupEmail(request.getPatientEmail(), passwordSetupToken);

            return "Patient registered successfully.";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while registering the patient.";
        }
    }

    // Method to send the initial password setup email following account creation
    private void sendPasswordSetupEmail(String email, String token) {
        String url = "http://localhost:8080/reset-password?token=" + token;
        String subject = "Set Your Password";
        String text = "Dear Patient, Welcome to The University Hospitals of Derby and Burton NHS Foundation Trust Lymphoedema Services" +
                "\n\nPlease set your new password via the link below:\n" + url + "\n\nThank you.";

        emailService.sendSimpleMessage(email, subject, text);
    }

    // Simple generic password generator for now
    private String generateGenericPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    // Generate a unique token for password setup
    private String generatePasswordSetupToken() {
        return UUID.randomUUID().toString();
    }
}
