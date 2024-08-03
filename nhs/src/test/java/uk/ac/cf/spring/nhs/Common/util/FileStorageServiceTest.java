package uk.ac.cf.spring.nhs.Common.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileStorageServiceTest {

    private FileStorageService fileStorageService;

    @Mock
    private MultipartFile multipartFile;

    private final String uploadDir = "uploads/";
    private final long maxFileSize = 5242880; // 5 MB

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        fileStorageService = new FileStorageService(uploadDir, maxFileSize);
    }

    @Test
    void storeFileStoresFileAndReturnsPath() throws IOException {
        String fileName = "testfile.png";
        Path filePath = Path.of(uploadDir).resolve(fileName).toAbsolutePath().normalize();
        byte[] content = "file content".getBytes();
        InputStream inputStream = new ByteArrayInputStream(content);

        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
        when(multipartFile.getInputStream()).thenReturn(inputStream);
        when(multipartFile.getSize()).thenReturn((long) content.length);

        String storedFilePath = fileStorageService.storeFile(multipartFile);

        assertEquals(filePath.toString(), storedFilePath);
        verify(multipartFile, times(1)).getInputStream();
    }

    @Test
    void storeFileThrowsExceptionWhenFileNameContainsInvalidCharacters() {
        String fileName = "../testfile.png";
        when(multipartFile.getOriginalFilename()).thenReturn(fileName);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileStorageService.storeFile(multipartFile);
        });

        assertTrue(exception.getMessage().contains("Filename contains invalid path sequence"));
    }

    @Test
    void storeFileThrowsExceptionWhenFileIsNull() {
        when(multipartFile.getOriginalFilename()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            fileStorageService.storeFile(multipartFile);
        });
    }

    @Test
    void storeFileThrowsExceptionWhenFileSizeExceedsLimit() {
        String fileName = "largefile.png";
        long largeFileSize = maxFileSize + 1;

        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
        when(multipartFile.getSize()).thenReturn(largeFileSize);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            fileStorageService.storeFile(multipartFile);
        });

        assertTrue(exception.getMessage().contains("File size exceeds the maximum allowed size"));
    }
}
