package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.HistoryLikeVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.request.HistoryWatchVideoCreationRequest;
import com.phuckhanh.VideoApp.dto.request.VideoCreationRequest;
import com.phuckhanh.VideoApp.dto.response.VideoResponse;
import com.phuckhanh.VideoApp.entity.*;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.VideoMapper;
import com.phuckhanh.VideoApp.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
    HistoryWatchVideoRepository historyWatchVideoRepository;

    public void downloadVideo(Integer idVideo, HttpServletResponse response) throws IOException {
        Video video = videoRepository.findById(idVideo).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND));
        String videoUrl = video.getLinkVideo();

        URL url = new URL(videoUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Get the media type of the file
        String contentType = connection.getContentType();
        if (contentType == null) {
            // Use the default media type
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        response.setContentType(contentType);

        // Set Content-Disposition header
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename("video.mp4", StandardCharsets.UTF_8)
                .build()
                .toString());

        // Copy data from the URL connection to the response output stream
        try (InputStream inputStream = connection.getInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new IOException("Failed to download video", e);
        }
    }

    public long countChannelLikeVideo(Integer idVideo) {
        return historyLikeVideoRepository.countChannelLikeVideo(idVideo);
    }

    public boolean isChannelLikeVideo(Integer idChannel, Integer idVideo) {
        return historyLikeVideoRepository.isChannelLikeVideo(idChannel, idVideo);
    }

    public List<VideoResponse> getAllVideoChannelWatched(Integer idChannel) {
        return historyWatchVideoRepository.findAllByChannel_IdChannelOrderByDateTimeWatchDesc(idChannel).stream()
                .map(historyWatchVideo -> videoMapper.toVideoResponse(historyWatchVideo.getVideo()))
                .toList();
    }

    public List<VideoResponse> getAllVideoChannelLiked(Integer idChannel) {
        return historyLikeVideoRepository.findAllByChannel_IdChannelOrderByDateTimeLikeDesc(idChannel).stream()
                .map(historyLikeVideo -> videoMapper.toVideoResponse(historyLikeVideo.getVideo()))
                .toList();
    }

    public VideoResponse getById(Integer id) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND));

        return videoMapper.toVideoResponse(video);
    }

    public List<VideoResponse> getAllByChannelNameUnique(String nameUniqueChannel) {
        return videoRepository.findAllByChannel_NameUniqueOrderByDateTimeCreateDesc(nameUniqueChannel).stream()
                .map(videoMapper::toVideoResponse)
                .toList();
    }

    public long countAllByChannelNameUnique(String nameUniqueChannel) {
        return videoRepository.countByChannelNameUnique(nameUniqueChannel);
    }

    public List<VideoResponse> getAllVideo() {
        return videoRepository.findAllByOrderByDateTimeCreateDesc().stream()
                .map(videoMapper::toVideoResponse)
                .toList();
    }

    public void createHistoryLikeVideo(HistoryLikeVideoCreationRequest request) {
        HistoryLikeVideo historyLikeVideo = new HistoryLikeVideo();

        historyLikeVideo.setChannel(channelRepository.findById(request.getIdChannel()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND)));
        historyLikeVideo.setVideo(videoRepository.findById(request.getIdVideo()).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND)));
        historyLikeVideo.setDateTimeLike(LocalDateTime.now());
        historyLikeVideo.setIdHistoryLikeVideoKey(new HistoryLikeVideoKey(request.getIdChannel(), request.getIdVideo()));

        historyLikeVideoRepository.save(historyLikeVideo);
    }

    public void createHistoryWatchVideo(HistoryWatchVideoCreationRequest request) {
        HistoryWatchVideo historyWatchVideo = new HistoryWatchVideo();

        historyWatchVideo.setChannel(channelRepository.findById(request.getIdChannel()).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND)));
        historyWatchVideo.setVideo(videoRepository.findById(request.getIdVideo()).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND)));
        historyWatchVideo.setDateTimeWatch(LocalDateTime.now());
        historyWatchVideo.setIdHistoryWatchVideoKey(new HistoryWatchVideoKey(request.getIdChannel(), request.getIdVideo()));

        historyWatchVideoRepository.save(historyWatchVideo);
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
        video.setView(0);
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

        List<HistoryLikeVideo> historyLikeVideos = historyLikeVideoRepository.findAllByVideo_IdVideo(idVideo);
        for (HistoryLikeVideo historyLikeVideo : historyLikeVideos) {
            historyLikeVideoRepository.delete(historyLikeVideo);
        }

        List<HistoryWatchVideo> historyWatchVideos = historyWatchVideoRepository.findAllByVideo_IdVideo(idVideo);
        for (HistoryWatchVideo historyWatchVideo : historyWatchVideos) {
            historyWatchVideoRepository.delete(historyWatchVideo);
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

    public void deleteAllHistoryWatchVideosByChannel(Integer idChannel) {
        List<HistoryWatchVideo> historyWatchVideos = historyWatchVideoRepository.findAllByChannel_IdChannel(idChannel);

        if (historyWatchVideos.isEmpty()) {
            throw new AppException(ErrorCode.HISTORY_WATCH_VIDEO_NOT_FOUND);
        }

        historyWatchVideoRepository.deleteAll(historyWatchVideos);
    }
}
