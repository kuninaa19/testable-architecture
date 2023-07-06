package com.example.demo.user.controller;

import com.example.demo.user.controller.request.CreateUserRequest;
import com.example.demo.user.controller.request.UpdateUserRequest;
import com.example.demo.user.controller.request.VerifyUserRequest;
import com.example.demo.user.controller.response.CreateUserResponse;
import com.example.demo.user.controller.response.GetUserResponse;
import com.example.demo.user.controller.response.UpdateUserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> GetById(@PathVariable long id) {
        userService.login(id);
        User user = userService.getById(id);

        return ResponseEntity.ok().body(GetUserResponse.from(user));
    }

    @PostMapping("/{id}/verify")
    public ResponseEntity<Void> verify(@PathVariable long id, @RequestBody VerifyUserRequest verifyUserRequest) {
        userService.verify(id, verifyUserRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    public ResponseEntity<CreateUserResponse> create(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.create(createUserRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(CreateUserResponse.from(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateUserResponse> update(@PathVariable long id, @RequestBody UpdateUserRequest updateUserRequest) {
        User user = userService.update(id, updateUserRequest);

        return ResponseEntity.ok().body(UpdateUserResponse.from(user));
    }
}
