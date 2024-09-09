package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.CommentInComment;
import com.phuckhanh.VideoApp.entity.CommentVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentInCommentRepository extends JpaRepository<CommentInComment, Integer> {
    @Query("SELECT COUNT(c) FROM CommentInComment c WHERE c.commentVideo.idCommentVideo = :idCommentVideo")
    long countCommentByCommentVideo(Integer idCommentVideo);

    List<CommentInComment> findAllByCommentVideo_IdCommentVideoOrderByIdCommentInCommentDesc(Integer idCommentVideo);

    List<CommentInComment> findAllByCommentVideo_IdCommentVideoOrderByIdCommentInCommentAsc(Integer idCommentVideo);
}
