package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryNotificationVideo;
import com.phuckhanh.VideoApp.entity.HistoryNotificationVideoKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryNotificationVideoRepository extends JpaRepository<HistoryNotificationVideo, HistoryNotificationVideoKey> {
}
