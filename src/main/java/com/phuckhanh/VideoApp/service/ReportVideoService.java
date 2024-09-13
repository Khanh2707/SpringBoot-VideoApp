package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.ReportVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.ReportVideoResponse;
import com.phuckhanh.VideoApp.entity.Channel;
import com.phuckhanh.VideoApp.entity.ReportVideo;
import com.phuckhanh.VideoApp.entity.TypeReportVideo;
import com.phuckhanh.VideoApp.entity.Video;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.ReportVideoMapper;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ReportVideoService {
    ReportVideoRepository reportVideoRepository;
    ReportVideoMapper reportVideoMapper;
    TypeReportVideoRepository typeReportVideoRepository;
    ChannelRepository channelRepository;
    VideoRepository videoRepository;

    public Page<ReportVideoResponse> getAllReportVideos(String propertySort, String optionSort, Integer page, Integer size, Integer idTypeReportVideo) {
        Pageable pageable = PageRequest.of(page, size);

        Sort sort = Sort.by(Sort.Direction.fromString(optionSort), propertySort);
        pageable = PageRequest.of(page, size, sort);

        if (idTypeReportVideo == 0) {
            return reportVideoRepository.findAll(pageable)
                    .map(reportVideoMapper::toReportVideoResponse);
        } else {
            return reportVideoRepository.findAllByTypeReportVideo_IdTypeReportVideo(idTypeReportVideo, pageable)
                    .map(reportVideoMapper::toReportVideoResponse);
        }
    }

    public ReportVideoResponse createReportVideo(ReportVideoCreationRequest request) {
        ReportVideo reportVideo = new ReportVideo();

        TypeReportVideo typeReportVideo = typeReportVideoRepository.findById(request.getIdTypeReportVideo()).orElseThrow(() -> new AppException(ErrorCode.TYPE_REPORT_VIDEO_NOT_FOUND));
        Channel channel = channelRepository.findById(request.getIdChannel()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));
        Video video = videoRepository.findById(request.getIdVideo()).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND));

        reportVideo.setDateTimeReport(LocalDateTime.now());
        reportVideo.setTypeReportVideo(typeReportVideo);
        reportVideo.setChannel(channel);
        reportVideo.setVideo(video);

        return reportVideoMapper.toReportVideoResponse(reportVideoRepository.save(reportVideo));
    }
}
