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
public class HistorySearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idHistorySearch;
    String content;
    LocalDateTime dateTimeSearch;

    @ManyToOne
    @JoinColumn(name = "id_channel_history_search", referencedColumnName = "idChannel")
    Channel channel;
}
