package uk.ac.cf.spring.nhs.Diary.Service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uk.ac.cf.spring.nhs.Diary.Entity.Photo;
import uk.ac.cf.spring.nhs.Diary.Repository.PhotoRepository;
import uk.ac.cf.spring.nhs.Files.Service.FileStorageService;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    private final FileStorageService fileStorageService;

    public PhotoService(PhotoRepository photoRepository, FileStorageService fileStorageService) {
        this.photoRepository = photoRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional(readOnly = true)
    public List<Photo> getPhotosByUserId(int userId) {
        return photoRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, "date"));
    }

    public Photo savePhoto(MultipartFile file, int userId) {
        String photoUrl = fileStorageService.storeFile(file);
        Photo photo = new Photo(photoUrl, new Date(), "", userId);

        return photoRepository.save(photo);
    }

}
