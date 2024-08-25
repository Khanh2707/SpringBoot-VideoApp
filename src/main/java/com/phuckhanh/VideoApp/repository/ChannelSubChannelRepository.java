package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.Channel;
import com.phuckhanh.VideoApp.entity.ChannelSubChannel;
import com.phuckhanh.VideoApp.entity.ChannelSubChannelKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelSubChannelRepository extends JpaRepository<ChannelSubChannel, ChannelSubChannelKey> {
    @Query("SELECT COUNT(c) FROM ChannelSubChannel c WHERE c.channel2.idChannel = :idChannel2")
    long countUser1SubForUser2(Integer idChannel2);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM ChannelSubChannel c WHERE c.channel1.idChannel = :idChannel1 AND c.channel2.idChannel = :idChannel2")
    boolean isUser1SubscribingUser2(Integer idChannel1, Integer idChannel2);

    List<ChannelSubChannel> findByChannel2_IdChannel(Integer idChannel2);
}
