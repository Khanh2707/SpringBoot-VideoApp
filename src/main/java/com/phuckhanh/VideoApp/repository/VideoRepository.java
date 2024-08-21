package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
}
