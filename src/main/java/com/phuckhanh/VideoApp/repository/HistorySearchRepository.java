package com.phuckhanh.VideoApp.repository;

import com.phuckhanh.VideoApp.entity.HistorySearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorySearchRepository extends JpaRepository<HistorySearch, Integer> {
    Page<HistorySearch> findAllByChannel_IdChannelOrderByDateTimeSearchDesc(Integer idChannel, Pageable pageable);
}
