package uk.ac.cf.spring.nhs.AddPatient.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import uk.ac.cf.spring.nhs.Account.PatientProfileDTO;
import uk.ac.cf.spring.nhs.Account.UpdateRequest;
import uk.ac.cf.spring.nhs.AddPatient.DTO.RegisterRequest;
import uk.ac.cf.spring.nhs.AddPatient.Entity.Patient;
import uk.ac.cf.spring.nhs.AddPatient.Repository.PatientRepository;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentials;
import uk.ac.cf.spring.nhs.Security.UserCredentials.UserCredentialsRepository;
import uk.ac.cf.spring.nhs.Common.util.PatientDataUtility;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.time.LocalDate;
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

    public Patient findPatientbyId(long userId){
        Patient user = patientRepository.findById(userId);
        return user;
    }

    // Generating a key for the patient
    public SecretKey generatePatientKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    //Key decoder function
    private SecretKey decodeKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES"); 
        return key;
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
        String text = "Dear Patient, Welcome to The University Hospitals of Derby and Burton NHS Foundation Trust Lymphoedema Services"
                + "\n\nYou are receiving this email because your Lymphoedema specialist would like to invite you to our patient management application.\n"
                + "\nHere you will be able to track your symptoms, utilise a personal diary, access your management plans set by your provider, and see your upcoming appointments.\n"
                + "\nPlease set your new password via the link below:\n"
                + url + "\n\nAdvice on setting safe passwords can be found at this link:\n"
                + "https://webarchive.nationalarchives.gov.uk/ukgwa/20231221190002/https:/digital.nhs.uk/about-nhs-digital/corporate-information-and-documents/password-policy-guide---example-guide"
                + "\n\nHere can be found information on how your data may be used:\n"
                + "https://www.stepintothenhs.nhs.uk/site-information#:~:text=Consent,cookies%20we%20need%20your%20permission"
                + "\n\nThank you, and welcome to the application.";

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

    public void updatePatient(UpdateRequest request, Patient patient){
        SecretKey key = decodeKey(patient.getEncryptionKey());
        try{
        String newTitle = request.getPatientTitle();
        String newName = encrypt(request.getPatientName(), key);
        String newLastName = encrypt(request.getPatientLastName(), key);
        String newMobile = encrypt(request.getPatientMobile(), key);
        String newEmail = encrypt(request.getPatientEmail(), key);
        patientRepository.updatePatient(newTitle, newName, newLastName, newMobile, newEmail, patient.getUserId());
    } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFullname(Patient patient){
        SecretKey key = decodeKey(patient.getEncryptionKey());
        try{
        String decryptedName = decrypt(patient.getPatientName(), key);
        String decryptedLastname = decrypt(patient.getPatientLastName(), key);
        String fullName = patient.getPatientTitle() + " " + decryptedName + " " + decryptedLastname;
        return fullName;} catch (Exception e) {
            e.printStackTrace();
            return "Could not get patient name";
        }
    }
    
    public PatientProfileDTO profile(long userId){
        Patient user = findPatientbyId(userId);
        SecretKey key = decodeKey(user.getEncryptionKey());
        try{
        String title = user.getPatientTitle();
        String name = decrypt(user.getPatientName(), key);
        String lastname = decrypt(user.getPatientLastName(), key);
        String fullname = title + " " + name + " " + lastname;
        String email = decrypt(user.getPatientEmail(), key);
        String mobile = decrypt(user.getPatientMobile(), key);
        String nhs = user.getNhsNumber();
        LocalDate dob = user.getPatientDOB();
        int age = PatientDataUtility.calculateAge(dob);
        String clinic = user.getPatientClinic();
        PatientProfileDTO profile = new PatientProfileDTO(fullname, title, name, lastname, email, mobile, nhs, dob,age,clinic);
        return profile;} catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
