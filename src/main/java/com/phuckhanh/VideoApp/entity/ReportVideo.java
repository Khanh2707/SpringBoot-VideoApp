package com.phuckhanh.VideoApp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ReportVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idReportVideo;
    LocalDateTime dateTimeReport;

    @ManyToOne
    @JoinColumn(name = "id_type_report_video_report_video")
    TypeReportVideo typeReportVideo;

    @ManyToOne
    @JoinColumn(name = "id_channel_report_video")
    Channel channel;

    @ManyToOne
    @JoinColumn(name = "id_video_report_video")
    Video video;
}
