package com.phuckhanh.VideoApp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class TypeReportVideo {
    @Id
    Integer idTypeReportVideo;
    String description;

    @OneToMany(mappedBy = "typeReportVideo")
    List<ReportVideo> reportVideos;
}
