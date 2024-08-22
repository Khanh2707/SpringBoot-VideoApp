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
public class HistoryNotificationVideo {
    @EmbeddedId
    HistoryNotificationVideoKey idHistoryNotificationVideoKey;

    @ManyToOne
    @MapsId("idChannel")
    @JoinColumn(name = "id_channel_history_notification_video")
    Channel channel;

    @ManyToOne
    @MapsId("idNotificationVideo")
    @JoinColumn(name = "id_notification_video_history_notification_video")
    NotificationVideo notificationVideo;

    Boolean isCheck;
}
