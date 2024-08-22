package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.NotificationVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationVideoRepository extends JpaRepository<NotificationVideo, Integer> {
}
