package com.phuckhanh.VideoApp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idVideo;
    String title;
    String linkVideo;
    String description;
    String imagePreview;
    LocalDateTime dateTimeCreate;
    Integer view;
    Boolean hide;
    Boolean ban;

    @OneToOne(mappedBy = "video", cascade = CascadeType.ALL)
    NotificationVideo notificationVideo;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL)
    List<CommentVideo> commentVideos;

    @OneToMany(mappedBy = "video")
    List<ReportVideo> reportVideos;

    @ManyToOne
    @JoinColumn(name = "id_category_video")
    Category category;

    @ManyToOne
    @JoinColumn(name = "id_channel_video")
    Channel channel;
}
