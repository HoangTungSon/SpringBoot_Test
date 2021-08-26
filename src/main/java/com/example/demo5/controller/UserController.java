package com.example.demo5.controller;

import com.example.demo5.assembles.UserModelAssembler;
import com.example.demo5.exception.UserException;
import com.example.demo5.model.User;
import com.example.demo5.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping("")
    public CollectionModel<EntityModel<User>> findAll() {
        List<EntityModel<User>> users = userService.findAll().stream().map(userModelAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<User> findById(@PathVariable long id) {
        User user = userService.findById(id).orElseThrow(() -> new UserException(id));
        return userModelAssembler.toModel(user);
    }

    @PostMapping("/users")
    ResponseEntity<?> newUser(@RequestBody User newUser) {
        EntityModel<User> entityModel = userModelAssembler.toModel(userService.save(newUser));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

}
