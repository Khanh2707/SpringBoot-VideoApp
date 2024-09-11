package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.CommentVideo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentVideoRepository extends JpaRepository<CommentVideo, Integer> {
    @Query("SELECT (COUNT(cv) + " +
            "COALESCE((SELECT COUNT(cic) FROM CommentInComment cic WHERE cic.commentVideo.video.idVideo = :idVideo), 0)) " +
            "FROM CommentVideo cv WHERE cv.video.idVideo = :idVideo")
    long countCommentVideosByVideo(Integer idVideo);

    Page<CommentVideo> findAllByVideo_IdVideo(Integer idVideo, Pageable pageable);
}
