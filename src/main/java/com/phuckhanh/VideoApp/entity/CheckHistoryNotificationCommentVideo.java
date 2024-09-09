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
public class CheckHistoryNotificationCommentVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idCheckHistoryNotificationCommentVideo;
    LocalDateTime dateTimeCheck;

    @OneToOne
    @JoinColumn(name = "id_channel_check_history_notification_comment_video", referencedColumnName = "idChannel")
    Channel channel;
}
