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
public class BanChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idBanChannel;
    String reason;
    LocalDateTime dateTimeBan;

    @OneToOne
    @JoinColumn(name = "id_channel_ban_channel", referencedColumnName = "idChannel")
    Channel channel;
}
