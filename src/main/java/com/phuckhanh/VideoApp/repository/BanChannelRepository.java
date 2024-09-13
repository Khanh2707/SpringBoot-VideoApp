package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.BanChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BanChannelRepository extends JpaRepository<BanChannel, Integer> {
    Optional<BanChannel> findByChannel_IdChannel(Integer idChannel);
}
