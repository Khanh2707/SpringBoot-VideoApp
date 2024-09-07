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
public class CommentInComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idCommentInComment;
    String content;
    LocalDateTime dateTimeComment;

    @OneToMany(mappedBy = "commentInComment", cascade = CascadeType.ALL)
    List<HistoryNotificationCommentInComment> historyNotificationCommentInComments;

    @ManyToOne
    @JoinColumn(name = "id_channel_comment_in_comment")
    Channel channel;

    @ManyToOne
    @JoinColumn(name = "id_comment_video_comment_in_comment")
    CommentVideo commentVideo;
}
