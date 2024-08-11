package uk.ac.cf.spring.nhs.Diary.DTO;

import org.springframework.web.multipart.MultipartFile;

public class PhotoDTO {
        private MultipartFile file;
        private String bodyPart;

        public PhotoDTO() {}

        public PhotoDTO(MultipartFile file, String bodyPart) {
            this.file = file;
            this.bodyPart = bodyPart;
        }

        public MultipartFile getFile() {
            return file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }

        public String getBodyPart() {
            return bodyPart;
        }

        public void setBodyPart(String bodyPart) {
            this.bodyPart = bodyPart;
        }

        @Override
        public String toString() {
            return "PhotoDTO{" +
                    "file=" + file +
                    ", bodyPart='" + bodyPart + '\'' +
                    '}';
        }
}
