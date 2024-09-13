package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.constant.PredefinedRole;
import com.phuckhanh.VideoApp.dto.request.AccountCreationRequest;
import com.phuckhanh.VideoApp.dto.request.AccountUpdatePasswordRequest;
import com.phuckhanh.VideoApp.dto.request.AccountUpdateRoleRequest;
import com.phuckhanh.VideoApp.dto.response.AccountResponse;
import com.phuckhanh.VideoApp.dto.response.RoleResponse;
import com.phuckhanh.VideoApp.dto.response.VideoResponse;
import com.phuckhanh.VideoApp.entity.Account;
import com.phuckhanh.VideoApp.entity.Channel;
import com.phuckhanh.VideoApp.entity.Role;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.mapper.AccountMapper;
import com.phuckhanh.VideoApp.mapper.RoleMapper;
import com.phuckhanh.VideoApp.repository.AccountRepository;
import com.phuckhanh.VideoApp.repository.ChannelRepository;
import com.phuckhanh.VideoApp.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    VerifyEmailService verifyEmailService;

    public AccountResponse getMyAccount() {
        var context = SecurityContextHolder.getContext();
        String usernameAccount = context.getAuthentication().getName();

        Account account = accountRepository.findByUsername(usernameAccount).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        return accountMapper.toAccountResponse(account);
    }

    public AccountResponse getAccountByChannelNameUnique(String nameUniqueChannel) {
        Account account = accountRepository.findAccountByChannel_NameUnique(nameUniqueChannel).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        return accountMapper.toAccountResponse(account);
    }

    public Page<AccountResponse> searchAccountsByAccountChannelName(String keyword, String propertySort, String optionSort, Integer page, Integer size, Integer idRole) {
        Pageable pageable = PageRequest.of(page, size);

        if ("amountVideo".equals(propertySort)) {
            if ("asc".equalsIgnoreCase(optionSort)) {
                return accountRepository.findAllOrderByVideoCountAscAndAccountChannelNameContainingIgnoreCase(keyword, pageable, idRole)
                        .map(accountMapper::toAccountResponse);
            } else {
                return accountRepository.findAllOrderByVideoCountDescAndAccountChannelNameContainingIgnoreCase(keyword, pageable, idRole)
                        .map(accountMapper::toAccountResponse);
            }
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(optionSort), propertySort);
            pageable = PageRequest.of(page, size, sort);
            if (idRole == 0) {
                return accountRepository.findByAccountChannelNameContainingIgnoreCase(keyword, pageable)
                        .map(accountMapper::toAccountResponse);
            } else {
                return accountRepository.findByAccountChannelNameContainingIgnoreCaseAndIdRole(keyword, pageable, idRole)
                        .map(accountMapper::toAccountResponse);
            }
        }
    }

    public Page<AccountResponse> getAllAccount(String propertySort, String optionSort, Integer page, Integer size, Integer idRole) {
        Pageable pageable = PageRequest.of(page, size);

        if ("amountVideo".equals(propertySort)) {
            if ("asc".equalsIgnoreCase(optionSort)) {
                return accountRepository.findAllOrderByVideoCountAsc(pageable, idRole).map(accountMapper::toAccountResponse);
            } else {
                return accountRepository.findAllOrderByVideoCountDesc(pageable, idRole).map(accountMapper::toAccountResponse);
            }
        } else {
            Sort sort = Sort.by(Sort.Direction.fromString(optionSort), propertySort);
            pageable = PageRequest.of(page, size, sort);

            if (idRole == 0) {
                return accountRepository.findAll(pageable)
                        .map(accountMapper::toAccountResponse);
            } else {
                return accountRepository.findAllByIdRole(idRole, pageable)
                        .map(accountMapper::toAccountResponse);
            }
        }
    }

    public AccountResponse createAccount(AccountCreationRequest request) {
        if (accountRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.ACCOUNT_EXISTED);

        if (channelRepository.existsByNameUnique(request.getNameUnique()))
            throw new AppException(ErrorCode.CHANNEL_EXISTED);

        if (verifyEmailService.getVerifyEmailNewest(request.getUsername()).getCode().equals(request.getCodeEmail())) {

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
        } else {
            throw new AppException(ErrorCode.INVALID_VERIFY_EMAIL);
        }
    }

    public AccountResponse updateAccountRole(String username, AccountUpdateRoleRequest request) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Role role = roleRepository.findById(request.getIdRole()).orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        account.setRoles(roles);

        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    public AccountResponse updateAccountPassword(String username, AccountUpdatePasswordRequest request) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (passwordEncoder.matches(request.getCurrentPassword(), account.getPassword()) || verifyEmailService.getVerifyEmailNewest(username).getCode().equals(request.getCurrentPassword())) {
            accountMapper.updateAccountPassword(account, request);

            account.setPassword(passwordEncoder.encode(request.getNewPassword()));

            return accountMapper.toAccountResponse(accountRepository.save(account));
        } else {
            throw new AppException(ErrorCode.INVALID_PASSWORD);
        }
    }

    public void deleteAccountByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        account.getRoles().clear();

        accountRepository.save(account);

        accountRepository.deleteAccountByUsername(username);
    }
}
