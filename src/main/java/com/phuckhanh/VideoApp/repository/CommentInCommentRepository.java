package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.CommentInComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentInCommentRepository extends JpaRepository<CommentInComment, Integer> {
    @Query("SELECT COUNT(c) FROM CommentInComment c WHERE c.commentVideo.idCommentVideo = :idCommentVideo")
    long countCommentByCommentVideo(Integer idCommentVideo);

    Page<CommentInComment> findAllByCommentVideo_IdCommentVideo(Integer idCommentVideo, Pageable pageable);
}
