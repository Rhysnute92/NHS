package uk.ac.cf.spring.nhs.Files.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cf.spring.nhs.Files.Service.FileStorageService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileStorageServiceTest {

    private FileStorageService fileStorageService;

    @Mock
    private MultipartFile multipartFile;

    private final String uploadDir = "uploads/";

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        fileStorageService = new FileStorageService(uploadDir);
    }

    @Test
    void storeFileStoresFileAndReturnsPath() throws IOException {
        String fileName = "testfile.png";
        byte[] content = "file content".getBytes();
        InputStream inputStream = new ByteArrayInputStream(content);

        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
        when(multipartFile.getInputStream()).thenReturn(inputStream);

        String storedFilePath = fileStorageService.storeFile(multipartFile);

        assertEquals(fileName, storedFilePath);
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
}
