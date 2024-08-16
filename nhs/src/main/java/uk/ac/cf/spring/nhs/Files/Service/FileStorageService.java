package uk.ac.cf.spring.nhs.Files.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // If the file is a blob (e.g. it's an image with no extension), save it as a jpg
        String extension;
        if (originalFilename.equals("blob")) {
            extension = ".jpg";
        } else {
            // Get the file extension
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // Generate a unique filename so files with the same name don't overwrite each other
        String uniqueFilename = UUID.randomUUID().toString() + extension;

        try {
            if (uniqueFilename.contains("..")) {
                throw new RuntimeException("Filename contains invalid path sequence " + uniqueFilename);
            }

            Path targetLocation = this.fileStorageLocation.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return uniqueFilename;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + uniqueFilename + ". Please try again!", ex);
        }
    }
}
