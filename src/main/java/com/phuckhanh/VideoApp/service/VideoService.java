package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.HistoryLikeVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.request.VideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.ChannelResponse;
import com.phuckhanh.VideoApp.dto.response.VideoResponse;
import com.phuckhanh.VideoApp.entity.*;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.VideoMapper;
import com.phuckhanh.VideoApp.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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
    NotificationVideoRepository notificationVideoRepository;
    HistoryNotificationVideoRepository historyNotificationVideoRepository;
    ChannelSubChannelRepository channelSubChannelRepository;
    HistoryLikeVideoRepository historyLikeVideoRepository;

    public long countChannelLikeVideo(Integer idVideo) {
        return historyLikeVideoRepository.countChannelLikeVideo(idVideo);
    }

    public boolean isChannelLikeVideo(Integer idChannel, Integer idVideo) {
        return historyLikeVideoRepository.isChannelLikeVideo(idChannel, idVideo);
    }

    public void createHistoryLikeVideo(HistoryLikeVideoCreationRequest request) {
        HistoryLikeVideo historyLikeVideo = new HistoryLikeVideo();

        historyLikeVideo.setChannel(channelRepository.findById(request.getIdChannel()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND)));
        historyLikeVideo.setVideo(videoRepository.findById(request.getIdVideo()).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND)));
        historyLikeVideo.setIdHistoryLikeVideoKey(new HistoryLikeVideoKey(request.getIdChannel(), request.getIdVideo()));

        historyLikeVideoRepository.save(historyLikeVideo);
    }

    public VideoResponse getById(Integer id) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND));

        return videoMapper.toVideoResponse(video);
    }

    public List<VideoResponse> getAllByChannelNameUnique(String nameUniqueChannel) {
        return videoRepository.findAllByChannel_NameUnique(nameUniqueChannel).stream()
                .map(videoMapper::toVideoResponse)
                .toList();
    }

    public long countAllByChannelNameUnique(String nameUniqueChannel) {
        return videoRepository.countByChannelNameUnique(nameUniqueChannel);
    }

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
        video.setImagePreview(urlImagePreview);
        video.setDateTimeCreate(LocalDateTime.now());
        video.setHide(request.getHide());
        video.setBan(false);
        if (!request.getDescription().isEmpty())
            video.setDescription(request.getDescription());

        Category category = categoryRepository.findById(request.getIdCategory()).orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOTFOUND));
        video.setCategory(category);

        Channel channel = channelRepository.findById(request.getIdChannel()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));
        video.setChannel(channel);

        videoRepository.save(video);

        NotificationVideo notificationVideo = new NotificationVideo();
        notificationVideo.setVideo(video);
        notificationVideoRepository.save(notificationVideo);

        channelSubChannelRepository.findByChannel2_IdChannel(channel.getIdChannel()).forEach(
                channelSubChannel -> {
                    HistoryNotificationVideo historyNotificationVideo = new HistoryNotificationVideo();
                    historyNotificationVideo.setIdHistoryNotificationVideoKey(new HistoryNotificationVideoKey(channelSubChannel.getChannel1().getIdChannel(), notificationVideo.getIdNotificationVideo()));
                    historyNotificationVideo.setChannel(channelSubChannel.getChannel1());
                    historyNotificationVideo.setNotificationVideo(notificationVideo);
                    historyNotificationVideo.setIsCheck(false);
                    historyNotificationVideoRepository.save(historyNotificationVideo);
                });

        return videoMapper.toVideoResponse(video);
    }

    public void deleteVideo(Integer idVideo) throws IOException {
        Video video = videoRepository.findById(idVideo).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND));

        for (HistoryNotificationVideo historyNotificationVideo : video.getNotificationVideo().getHistoryNotificationVideos()) {
            historyNotificationVideoRepository.delete(historyNotificationVideo);
        }

        cloudinaryService.destroyFile("video", video.getLinkVideo(), "video");
        cloudinaryService.destroyFile("image preview", video.getImagePreview(), "image");

        videoRepository.deleteById(idVideo);
    }

    public void deleteHistoryLikeVideo(Integer idChannel, Integer idVideo) {
        HistoryLikeVideo historyLikeVideo = historyLikeVideoRepository.findById(new HistoryLikeVideoKey(idChannel, idVideo))
                .orElseThrow(() -> new AppException(ErrorCode.HISTORY_LIKE_VIDEO_NOT_FOUND));

        historyLikeVideoRepository.delete(historyLikeVideo);
    }
}
