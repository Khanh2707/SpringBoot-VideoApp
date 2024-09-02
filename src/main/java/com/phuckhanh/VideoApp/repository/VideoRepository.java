package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findAllByOrderByDateTimeCreateDesc();

    @Query("SELECT COUNT(v) FROM Video v WHERE v.channel.nameUnique = :nameUniqueChannel")
    long countByChannelNameUnique(@Param("nameUniqueChannel") String nameUniqueChannel);

    List<Video> findAllByChannel_NameUniqueOrderByDateTimeCreateDesc(String nameUniqueChannel);

    List<Video> findAllByCategory_IdCategoryOrderByDateTimeCreateDesc(Integer idCategory);
}
