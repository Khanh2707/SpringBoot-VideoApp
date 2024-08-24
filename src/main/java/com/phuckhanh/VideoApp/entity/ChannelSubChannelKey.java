package com.phuckhanh.VideoApp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Embeddable
public class ChannelSubChannelKey implements Serializable {
    @Column(name = "id_channel_1_sub_channel")
    Integer idChannel1;

    @Column(name = "id_channel_2_sub_channel")
    Integer idChannel2;
}
