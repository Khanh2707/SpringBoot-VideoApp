package com.phuckhanh.VideoApp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VideoResponse {
    Integer idVideo;
    String title;
    String linkVideo;
    String description;
    String imagePreview;
    LocalDateTime dateTimeCreate;
    Integer view;
    Boolean hide;
    Boolean ban;
    CategoryResponse category;
    ChannelResponse channel;
}
