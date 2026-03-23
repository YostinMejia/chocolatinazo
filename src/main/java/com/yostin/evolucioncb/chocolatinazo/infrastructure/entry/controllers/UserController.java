package com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.controllers;

import com.yostin.evolucioncb.chocolatinazo.application.service.UserService;
import com.yostin.evolucioncb.chocolatinazo.domain.models.User;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.entry.dto.CreateUserDto;
import com.yostin.evolucioncb.chocolatinazo.infrastructure.mappers.UserMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    public ResponseEntity<User> save(@Valid @RequestBody CreateUserDto createUserDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                userService.createUser(
                        userMapper.toUserFromCreateDto(createUserDto)
                )
        );
    }

}
