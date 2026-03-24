package com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers;

import com.yostin.evolucioncb.chocolatinazo.domain.models.ChocolatinaUser;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.entities.ChocolatinaUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChocolatinaUserMapper {
    @Mapping(target = "userEmail", source = "userEmail")
    ChocolatinaUserEntity toEntityFromModel(ChocolatinaUser chocolatinaUser);
    @Mapping(target = "userEmail", source = "userEmail")
    ChocolatinaUser toModelFromEntity(ChocolatinaUserEntity chocolatinaUserEntity);
}
