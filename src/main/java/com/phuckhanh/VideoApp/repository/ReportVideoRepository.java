package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.ReportVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportVideoRepository extends JpaRepository<ReportVideo, Integer> {
    Page<ReportVideo> findAllByTypeReportVideo_IdTypeReportVideo(Integer idTypeReportVideo, Pageable pageable);
}
