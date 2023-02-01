package com.qnocks.authorizationserver.mapper;

import com.qnocks.authorizationserver.dto.UserDto;
import com.qnocks.authorizationserver.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.login", target = "login")
    @Mapping(source = "token", target = "token")
    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(User user, String token);

    @Mapping(source = "encodedPassword", target = "password")
    User toUser(UserDto userDto, String encodedPassword);
}
