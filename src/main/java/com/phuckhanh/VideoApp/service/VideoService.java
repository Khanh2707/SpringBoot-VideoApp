package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.CategoryCreationRequest;
import com.phuckhanh.VideoApp.dto.request.VideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.CategoryResponse;
import com.phuckhanh.VideoApp.dto.response.VideoResponse;
import com.phuckhanh.VideoApp.entity.Category;
import com.phuckhanh.VideoApp.entity.Channel;
import com.phuckhanh.VideoApp.entity.Video;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.VideoMapper;
import com.phuckhanh.VideoApp.repository.CategoryRepository;
import com.phuckhanh.VideoApp.repository.ChannelRepository;
import com.phuckhanh.VideoApp.repository.VideoRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VideoService {
    VideoRepository videoRepository;
    VideoMapper videoMapper;
    CloudinaryService cloudinaryService;
    CategoryRepository categoryRepository;
    ChannelRepository channelRepository;

    public VideoResponse createVideo(VideoCreationRequest request) throws IOException {
        Video video = videoMapper.toVideo(request);

        String urlVideo = (String) cloudinaryService
                .uploadFile(request.getFileVideo(), "video", "")
                .get("secure_url");

        String urlImagePreview = (String) cloudinaryService
                .uploadFile(request.getFileImagePreview(), "image preview", "")
                .get("secure_url");

        video.setTitle(request.getTitle());
        video.setLinkVideo(urlVideo);
        if (!request.getDescription().isEmpty())
            video.setDescription(request.getDescription());
        if (!request.getFileImagePreview().isEmpty())
            video.setImagePreview(urlImagePreview);
        video.setDateTimeCreate(LocalDateTime.now());
        video.setHide(request.getHide());
        video.setBan(false);

        Category category = categoryRepository.findById(request.getIdCategory()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));
        video.setCategory(category);

        Channel channel = channelRepository.findById(request.getIdChannel()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));
        video.setChannel(channel);

        videoRepository.save(video);

        return videoMapper.toVideoResponse(video);
    }

    public void deleteVideo(Integer idVideo) throws IOException {
        Video video = videoRepository.findById(idVideo).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND));

        cloudinaryService.destroyFile("video", video.getLinkVideo(), "video");
        cloudinaryService.destroyFile("image preview", video.getImagePreview(), "image");

        videoRepository.deleteById(idVideo);
    }
}
