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
public class HistoryWatchVideoKey implements Serializable {
    @Column(name = "id_channel_history_watch_video")
    Integer idChannel;

    @Column(name = "id_video_history_watch_video")
    Integer idVideo;
}
