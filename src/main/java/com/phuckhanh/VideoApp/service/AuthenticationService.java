package com.phuckhanh.VideoApp.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.phuckhanh.VideoApp.constant.PredefinedRole;
import com.phuckhanh.VideoApp.dto.request.*;
import com.phuckhanh.VideoApp.dto.response.AuthenticationResponse;
import com.phuckhanh.VideoApp.dto.response.IntrospectResponse;
import com.phuckhanh.VideoApp.entity.Account;
import com.phuckhanh.VideoApp.entity.Channel;
import com.phuckhanh.VideoApp.entity.InvalidatedToken;
import com.phuckhanh.VideoApp.entity.Role;
import com.phuckhanh.VideoApp.exception.AppException;
import com.phuckhanh.VideoApp.exception.ErrorCode;
import com.phuckhanh.VideoApp.repository.AccountRepository;
import com.phuckhanh.VideoApp.repository.ChannelRepository;
import com.phuckhanh.VideoApp.repository.InvalidatedTokenRepository;
import com.phuckhanh.VideoApp.repository.RoleRepository;
import com.phuckhanh.VideoApp.repository.httpclient.OutboundUserClient;
import com.phuckhanh.VideoApp.repository.httpclient.OutboundVideoAppClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.Normalizer;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService {
    OutboundVideoAppClient outboundVideoAppClient;
    OutboundUserClient outboundUserClient;
    AccountRepository accountRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    RoleRepository roleRepository;
    ChannelRepository channelRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNED_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected long REFRESHABLE_DURATION;

    @NonFinal
    @Value("${outbound.chatapp.client-id}")
    protected String CLIENT_ID;

    @NonFinal
    @Value("${outbound.chatapp.client-secret}")
    protected String CLIENT_SECRET;

    @NonFinal
    @Value("${outbound.chatapp.redirect-uri}")
    protected String REDIRECT_URI;

    @NonFinal
    protected final String GRANT_TYPE = "authorization_code";

    public AuthenticationResponse outboundAuthenticate(String code) {
        var response = outboundVideoAppClient.exchangeToken(ExchangeTokenRequest.builder()
                .code(code)
                .clientId(CLIENT_ID)
                .clientSecret(CLIENT_SECRET)
                .redirectUri(REDIRECT_URI)
                .grantType(GRANT_TYPE)
                .build());

        log.info("TOKEN RESPONSE {}", response);

        var userInfo = outboundUserClient.getUserInfo("json", response.getAccessToken());

        log.info("User Info {}", userInfo);

        Set<Role> roles = new HashSet<>();
        roleRepository.findByName(PredefinedRole.USER_ROLE).ifPresent(roles::add);

        var account = accountRepository.findByUsername(userInfo.getEmail()).orElseGet(() -> {
            Account newAccount = accountRepository.save(Account.builder()
                    .username(userInfo.getEmail())
                    .password("")
                    .dateTimeCreate(LocalDateTime.now())
                    .roles(roles)
                    .build());

            Channel newChannel = channelRepository.save(Channel.builder()
                    .avatar(userInfo.getPicture())
                    .name(userInfo.getFamilyName() + " " + userInfo.getGivenName())
                    .nameUnique(generateChannelNameUnique(userInfo.getFamilyName() + " " + userInfo.getGivenName()))
                    .account(newAccount)
                    .build());
            channelRepository.save(newChannel);

            return newAccount;
        });

        var token = generateToken(account);

        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (AppException e) {
            isValid = false;
        }

        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        boolean authenticated = passwordEncoder
                .matches(request.getPassword(), account.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

//        boolean checkExisted = logLockAccountRepository.existsByAccount_IdAccount(account.getIdAccount());
//
//        if (checkExisted) {
//            if (logLockAccountRepository.findFirstByAccount_IdAccountOrderByDateTimeLockDesc(account.getIdAccount()).getStateLock()) {
//                throw new RuntimeException(logLockAccountRepository.findFirstByAccount_IdAccountOrderByDateTimeLockDesc(account.getIdAccount()).getReasonLock());
//            }
//        }

        var token = generateToken(account);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            var signToken = verifyToken(request.getToken(), false);

            String jit = signToken.getJWTClaimsSet().getJWTID();

            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            log.info(String.valueOf(expiryTime));

            InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                    .idInvalidatedToken(jit)
                    .expiryDate(expiryTime)
                    .build();

            invalidatedTokenRepository.save(invalidatedToken);
        } catch (AppException exception) {
            log.info("Token already expired");
        }
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException {
        var signJWT = verifyToken(request.getToken(), true);

        var jit = signJWT.getJWTClaimsSet().getJWTID();

        var expiryTime = signJWT.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .idInvalidatedToken(jit)
                .expiryDate(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);

        var username = signJWT.getJWTClaimsSet().getSubject();

        var account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var token = generateToken(account);

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNED_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh) ?
                new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                :
                signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    private String generateToken(Account account) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUsername())
                .issuer("https://VideoApp.phuckhanh.com/")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(account))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNED_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Account account) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(account.getRoles()))
            account.getRoles().forEach(role -> {
                stringJoiner.add(role.getName());
//                if (!CollectionUtils.isEmpty(role.getPermissions()))
//                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
            });

        return stringJoiner.toString();
    }

    public String generateChannelNameUnique(String fullName) {
        String[] nameParts = fullName.split(" ");
        StringBuilder userIdBuilder = new StringBuilder();

        if (nameParts.length < 2) {
            // Trường hợp tên không đầy đủ, chỉ trả về tên nguyên gốc
            return fullName.toLowerCase();
        }

        for (int i = 0; i < nameParts.length - 1; i++) {
            userIdBuilder.append(Character.toLowerCase(nameParts[i].charAt(0))); // Chữ cái đầu viết thường
        }

        userIdBuilder.append("_");

        String lastName = nameParts[nameParts.length - 1];
        userIdBuilder.append(Character.toLowerCase(lastName.charAt(0))); // Chữ cái đầu của tên cuối cùng viết thường

        String normalizedLastName = Normalizer.normalize(lastName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", ""); // Xóa các dấu
        userIdBuilder.append(normalizedLastName.toLowerCase().substring(1)); // Bỏ chữ cái đầu và viết thường phần còn lại của tên cuối cùng

        String baseUserId = userIdBuilder.toString();

        // Check for uniqueness and append number if needed
        int counter = 1;
        String uniqueUserId = baseUserId;
        while (channelRepository.existsByNameUnique(uniqueUserId)) {
            uniqueUserId = baseUserId + "_" + counter++;
        }

        return uniqueUserId;
    }
}
