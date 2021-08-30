package com.example.demo5.controller;

import com.example.demo5.assembles.UserModelAssembler;
import com.example.demo5.exception.UserException;
import com.example.demo5.model.User;
import com.example.demo5.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE, MediaTypes.HAL_JSON_VALUE })
public class UserController {

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 1;
    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping("")
    @ResponseBody
    public CollectionModel<EntityModel<User>> findAll(
            @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE) Pageable pageable,
            PagedResourcesAssembler<User> pagedResourcesAssembler
    ) {
        Page<User> usersPage = userService.findAll(pageable);
        List<EntityModel<User>> users = usersPage.map(userModelAssembler::toModel).stream().collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class)).withSelfRel());
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
