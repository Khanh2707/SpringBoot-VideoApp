package com.phuckhanh.VideoApp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentVideoResponse {
    Integer idCommentVideo;
    String content;
    LocalDateTime dateTimeComment;
    ChannelResponse channel;
    VideoResponse video;
}
