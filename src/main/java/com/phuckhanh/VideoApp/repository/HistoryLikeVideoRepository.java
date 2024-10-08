package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryLikeVideo;
import com.phuckhanh.VideoApp.entity.HistoryLikeVideoKey;
import com.phuckhanh.VideoApp.entity.HistoryWatchVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryLikeVideoRepository extends JpaRepository<HistoryLikeVideo, HistoryLikeVideoKey> {
    @Query("SELECT COUNT(h) FROM HistoryLikeVideo h WHERE h.video.idVideo = :idVideo")
    long countChannelLikeVideo(Integer idVideo);

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM HistoryLikeVideo h WHERE h.channel.idChannel = :idChannel AND h.video.idVideo = :idVideo")
    boolean isChannelLikeVideo(Integer idChannel, Integer idVideo);

    List<HistoryLikeVideo> findAllByVideo_IdVideo(Integer idVideo);

    Page<HistoryLikeVideo> findAllByChannel_IdChannelOrderByDateTimeLikeDesc(Integer idChannel, Pageable pageable);

    @Query("SELECT h FROM HistoryLikeVideo h WHERE h.channel.idChannel = :idChannel AND LOWER(h.video.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<HistoryLikeVideo> findByChannelIdAndVideoTitleContainingIgnoreCase(@Param("idChannel") Integer idChannel, @Param("keyword") String keyword, Pageable pageable);
}
