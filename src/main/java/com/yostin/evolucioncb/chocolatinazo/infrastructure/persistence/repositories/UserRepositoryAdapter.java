package com.yostin.evolucioncb.chocolatinazo.infrastructure.persistence.repositories;

import com.yostin.evolucioncb.chocolatinazo.domain.models.User;
import com.yostin.evolucioncb.chocolatinazo.domain.repositories.UserRepository;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        return userMapper.toUserFromEntity(jpaUserRepository.save(userMapper.toEntity(user)));
    }
}
