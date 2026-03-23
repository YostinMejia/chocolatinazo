package com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers;

import com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto.CreateUserDto;
import com.yostin.evolucioncb.chocolatinazo.domain.models.User;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUserFromCreateDto(CreateUserDto createUserDto);
    User toUserFromEntity(UserEntity userEntity);
    UserEntity toEntity(User user);
}
