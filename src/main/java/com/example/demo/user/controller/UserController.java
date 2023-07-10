package com.example.demo.user.controller;

import com.example.demo.user.controller.port.UserService;
import com.example.demo.user.controller.request.UserCreateRequest;
import com.example.demo.user.controller.request.UserUpdateRequest;
import com.example.demo.user.controller.request.UserVerifyRequest;
import com.example.demo.user.controller.response.UserCreateResponse;
import com.example.demo.user.controller.response.UserGetResponse;
import com.example.demo.user.controller.response.UserUpdateResponse;
import com.example.demo.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserGetResponse> getById(@PathVariable long id) {
        userService.login(id);
        User user = userService.getById(id);

        return ResponseEntity.ok().body(UserGetResponse.from(user));
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<Void> verify(@PathVariable long id, @RequestBody UserVerifyRequest userVerifyRequest) {
        userService.verify(id, userVerifyRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    public ResponseEntity<UserCreateResponse> create(@RequestBody UserCreateRequest userCreateRequest) {
        User user = userService.create(userCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(UserCreateResponse.from(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateResponse> update(@PathVariable long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        User user = userService.update(id, userUpdateRequest);

        return ResponseEntity.ok().body(UserUpdateResponse.from(user));
    }
}
