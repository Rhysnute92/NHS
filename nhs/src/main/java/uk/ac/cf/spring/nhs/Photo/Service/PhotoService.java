package uk.ac.cf.spring.nhs.Photo.Service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.ac.cf.spring.nhs.Photo.DTO.PhotoDTO;
import uk.ac.cf.spring.nhs.Files.Service.FileStorageService;
import uk.ac.cf.spring.nhs.Photo.Entity.Photo;
import uk.ac.cf.spring.nhs.Photo.Repository.PhotoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    private final FileStorageService fileStorageService;

    public PhotoService(PhotoRepository photoRepository, FileStorageService fileStorageService) {
        this.photoRepository = photoRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional(readOnly = true)
    public List<Photo> getPhotosByUserId(long userId) {
        return photoRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, "date"));
    }

    public Photo savePhoto(PhotoDTO photoDTO, long userId) {
        String photoUrl = fileStorageService.storeFile(photoDTO.getFile());
        Photo photo = new Photo(photoUrl, new Date(), "", userId);
        return photoRepository.save(photo);
    }

    public boolean deletePhotoById(long photoId) {
        Optional<Photo> optionalPhoto = photoRepository.findById(photoId);

        if (optionalPhoto.isPresent()) {
            photoRepository.deleteById(photoId);
            return true;
        }

        return false;
    }
}
