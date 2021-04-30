package ru.leverx.dealerStatistics.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.leverx.dealerStatistics.dto.FeedbackDto;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.dto.UserResponseDto;
import ru.leverx.dealerStatistics.entity.UserRole;
import ru.leverx.dealerStatistics.service.FeedbackService;
import ru.leverx.dealerStatistics.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final FeedbackService feedbackService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.create(userDto));
    }

    @GetMapping
    public List<UserResponseDto> getUsers(@RequestParam(value = "role") UserRole role) {
        return userService.getUsersByRole(role);
    }


    //get user posts, get user comments
    @GetMapping("/{userId}/feedbacks")
    public List<FeedbackDto> getUserFeedbacks(@PathVariable Long userId) {
        return feedbackService.getFeedbacksByUserId(userId);
    }
}
