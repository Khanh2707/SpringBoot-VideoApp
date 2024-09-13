package com.phuckhanh.VideoApp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportVideoResponse {
    Integer idReportVideo;
    LocalDateTime dateTimeReport;
    TypeReportVideoResponse typeReportVideo;
    ChannelResponse channel;
    VideoResponse video;
}
