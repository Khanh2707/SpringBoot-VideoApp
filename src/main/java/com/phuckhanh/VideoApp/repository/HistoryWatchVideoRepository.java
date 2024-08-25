package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryLikeVideoKey;
import com.phuckhanh.VideoApp.entity.HistoryWatchVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryWatchVideoRepository extends JpaRepository<HistoryWatchVideo, HistoryLikeVideoKey> {
    List<HistoryWatchVideo> findAllByVideo_IdVideo(Integer idVideo);

    List<HistoryWatchVideo> findAllByChannel_IdChannelOrderByDateTimeWatchDesc(Integer idChannel);
}
