package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryNotificationVideo;
import com.phuckhanh.VideoApp.entity.HistoryNotificationVideoKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryNotificationVideoRepository extends JpaRepository<HistoryNotificationVideo, HistoryNotificationVideoKey> {
    List<HistoryNotificationVideo> findAllByChannel_IdChannel(Integer idChannel);
}
