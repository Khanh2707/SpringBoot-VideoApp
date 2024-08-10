package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {
    boolean existsByNameUnique(String channel);
}
