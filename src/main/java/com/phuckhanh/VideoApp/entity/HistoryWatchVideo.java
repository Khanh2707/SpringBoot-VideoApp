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
public class HistoryWatchVideo {
    @EmbeddedId
    HistoryWatchVideoKey idHistoryWatchVideoKey;

    @ManyToOne
    @MapsId("idChannel")
    @JoinColumn(name = "id_channel_history_watch_video")
    Channel channel;

    @ManyToOne
    @MapsId("idVideo")
    @JoinColumn(name = "id_video_history_watch_video")
    Video video;

    LocalDateTime dateTimeWatch;
}
