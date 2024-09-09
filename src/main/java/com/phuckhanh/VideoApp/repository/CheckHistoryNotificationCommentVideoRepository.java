package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.CheckHistoryNotificationCommentVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckHistoryNotificationCommentVideoRepository extends JpaRepository<CheckHistoryNotificationCommentVideo, Integer> {
    Optional<CheckHistoryNotificationCommentVideo> findByChannel_IdChannel(Integer idChannel);
}
