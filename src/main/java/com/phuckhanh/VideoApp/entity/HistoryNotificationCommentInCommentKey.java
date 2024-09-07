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
public class HistoryNotificationCommentInCommentKey implements Serializable {
    @Column(name = "id_comment_in_comment_history_notification_comment_in_comment")
    Integer idCommentInComment;

    @Column(name = "id_channel_history_notification_comment_in_comment")
    Integer idChannel;
}
