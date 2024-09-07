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
public class HistoryNotificationCommentVideo {
    @EmbeddedId
    HistoryNotificationCommentVideoKey idHistoryNotificationCommentVideoKey;

    @ManyToOne
    @MapsId("idCommentVideo")
    @JoinColumn(name = "id_comment_video_history_notification_comment_video")
    CommentVideo commentVideo;

    @ManyToOne
    @MapsId("idChannel")
    @JoinColumn(name = "id_channel_history_notification_comment_video")
    Channel channel;

    Boolean isCheck;
}
