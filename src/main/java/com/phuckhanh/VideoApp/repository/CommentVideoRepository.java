package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.CommentVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentVideoRepository extends JpaRepository<CommentVideo, Integer> {
    @Query("SELECT (COUNT(cv) + " +
            "COALESCE((SELECT COUNT(cic) FROM CommentInComment cic WHERE cic.commentVideo.video.idVideo = :idVideo), 0)) " +
            "FROM CommentVideo cv WHERE cv.video.idVideo = :idVideo")
    long countCommentVideosByVideo(Integer idVideo);

    List<CommentVideo> findAllByVideo_IdVideoOrderByIdCommentVideoDesc(Integer idVideo);

    List<CommentVideo> findAllByVideo_IdVideoOrderByIdCommentVideoAsc(Integer idVideo);
}
