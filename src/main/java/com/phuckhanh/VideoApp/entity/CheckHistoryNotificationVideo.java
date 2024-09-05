package com.phuckhanh.VideoApp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class CheckHistoryNotificationVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idCheckHistoryNotificationVideo;
    LocalDateTime dateTimeCheck;

    @OneToOne
    @JoinColumn(name = "id_channel_check_history_notification_video", referencedColumnName = "idChannel")
    Channel channel;
}
