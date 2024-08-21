package com.phuckhanh.VideoApp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoCreationRequest {
    String title;
    String description;
    Boolean hide;
    Integer idCategory;
    Integer idChannel;
    MultipartFile fileVideo;
    MultipartFile fileImagePreview;
}
