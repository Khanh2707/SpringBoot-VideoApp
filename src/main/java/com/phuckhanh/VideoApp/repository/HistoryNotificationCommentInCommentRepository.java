package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentInComment;
import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentInCommentKey;
import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryNotificationCommentInCommentRepository extends JpaRepository<HistoryNotificationCommentInComment, HistoryNotificationCommentInCommentKey> {
    Page<HistoryNotificationCommentInComment> findAllByChannel_IdChannelOrderByCommentInCommentDesc(Integer idChannel, Pageable pageable);

    Optional<HistoryNotificationCommentInComment> findByIdHistoryNotificationCommentInCommentKey(HistoryNotificationCommentInCommentKey historyNotificationCommentInCommentKey);

    @Query("SELECT h FROM HistoryNotificationCommentInComment h " +
            "JOIN h.commentInComment c " +
            "WHERE h.channel.idChannel = :idChannel " +
            "AND c.dateTimeComment BETWEEN :startDate AND :endDate")
    List<HistoryNotificationCommentInComment> findAllByChannelIdAndTimeBetween(
            @Param("idChannel") Integer idChannel,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
