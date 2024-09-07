package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentInComment;
import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentInCommentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryNotificationCommentInCommentRepository extends JpaRepository<HistoryNotificationCommentInComment, HistoryNotificationCommentInCommentKey> {
}
