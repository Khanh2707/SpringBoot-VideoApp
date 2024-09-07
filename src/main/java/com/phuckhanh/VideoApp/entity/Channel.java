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
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idChannel;
    String avatar;
    String name;
    String nameUnique;
    String description;

    @OneToOne
    @JoinColumn(name = "id_account_channel", referencedColumnName = "idAccount")
    Account account;

    @OneToOne(mappedBy = "channel", cascade = CascadeType.ALL)
    CheckHistoryNotificationVideo checkHistoryNotificationVideo;

    @OneToMany(mappedBy = "channel")
    List<Video> videos;

    @OneToMany(mappedBy = "channel")
    List<HistoryNotificationVideo> historyNotificationVideos;

    @OneToMany(mappedBy = "channel")
    List<CommentVideo> commentVideos;

    @OneToMany(mappedBy = "channel")
    List<CommentInComment> commentInComments;

    @OneToMany(mappedBy = "channel")
    List<HistoryNotificationCommentVideo> historyNotificationCommentVideos;

    @OneToMany(mappedBy = "channel")
    List<HistoryNotificationCommentInComment> historyNotificationCommentInComments;
}
