package com.example.demo5.controller;

import com.example.demo5.assembles.UserModelAssembler;
import com.example.demo5.exception.UserException;
import com.example.demo5.model.User;
import com.example.demo5.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler userModelAssembler;
    Pageable firstPageWithTwoElements = PageRequest.of(0, 2);

    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping("")
    public CollectionModel<EntityModel<User>> findAll(@PathVariable(required = false) int size) {
        List<EntityModel<User>> users = userService.findAll(firstPageWithTwoElements).map(userModelAssembler::toModel).stream().collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class)).withSelfRel(),
                linkTo(methodOn(UserController.class).findAll(size)).withRel(""));
    }

    @GetMapping("/{id}")
    public EntityModel<User> findById(@PathVariable long id) {
        User user = userService.findById(id).orElseThrow(() -> new UserException(id));
        return userModelAssembler.toModel(user);
    }

    @PostMapping("/")
    ResponseEntity<?> newUser(@RequestBody User newUser) {
        EntityModel<User> entityModel = userModelAssembler.toModel(userService.save(newUser));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

}
