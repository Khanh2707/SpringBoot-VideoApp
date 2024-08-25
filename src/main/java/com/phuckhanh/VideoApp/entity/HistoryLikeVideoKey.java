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
public class HistoryLikeVideoKey implements Serializable {
    @Column(name = "id_channel_history_like_video")
    Integer idChannel;

    @Column(name = "id_video_history_like_video")
    Integer idVideo;
}
