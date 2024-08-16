package uk.ac.cf.spring.nhs.Photo.DTO;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class PhotoListDTO {

    private List<PhotoDTO> photos;
    public PhotoListDTO() {
        this.photos = new ArrayList<>();
    }

    public PhotoListDTO(List<PhotoDTO> photos) {
        this.photos = photos != null ? photos : new ArrayList<>();
    }

    public List<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDTO> photos) {
        this.photos = photos;
    }
}
