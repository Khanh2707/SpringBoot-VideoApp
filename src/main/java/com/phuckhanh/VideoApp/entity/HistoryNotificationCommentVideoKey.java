package com.phuckhanh.VideoApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Embeddable
public class HistoryNotificationCommentVideoKey implements Serializable {
    @Column(name = "id_comment_video_history_notification_comment_video")
    Integer idCommentVideo;

    @Column(name = "id_channel_history_notification_comment_video")
    Integer idChannel;
}
