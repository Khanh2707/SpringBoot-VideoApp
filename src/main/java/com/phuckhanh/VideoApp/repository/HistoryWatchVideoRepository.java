package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryWatchVideo;
import com.phuckhanh.VideoApp.entity.HistoryWatchVideoKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryWatchVideoRepository extends JpaRepository<HistoryWatchVideo, HistoryWatchVideoKey> {
    List<HistoryWatchVideo> findAllByVideo_IdVideo(Integer idVideo);

    List<HistoryWatchVideo> findAllByChannel_IdChannelOrderByDateTimeWatchDesc(Integer idChannel);

    List<HistoryWatchVideo> findAllByChannel_IdChannel(Integer idChannel);
}
