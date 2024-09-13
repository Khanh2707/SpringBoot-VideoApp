package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.TypeReportVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.TypeReportVideoResponse;
import com.phuckhanh.VideoApp.entity.TypeReportVideo;
import com.phuckhanh.VideoApp.mapper.ReportVideoMapper;
import com.phuckhanh.VideoApp.mapper.TypeReportVideoMapper;
import com.phuckhanh.VideoApp.repository.ChannelRepository;
import com.phuckhanh.VideoApp.repository.ReportVideoRepository;
import com.phuckhanh.VideoApp.repository.TypeReportVideoRepository;
import com.phuckhanh.VideoApp.repository.VideoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TypeReportVideoService {
    TypeReportVideoRepository typeReportVideoRepository;
    TypeReportVideoMapper typeReportVideoMapper;

    public Page<TypeReportVideoResponse> getAllTypeReportVideos(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        return typeReportVideoRepository.findAll(pageable)
                .map(typeReportVideoMapper::toTypeReportVideoResponse);
    }

    public TypeReportVideoResponse createTypeReportVideo(TypeReportVideoCreationRequest request) {
        TypeReportVideo typeReportVideo = typeReportVideoMapper.toTypeReportVideo(request);

        return typeReportVideoMapper.toTypeReportVideoResponse(typeReportVideoRepository.save(typeReportVideo));
    }
}
