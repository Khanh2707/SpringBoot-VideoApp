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
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idChannel;
    String avatar;
    String name;
    String nameUnique;
    String description;

    @OneToOne
    @JoinColumn(name = "id_account_channel", referencedColumnName = "idAccount")
    Account account;

    @OneToMany(mappedBy = "channel")
    List<Video> videos;
}
