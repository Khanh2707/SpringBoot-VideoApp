package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.CheckHistoryNotificationCommentInComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheckHistoryNotificationCommentInCommentRepository extends JpaRepository<CheckHistoryNotificationCommentInComment, Long> {
    Optional<CheckHistoryNotificationCommentInComment> findByChannel_IdChannel(Integer idChannel);
}
