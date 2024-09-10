package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryWatchVideo;
import com.phuckhanh.VideoApp.entity.HistoryWatchVideoKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryWatchVideoRepository extends JpaRepository<HistoryWatchVideo, HistoryWatchVideoKey> {
    List<HistoryWatchVideo> findAllByVideo_IdVideo(Integer idVideo);

    Page<HistoryWatchVideo> findAllByChannel_IdChannelOrderByDateTimeWatchDesc(Integer idChannel, Pageable pageable);

    List<HistoryWatchVideo> findAllByChannel_IdChannel(Integer idChannel);

    @Query("SELECT h FROM HistoryWatchVideo h WHERE h.channel.idChannel = :idChannel AND LOWER(h.video.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<HistoryWatchVideo> findByChannelIdAndVideoTitleContainingIgnoreCase(@Param("idChannel") Integer idChannel, @Param("keyword") String keyword, Pageable pageable);
}
