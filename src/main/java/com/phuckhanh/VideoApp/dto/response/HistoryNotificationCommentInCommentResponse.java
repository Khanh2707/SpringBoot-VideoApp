package com.phuckhanh.VideoApp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HistoryNotificationCommentInCommentResponse {
    Boolean isCheck;
    CommentInCommentResponse commentInComment;
}
