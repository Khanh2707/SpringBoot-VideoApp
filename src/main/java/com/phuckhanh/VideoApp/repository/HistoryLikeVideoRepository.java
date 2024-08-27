package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryLikeVideo;
import com.phuckhanh.VideoApp.entity.HistoryLikeVideoKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryLikeVideoRepository extends JpaRepository<HistoryLikeVideo, HistoryLikeVideoKey> {
    @Query("SELECT COUNT(h) FROM HistoryLikeVideo h WHERE h.video.idVideo = :idVideo")
    long countChannelLikeVideo(Integer idVideo);

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM HistoryLikeVideo h WHERE h.channel.idChannel = :idChannel AND h.video.idVideo = :idVideo")
    boolean isChannelLikeVideo(Integer idChannel, Integer idVideo);

    List<HistoryLikeVideo> findAllByVideo_IdVideo(Integer idVideo);

    List<HistoryLikeVideo> findAllByChannel_IdChannelOrderByDateTimeLikeDesc(Integer idChannel);
}
