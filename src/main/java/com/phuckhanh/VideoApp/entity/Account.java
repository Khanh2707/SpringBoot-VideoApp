package com.phuckhanh.VideoApp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idAccount;
    String username;
    String password;
    LocalDateTime dateTimeCreate;

    @OneToOne(mappedBy = "account")
    Channel channel;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "id_account_account_role"),
            inverseJoinColumns = @JoinColumn(name = "id_role_account_role")
    )
    Set<Role> roles;
}
