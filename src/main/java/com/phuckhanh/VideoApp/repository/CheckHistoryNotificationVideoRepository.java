package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.CheckHistoryNotificationVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckHistoryNotificationVideoRepository extends JpaRepository<CheckHistoryNotificationVideo, Integer> {
    Optional<CheckHistoryNotificationVideo> findByChannel_IdChannel(Integer idChannel);
}
