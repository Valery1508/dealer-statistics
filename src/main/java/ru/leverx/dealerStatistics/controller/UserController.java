package ru.leverx.dealerStatistics.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.service.UserService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.get(id));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto userDto){
        System.out.println("create method mazafaka");
        return ResponseEntity.ok(userService.create(userDto));
    }
/*
    //get all treiders (just list)
    @GetMapping(value = "/treiders")
    public ResponseEntity<UserDto> getTreiders(){

    }*/

}
