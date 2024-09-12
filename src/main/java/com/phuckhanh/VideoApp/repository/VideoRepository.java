package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    @Query("SELECT COUNT(v) FROM Video v WHERE v.channel.nameUnique = :nameUniqueChannel")
    long countByChannelNameUnique(@Param("nameUniqueChannel") String nameUniqueChannel);

    Page<Video> findAllByCategory_IdCategory(Integer idCategory, Pageable pageable);

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN HistoryLikeVideo h ON v.idVideo = h.video.idVideo " +
            "WHERE :idCategory = 0 OR v.category.idCategory = :idCategory " +
            "GROUP BY v " +
            "ORDER BY COUNT(h) DESC")
    Page<Video> findAllOrderByLikeCountDesc(
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN HistoryLikeVideo h ON v.idVideo = h.video.idVideo " +
            "WHERE :idCategory = 0 OR v.category.idCategory = :idCategory " +
            "GROUP BY v " +
            "ORDER BY COUNT(h) ASC")
    Page<Video> findAllOrderByLikeCountAsc(
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN CommentVideo c ON v.idVideo = c.video.idVideo " +
            "LEFT JOIN CommentInComment c2 ON c.idCommentVideo = c2.commentVideo.idCommentVideo " +
            "WHERE :idCategory = 0 OR v.category.idCategory = :idCategory " +
            "GROUP BY v " +
            "ORDER BY (COUNT(c.idCommentVideo) + COUNT(c2.idCommentInComment)) DESC")
    Page<Video> findAllOrderByTotalCommentCountDesc(
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN CommentVideo c ON v.idVideo = c.video.idVideo " +
            "LEFT JOIN CommentInComment c2 ON c.idCommentVideo = c2.commentVideo.idCommentVideo " +
            "WHERE :idCategory = 0 OR v.category.idCategory = :idCategory " +
            "GROUP BY v " +
            "ORDER BY (COUNT(c.idCommentVideo) + COUNT(c2.idCommentInComment)) ASC")
    Page<Video> findAllOrderByTotalCommentCountAsc(
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Video> findByVideoTitleContainingIgnoreCase(
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT v FROM Video v WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) AND v.category.idCategory = :idCategory")
    Page<Video> findByVideoTitleContainingIgnoreCaseAndCategory_IdCategory(
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN HistoryLikeVideo h ON v.idVideo = h.video.idVideo " +
            "WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY COUNT(h) DESC")
    Page<Video> findAllOrderByLikeCountDescAndTitleContainingIgnoreCase(
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN HistoryLikeVideo h ON v.idVideo = h.video.idVideo " +
            "WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY COUNT(h) ASC")
    Page<Video> findAllOrderByLikeCountAscAndTitleContainingIgnoreCase(
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN CommentVideo c ON v.idVideo = c.video.idVideo " +
            "LEFT JOIN CommentInComment c2 ON c.idCommentVideo = c2.commentVideo.idCommentVideo " +
            "WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY (COUNT(c.idCommentVideo) + COUNT(c2.idCommentInComment)) DESC")
    Page<Video> findAllOrderByTotalCommentCountDescAndTitleContainingIgnoreCase(
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN CommentVideo c ON v.idVideo = c.video.idVideo " +
            "LEFT JOIN CommentInComment c2 ON c.idCommentVideo = c2.commentVideo.idCommentVideo " +
            "WHERE LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY (COUNT(c.idCommentVideo) + COUNT(c2.idCommentInComment)) ASC")
    Page<Video> findAllOrderByTotalCommentCountAscAndTitleContainingIgnoreCase(
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    Page<Video> findAllByChannel_NameUnique(String nameUniqueChannel, Pageable pageable);

    Page<Video> findAllByChannel_NameUniqueAndCategory_IdCategory(String nameUniqueChannel, Pageable pageable, Integer idCategory);

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN HistoryLikeVideo h ON v.idVideo = h.video.idVideo " +
            "WHERE v.channel.nameUnique = :nameUniqueChannel " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY COUNT(h) DESC")
    Page<Video> findAllByChannelOrderByLikeCountDesc(
            @Param("nameUniqueChannel") String nameUniqueChannel,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN HistoryLikeVideo h ON v.idVideo = h.video.idVideo " +
            "WHERE v.channel.nameUnique = :nameUniqueChannel " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY COUNT(h) ASC")
    Page<Video> findAllByChannelOrderByLikeCountAsc(
            @Param("nameUniqueChannel") String nameUniqueChannel,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN CommentVideo c ON v.idVideo = c.video.idVideo " +
            "LEFT JOIN CommentInComment c2 ON c.idCommentVideo = c2.commentVideo.idCommentVideo " +
            "WHERE v.channel.nameUnique = :nameUniqueChannel " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY (COUNT(c.idCommentVideo) + COUNT(c2.idCommentInComment)) DESC")
    Page<Video> findAllByChannelOrderByTotalCommentCountDesc(
            @Param("nameUniqueChannel") String nameUniqueChannel,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN CommentVideo c ON v.idVideo = c.video.idVideo " +
            "LEFT JOIN CommentInComment c2 ON c.idCommentVideo = c2.commentVideo.idCommentVideo " +
            "WHERE v.channel.nameUnique = :nameUniqueChannel " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY (COUNT(c.idCommentVideo) + COUNT(c2.idCommentInComment)) ASC")
    Page<Video> findAllByChannelOrderByTotalCommentCountAsc(
            @Param("nameUniqueChannel") String nameUniqueChannel,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v WHERE v.channel.nameUnique = :nameUnique AND LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Video> findByNameUniqueAndVideoTitleContainingIgnoreCase(
            @Param("nameUnique") String nameUnique,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT v FROM Video v WHERE v.channel.nameUnique = :nameUnique AND LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) AND v.category.idCategory = :idCategory")
    Page<Video> findByNameUniqueAndVideoTitleContainingIgnoreCaseAndCategory_IdCategory(
            @Param("nameUnique") String nameUnique,
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN HistoryLikeVideo h ON v.idVideo = h.video.idVideo " +
            "WHERE v.channel.nameUnique = :nameUniqueChannel " +
            "AND LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY COUNT(h) DESC")
    Page<Video> findAllByChannelOrderByLikeCountDescAndTitleContainingIgnoreCase(
            @Param("nameUniqueChannel") String nameUniqueChannel,
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN HistoryLikeVideo h ON v.idVideo = h.video.idVideo " +
            "WHERE v.channel.nameUnique = :nameUniqueChannel " +
            "AND LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY COUNT(h) ASC")
    Page<Video> findAllByChannelOrderByLikeCountAscAndTitleContainingIgnoreCase(
            @Param("nameUniqueChannel") String nameUniqueChannel,
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN CommentVideo c ON v.idVideo = c.video.idVideo " +
            "LEFT JOIN CommentInComment c2 ON c.idCommentVideo = c2.commentVideo.idCommentVideo " +
            "WHERE v.channel.nameUnique = :nameUniqueChannel " +
            "AND LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY (COUNT(c.idCommentVideo) + COUNT(c2.idCommentInComment)) DESC")
    Page<Video> findAllByChannelOrderByTotalCommentCountDescAndTitleContainingIgnoreCase(
            @Param("nameUniqueChannel") String nameUniqueChannel,
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

    @Query("SELECT v FROM Video v " +
            "LEFT JOIN CommentVideo c ON v.idVideo = c.video.idVideo " +
            "LEFT JOIN CommentInComment c2 ON c.idCommentVideo = c2.commentVideo.idCommentVideo " +
            "WHERE v.channel.nameUnique = :nameUniqueChannel " +
            "AND LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "AND (:idCategory = 0 OR v.category.idCategory = :idCategory) " +
            "GROUP BY v " +
            "ORDER BY (COUNT(c.idCommentVideo) + COUNT(c2.idCommentInComment)) ASC")
    Page<Video> findAllByChannelOrderByTotalCommentCountAscAndTitleContainingIgnoreCase(
            @Param("nameUniqueChannel") String nameUniqueChannel,
            @Param("keyword") String keyword,
            Pageable pageable,
            @Param("idCategory") Integer idCategory
    );

}
