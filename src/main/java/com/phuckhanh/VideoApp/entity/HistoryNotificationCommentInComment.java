package com.phuckhanh.VideoApp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class HistoryNotificationCommentInComment {
    @EmbeddedId
    HistoryNotificationCommentInCommentKey idHistoryNotificationCommentInCommentKey;

    @ManyToOne
    @MapsId("idCommentInComment")
    @JoinColumn(name = "id_comment_in_comment_history_notification_comment_in_comment")
    CommentInComment commentInComment;

    @ManyToOne
    @MapsId("idChannel")
    @JoinColumn(name = "id_channel_history_notification_comment_in_comment")
    Channel channel;

    Boolean isCheck;
}
