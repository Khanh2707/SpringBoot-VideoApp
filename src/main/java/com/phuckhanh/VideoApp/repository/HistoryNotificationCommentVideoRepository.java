package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentVideo;
import com.phuckhanh.VideoApp.entity.HistoryNotificationCommentVideoKey;
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
public interface HistoryNotificationCommentVideoRepository extends JpaRepository<HistoryNotificationCommentVideo, HistoryNotificationCommentVideoKey> {
    Page<HistoryNotificationCommentVideo> findAllByChannel_IdChannelOrderByCommentVideoDesc(Integer idChannel, Pageable pageable);

    Optional<HistoryNotificationCommentVideo> findByIdHistoryNotificationCommentVideoKey(HistoryNotificationCommentVideoKey historyNotificationCommentVideoKey);

    @Query("SELECT h FROM HistoryNotificationCommentVideo h " +
            "JOIN h.commentVideo c " +
            "WHERE h.channel.idChannel = :idChannel " +
            "AND c.dateTimeComment BETWEEN :startDate AND :endDate")
    List<HistoryNotificationCommentVideo> findAllByChannelIdAndTimeBetween(
            @Param("idChannel") Integer idChannel,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
