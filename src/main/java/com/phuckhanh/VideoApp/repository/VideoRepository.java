package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistoryWatchVideo;
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

    Page<Video> findAllByOrderByDateTimeCreateDesc(Pageable pageable);

    Page<Video> findAllByChannel_NameUniqueOrderByDateTimeCreateDesc(String nameUniqueChannel, Pageable pageable);

    Page<Video> findAllByCategory_IdCategoryOrderByDateTimeCreateDesc(Integer idCategory, Pageable pageable);

    @Query("SELECT v FROM Video v WHERE v.channel.nameUnique = :nameUnique AND LOWER(v.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Video> findByNameUniqueAndVideoTitleContainingIgnoreCase(@Param("nameUnique") String nameUnique, @Param("keyword") String keyword, Pageable pageable);
}
