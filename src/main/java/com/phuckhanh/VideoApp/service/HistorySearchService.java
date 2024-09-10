package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.HistorySearchCreationRequest;
import com.phuckhanh.VideoApp.dto.response.HistorySearchResponse;
import com.phuckhanh.VideoApp.entity.Channel;
import com.phuckhanh.VideoApp.entity.HistorySearch;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.HistorySearchMapper;
import com.phuckhanh.VideoApp.repository.ChannelRepository;
import com.phuckhanh.VideoApp.repository.HistorySearchRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HistorySearchService {
    HistorySearchMapper historySearchMapper;
    ChannelRepository channelRepository;
    HistorySearchRepository historySearchRepository;

    public Page<HistorySearchResponse> getAllHistorySearchByChannel(Integer idChannel, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return historySearchRepository.findAllByChannel_IdChannelOrderByDateTimeSearchDesc(idChannel, pageable)
                .map(historySearchMapper::toHistorySearchResponse);
    }

    public HistorySearchResponse createHistorySearch(HistorySearchCreationRequest request) {
        HistorySearch historySearch = historySearchMapper.toHistorySearch(request);

        historySearch.setDateTimeSearch(LocalDateTime.now());

        Channel channel = channelRepository.findById(request.getIdChannel()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));
        historySearch.setChannel(channel);

        return historySearchMapper.toHistorySearchResponse(historySearchRepository.save(historySearch));
    }

    public void deleteHistorySearch(Integer idHistorySearch) {
        historySearchRepository.deleteById(idHistorySearch);
    }
}
