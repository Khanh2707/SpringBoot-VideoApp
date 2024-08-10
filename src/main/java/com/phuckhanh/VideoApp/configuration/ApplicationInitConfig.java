package com.phuckhanh.VideoApp.configuration;

import com.phuckhanh.VideoApp.constant.PredefinedRole;
import com.phuckhanh.VideoApp.entity.Account;
import com.phuckhanh.VideoApp.entity.Channel;
import com.phuckhanh.VideoApp.entity.Role;
import com.phuckhanh.VideoApp.repository.AccountRepository;
import com.phuckhanh.VideoApp.repository.ChannelRepository;
import com.phuckhanh.VideoApp.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(AccountRepository accountRepository, RoleRepository roleRepository, ChannelRepository channelRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (accountRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                roleRepository.save(Role.builder()
                        .idRole(1)
                        .name(PredefinedRole.ADMIN_ROLE)
                        .description("có tất cả quyền")
                        .build());

                roleRepository.save(Role.builder()
                        .idRole(2)
                        .name(PredefinedRole.CENSOR_ROLE)
                        .description("có quyền nhận các báo cáo video vi phạm, xóa video và thông báo đến người dùng lý do xóa")
                        .build());

                roleRepository.save(Role.builder()
                        .idRole(3)
                        .name(PredefinedRole.USER_ROLE)
                        .description("có quyền quản lý video của bản thân")
                        .build());

                HashSet<Role> roles = new HashSet<>();
                roleRepository.findByName(PredefinedRole.USER_ROLE).ifPresent(roles::add);

                Account account = Account.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .dateTimeCreate(LocalDateTime.now())
                        .roles(roles)
                        .build();

                Channel channel = new Channel();

                channel.setName("admin");
                channel.setNameUnique("admin");
                channel.setAccount(account);

                accountRepository.save(account);
                channelRepository.save(channel);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
