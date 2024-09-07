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
public class CommentVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idCommentVideo;
    String content;
    LocalDateTime dateTimeComment;

    @OneToMany(mappedBy = "commentVideo", cascade = CascadeType.ALL)
    List<CommentInComment> commentInComments;

    @OneToMany(mappedBy = "commentVideo", cascade = CascadeType.ALL)
    List<HistoryNotificationCommentVideo> historyNotificationCommentVideos;

    @ManyToOne
    @JoinColumn(name = "id_channel_comment_video")
    Channel channel;

    @ManyToOne
    @JoinColumn(name = "id_video_comment_video")
    Video video;
}
