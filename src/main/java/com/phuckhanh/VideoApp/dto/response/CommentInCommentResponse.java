package com.phuckhanh.VideoApp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentInCommentResponse {
    Integer idCommentInComment;
    String content;
    LocalDateTime dateTimeComment;
    ChannelResponse channel;
    CommentVideoResponse commentVideo;
}
