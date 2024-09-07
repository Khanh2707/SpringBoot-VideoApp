package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentVideo;
import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentVideoKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryNotificationCommentVideoRepository extends JpaRepository<HistoryNotificationCommentVideo, HistoryNotificationCommentVideoKey> {
}
