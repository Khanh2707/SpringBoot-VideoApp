package com.phuckhanh.VideoApp.service;

import com.phuckhanh.VideoApp.dto.response.RoleResponse;
import com.phuckhanh.VideoApp.mapper.RoleMapper;
import com.phuckhanh.VideoApp.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;

    RoleMapper roleMapper;

    public List<RoleResponse> getAllRole() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }
}
