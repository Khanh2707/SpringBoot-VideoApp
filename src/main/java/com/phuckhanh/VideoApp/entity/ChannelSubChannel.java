package com.phuckhanh.VideoApp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class ChannelSubChannel {
    @EmbeddedId
    ChannelSubChannelKey idChannelSubChannelKey;

    @ManyToOne
    @MapsId("idChannel1")
    @JoinColumn(name = "id_channel_1_sub_channel")
    Channel channel1;

    @ManyToOne
    @MapsId("idChannel2")
    @JoinColumn(name = "id_channel_2_sub_channel")
    Channel channel2;
}
