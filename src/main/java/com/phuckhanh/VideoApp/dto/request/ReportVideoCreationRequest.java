package com.phuckhanh.VideoApp.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportVideoCreationRequest {
    Integer idTypeReportVideo;
    Integer idChannel;
    Integer idVideo;
}
