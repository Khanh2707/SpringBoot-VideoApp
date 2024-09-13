package com.phuckhanh.VideoApp.dto.response;

import com.phuckhanh.VideoApp.entity.Channel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BanChannelResponse {
    Integer idBanChannel;
    String reason;
    LocalDateTime dateTimeBan;
    ChannelResponse channel;
}
