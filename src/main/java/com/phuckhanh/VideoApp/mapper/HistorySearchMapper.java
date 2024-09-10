package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.HistorySearchCreationRequest;
import com.phuckhanh.VideoApp.dto.response.HistorySearchResponse;
import com.phuckhanh.VideoApp.entity.HistorySearch;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistorySearchMapper {
    HistorySearch toHistorySearch(HistorySearchCreationRequest request);

    HistorySearchResponse toHistorySearchResponse(HistorySearch historySearch);
}
