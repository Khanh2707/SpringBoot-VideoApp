package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.TypeReportVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeReportVideoRepository extends JpaRepository<TypeReportVideo, Integer> {
}
