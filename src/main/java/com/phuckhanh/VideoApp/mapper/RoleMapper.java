package com.phuckhanh.VideoApp.mapper;

import com.phuckhanh.VideoApp.dto.request.RoleCreationRequest;
import com.phuckhanh.VideoApp.dto.response.RoleResponse;
import com.phuckhanh.VideoApp.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleCreationRequest request);

    RoleResponse toRoleResponse(Role role);
}
