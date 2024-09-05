package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryNotificationVideo;
import com.phuckhanh.VideoApp.entity.HistoryNotificationVideoKey;
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
public interface HistoryNotificationVideoRepository extends JpaRepository<HistoryNotificationVideo, HistoryNotificationVideoKey> {
    Page<HistoryNotificationVideo> findAllByChannel_IdChannelOrderByNotificationVideoDesc(Integer idChannel, Pageable pageable);

    Optional<HistoryNotificationVideo> findByIdHistoryNotificationVideoKey(HistoryNotificationVideoKey idHistoryNotificationVideoKey);

    @Query("SELECT h FROM HistoryNotificationVideo h " +
            "JOIN h.notificationVideo n " +
            "JOIN n.video v " +
            "WHERE h.channel.idChannel = :idChannel " +
            "AND v.dateTimeCreate BETWEEN :startDate AND :endDate")
    List<HistoryNotificationVideo> findAllByChannelIdAndTimeBetween(
            @Param("idChannel") Integer idChannel,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
