package com.phuckhanh.VideoApp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class NotificationVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idNotificationVideo;

    @OneToOne
    @JoinColumn(name = "id_video_notification_video", referencedColumnName = "idVideo")
    Video video;

    @OneToMany(mappedBy = "notificationVideo")
    List<HistoryNotificationVideo> historyNotificationVideos;
}
