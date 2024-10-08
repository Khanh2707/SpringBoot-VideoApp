package com.phuckhanh.VideoApp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ChannelUpdateAvatarRequest {
    MultipartFile fileAvatar;
    Boolean delete;
}
