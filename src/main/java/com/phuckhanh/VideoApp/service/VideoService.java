package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.request.*;
import com.phuckhanh.VideoApp.dto.response.HistoryNotificationVideoResponse;
import com.phuckhanh.VideoApp.dto.response.VideoResponse;
import com.phuckhanh.VideoApp.entity.*;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.HistoryNotificationVideoMapper;
import com.phuckhanh.VideoApp.mapper.VideoMapper;
import com.phuckhanh.VideoApp.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.Optional;

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
    CheckHistoryNotificationVideoRepository checkHistoryNotificationVideoRepository;
    HistoryNotificationVideoMapper historyNotificationVideoMapper;

    public void downloadVideo(Integer idVideo, HttpServletResponse response) throws IOException {
        Video video = videoRepository.findById(idVideo).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND));
        String videoUrl = video.getLinkVideo();

        URL url = new URL(videoUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        String contentType = connection.getContentType();
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        response.setContentType(contentType);

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename("video.mp4", StandardCharsets.UTF_8)
                .build()
                .toString());

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

    public long countVideoIsBanByChannel(Integer idChannel) {
        return videoRepository.countVideoIsBanByChannel(idChannel);
    }

    public long countHistoryNotificationVideoFromTimeToTime(Integer idChannel) {
        CheckHistoryNotificationVideo checkHistoryNotificationVideo = checkHistoryNotificationVideoRepository.findByChannel_IdChannel(idChannel).orElse(null);

        if (checkHistoryNotificationVideo == null) {
            Channel channel = channelRepository.findById(idChannel)
                    .orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));

            checkHistoryNotificationVideo = new CheckHistoryNotificationVideo();
            checkHistoryNotificationVideo.setDateTimeCheck(LocalDateTime.now());
            checkHistoryNotificationVideo.setChannel(channel);

            checkHistoryNotificationVideoRepository.save(checkHistoryNotificationVideo);

            Pageable pageable = PageRequest.of(0, 1);

            return historyNotificationVideoRepository.findAllByChannel_IdChannelOrderByNotificationVideoDesc(idChannel, pageable).getTotalElements();
        } else {
            LocalDateTime dateTimeCheck = checkHistoryNotificationVideo.getDateTimeCheck();
            LocalDateTime now = LocalDateTime.now();

            List<HistoryNotificationVideo> historyNotificationVideos = historyNotificationVideoRepository.findAllByChannelIdAndTimeBetween(
                    idChannel, dateTimeCheck, now
            );

            return historyNotificationVideos.size();
        }
    }

    public long countChannelLikeVideo(Integer idVideo) {
        return historyLikeVideoRepository.countChannelLikeVideo(idVideo);
    }

    public long countAllByChannelNameUnique(String nameUniqueChannel) {
        return videoRepository.countByChannelNameUnique(nameUniqueChannel);
    }

    public boolean isChannelLikeVideo(Integer idChannel, Integer idVideo) {
        return historyLikeVideoRepository.isChannelLikeVideo(idChannel, idVideo);
    }

    public VideoResponse getById(Integer id) {
        Video video = videoRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND));

        return videoMapper.toVideoResponse(video);
    }

    public Page<HistoryNotificationVideoResponse> getAllNotificationCreateVideo(Integer idChannel, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return historyNotificationVideoRepository.findAllByChannel_IdChannelOrderByNotificationVideoDesc(idChannel, pageable)
                .map(historyNotificationVideoMapper::toHistoryNotificationVideoResponse);
    }

    public Page<VideoResponse> searchWatchedVideosByChannelAndTitle(Integer idChannel, String keyword, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return historyWatchVideoRepository.findByChannelIdAndVideoTitleContainingIgnoreCase(idChannel, keyword, pageable)
                .map(historyWatchVideo -> videoMapper.toVideoResponse(historyWatchVideo.getVideo()));
    }

    public Page<VideoResponse> getAllVideoChannelWatched(Integer idChannel, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return historyWatchVideoRepository.findAllByChannel_IdChannelOrderByDateTimeWatchDesc(idChannel, pageable)
                .map(historyWatchVideo -> videoMapper.toVideoResponse(historyWatchVideo.getVideo()));
    }

    public Page<VideoResponse> searchLikedVideosByChannelAndTitle(Integer idChannel, String keyword, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return historyLikeVideoRepository.findByChannelIdAndVideoTitleContainingIgnoreCase(idChannel, keyword, pageable)
                .map(historyLikeVideo -> videoMapper.toVideoResponse(historyLikeVideo.getVideo()));
    }

    public Page<VideoResponse> getAllVideoChannelLiked(Integer idChannel, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return historyLikeVideoRepository.findAllByChannel_IdChannelOrderByDateTimeLikeDesc(idChannel, pageable)
                .map(historyLikeVideo -> videoMapper.toVideoResponse(historyLikeVideo.getVideo()));
    }

    public Page<VideoResponse> searchVideosByChannelAndTitle(String nameUniqueChannel, String keyword, String propertySort, String optionSort, Integer page, Integer size, Integer idCategory) {
        Pageable pageable = PageRequest.of(page, size);

        if ("amountLike".equals(propertySort)) {
            if ("asc".equalsIgnoreCase(optionSort)) {
                return videoRepository.findAllByChannelOrderByLikeCountAscAndTitleContainingIgnoreCase(nameUniqueChannel, keyword, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findAllByChannelOrderByLikeCountDescAndTitleContainingIgnoreCase(nameUniqueChannel, keyword, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            }
        } else if ("amountComment".equals(propertySort)) {
            if ("asc".equalsIgnoreCase(optionSort)) {
                return videoRepository.findAllByChannelOrderByTotalCommentCountAscAndTitleContainingIgnoreCase(nameUniqueChannel, keyword, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findAllByChannelOrderByTotalCommentCountDescAndTitleContainingIgnoreCase(nameUniqueChannel, keyword, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            }
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(optionSort), propertySort);
            pageable = PageRequest.of(page, size, sort);
            if (idCategory == 0) {
                return videoRepository.findByNameUniqueAndVideoTitleContainingIgnoreCase(nameUniqueChannel, keyword, pageable)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findByNameUniqueAndVideoTitleContainingIgnoreCaseAndCategory_IdCategory(nameUniqueChannel, keyword, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            }
        }
    }

    public Page<VideoResponse> getAllByChannelNameUnique(String nameUniqueChannel, String propertySort, String optionSort, Integer page, Integer size, Integer idCategory) {
        Pageable pageable = PageRequest.of(page, size);

        if ("amountLike".equals(propertySort)) {
            if ("asc".equalsIgnoreCase(optionSort)) {
                return videoRepository.findAllByChannelOrderByLikeCountAsc(nameUniqueChannel, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findAllByChannelOrderByLikeCountDesc(nameUniqueChannel, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            }
        } else if ("amountComment".equals(propertySort)) {
            if ("asc".equalsIgnoreCase(optionSort)) {
                return videoRepository.findAllByChannelOrderByTotalCommentCountAsc(nameUniqueChannel, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findAllByChannelOrderByTotalCommentCountDesc(nameUniqueChannel, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            }
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(optionSort), propertySort);
            pageable = PageRequest.of(page, size, sort);

            if (idCategory == 0) {
                return videoRepository.findAllByChannel_NameUnique(nameUniqueChannel, pageable)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findAllByChannel_NameUniqueAndCategory_IdCategory(nameUniqueChannel, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            }
        }
    }

    public Page<VideoResponse> searchVideosByTitle(String keyword, String propertySort, String optionSort, Integer page, Integer size, Integer idCategory) {
        Pageable pageable = PageRequest.of(page, size);

        if ("amountLike".equals(propertySort)) {
            if ("asc".equalsIgnoreCase(optionSort)) {
                return videoRepository.findAllOrderByLikeCountAscAndTitleContainingIgnoreCase(keyword, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findAllOrderByLikeCountDescAndTitleContainingIgnoreCase(keyword, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            }
        } else if ("amountComment".equals(propertySort)) {
            if ("asc".equalsIgnoreCase(optionSort)) {
                return videoRepository.findAllOrderByTotalCommentCountAscAndTitleContainingIgnoreCase(keyword, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findAllOrderByTotalCommentCountDescAndTitleContainingIgnoreCase(keyword, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            }
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(optionSort), propertySort);
            pageable = PageRequest.of(page, size, sort);
            if (idCategory == 0) {
                return videoRepository.findByVideoTitleContainingIgnoreCase(keyword, pageable)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findByVideoTitleContainingIgnoreCaseAndCategory_IdCategory(keyword, pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            }
        }
    }

    public Page<VideoResponse> getAllVideo(String propertySort, String optionSort, Integer page, Integer size, Integer idCategory) {
        Pageable pageable = PageRequest.of(page, size);

        if ("amountLike".equals(propertySort)) {
            if ("asc".equalsIgnoreCase(optionSort)) {
                return videoRepository.findAllOrderByLikeCountAsc(pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findAllOrderByLikeCountDesc(pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            }
        } else if ("amountComment".equals(propertySort)) {
            if ("asc".equalsIgnoreCase(optionSort)) {
                return videoRepository.findAllOrderByTotalCommentCountAsc(pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findAllOrderByTotalCommentCountDesc(pageable, idCategory)
                        .map(videoMapper::toVideoResponse);
            }
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(optionSort), propertySort);
            pageable = PageRequest.of(page, size, sort);

            if (idCategory == 0) {
                return videoRepository.findAll(pageable)
                        .map(videoMapper::toVideoResponse);
            } else {
                return videoRepository.findAllByCategory_IdCategory(idCategory, pageable)
                        .map(videoMapper::toVideoResponse);
            }
        }
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

    public VideoResponse updateVideo(Integer idVideo, VideoUpdateRequest request) {
        Video video = videoRepository.findById(idVideo).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND));

        videoMapper.updateVideo(video, request);

        return videoMapper.toVideoResponse(videoRepository.save(video));
    }

    public void updateCheckHistoryNotificationVideo(Integer idChannel) {
        Optional<CheckHistoryNotificationVideo> optionalCheckHistory = checkHistoryNotificationVideoRepository.findByChannel_IdChannel(idChannel);

        CheckHistoryNotificationVideo checkHistoryNotificationVideo;

        if (optionalCheckHistory.isPresent()) {
            checkHistoryNotificationVideo = optionalCheckHistory.get();
        } else {
            checkHistoryNotificationVideo = new CheckHistoryNotificationVideo();
            Channel channel = channelRepository.findById(idChannel).orElseThrow(() -> new AppException(ErrorCode.CHANNEL_NOT_FOUND));
            checkHistoryNotificationVideo.setChannel(channel);
        }

        checkHistoryNotificationVideo.setDateTimeCheck(LocalDateTime.now());

        checkHistoryNotificationVideoRepository.save(checkHistoryNotificationVideo);
    }

    public VideoResponse updateViewVideo(Integer idVideo) {
        Video video = videoRepository.findById(idVideo).orElseThrow(() -> new AppException(ErrorCode.VIDEO_NOT_FOUND));

        video.setView(video.getView() + 1);

        videoRepository.save(video);

        return videoMapper.toVideoResponse(video);
    }

    public HistoryNotificationVideoResponse updateIsCheckHistoryNotificationVideo(Integer idChannel, Integer idNotificationVideo, HistoryNotificationVideoUpdateRequest request) {
        HistoryNotificationVideoKey historyNotificationVideoKey = new HistoryNotificationVideoKey(idChannel, idNotificationVideo);
        HistoryNotificationVideo historyNotificationVideo = historyNotificationVideoRepository.findByIdHistoryNotificationVideoKey(historyNotificationVideoKey).orElseThrow(() -> new AppException(ErrorCode.HISTORY_NOTIFICATION_VIDEO_NOT_FOUND));

        historyNotificationVideoMapper.updateHistoryNotificationVideoIsCheck(historyNotificationVideo, request);

        return historyNotificationVideoMapper.toHistoryNotificationVideoResponse(historyNotificationVideoRepository.save(historyNotificationVideo));
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
