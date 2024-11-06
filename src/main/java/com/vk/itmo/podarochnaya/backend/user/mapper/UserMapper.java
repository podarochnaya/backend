package com.vk.itmo.podarochnaya.backend.user.mapper;

import com.vk.itmo.podarochnaya.backend.user.dto.UserResponse;
import com.vk.itmo.podarochnaya.backend.user.jpa.UserEntity;
import com.vk.itmo.podarochnaya.backend.wishlist.mapper.GiftMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    GiftMapper INSTANCE = Mappers.getMapper(GiftMapper.class);

    @Mapping(source = "fullname", target = "fullname")
    @Mapping(source = "id", target = "id")
    UserResponse toUserResponse(UserEntity entity);
    List<UserResponse> toUserResponseList(List<UserEntity> entities);
}