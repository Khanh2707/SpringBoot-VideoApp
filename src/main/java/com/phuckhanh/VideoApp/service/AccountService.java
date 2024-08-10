package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.constant.PredefinedRole;
import com.phuckhanh.VideoApp.dto.request.AccountCreationRequest;
import com.phuckhanh.VideoApp.dto.response.AccountResponse;
import com.phuckhanh.VideoApp.entity.Account;
import com.phuckhanh.VideoApp.entity.Channel;
import com.phuckhanh.VideoApp.entity.Role;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.AccountMapper;
import com.phuckhanh.VideoApp.repository.AccountRepository;
import com.phuckhanh.VideoApp.repository.ChannelRepository;
import com.phuckhanh.VideoApp.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountService {
    AccountRepository accountRepository;

    ChannelRepository channelRepository;

    AccountMapper accountMapper;

    PasswordEncoder passwordEncoder;

    RoleRepository roleRepository;

    public AccountResponse getMyAccount() {
        var context = SecurityContextHolder.getContext();
        String usernameAccount = context.getAuthentication().getName();

        Account account = accountRepository.findByUsername(usernameAccount).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        return accountMapper.toAccountResponse(account);
    }

    public List<AccountResponse> getListAccountByNameChannel(String nameChannel) {
        return accountRepository.findByChannel_NameContaining(nameChannel).stream()
                .map(accountMapper::toAccountResponse)
                .toList();
    }

    public AccountResponse createAccount(AccountCreationRequest request) {
        if (accountRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.ACCOUNT_EXISTED);

        if (channelRepository.existsByNameUnique(request.getNameUnique()))
            throw new AppException(ErrorCode.CHANNEL_EXISTED);

        Account account = accountMapper.toAccount(request);

        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setDateTimeCreate(LocalDateTime.now());

        HashSet<Role> roles = new HashSet<>();
        roleRepository.findByName(PredefinedRole.USER_ROLE).ifPresent(roles::add);
        account.setRoles(roles);

        Channel channel = new Channel();

        channel.setName(request.getName());
        channel.setNameUnique(request.getNameUnique());
        channel.setAccount(account);

        accountRepository.save(account);
        channelRepository.save(channel);

        return accountMapper.toAccountResponse(account);
    }

    public void deleteAccountByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        account.getRoles().clear();

        accountRepository.save(account);

        accountRepository.deleteAccountByUsername(username);
    }
}
