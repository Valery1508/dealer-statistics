package ru.leverx.dealerStatistics.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.leverx.dealerStatistics.dto.*;
import ru.leverx.dealerStatistics.email.EmailService;
import ru.leverx.dealerStatistics.entity.UserRole;
import ru.leverx.dealerStatistics.service.FeedbackService;
import ru.leverx.dealerStatistics.service.GameService;
import ru.leverx.dealerStatistics.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final FeedbackService feedbackService;

    private final GameService gameService;

    private final EmailService emailService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    // registration
    @PostMapping
    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserDto userDto) {
        emailService.sendMessage(userDto.getEmail(), "Registration form",
                "Hello, " + userDto.getFirstName() + " " + userDto.getLastName() +
                        ", Admit registration, please!"); //todo мейби вынести в юзерсервис
        return ResponseEntity.ok(userService.create(userDto));
    }

    @GetMapping
    public List<UserResponseDto> getUsers(@RequestParam(value = "role") UserRole role) {    //TODO чтобы выводило только аппрувнутых трейдеров
        return userService.getUsersByRole(role);
    }

    @GetMapping("/{userId}/feedbacks")
    public List<FeedbackDto> getUserFeedbacks(@PathVariable Long userId) {  //TODO чтобы выводило только аппрувнутых
        return feedbackService.getFeedbacksByUserId(userId);
    }

    @GetMapping("/{userId}/games")
    public List<GameDto> getUserGames(@PathVariable Long userId) {  //TODO чтобы выводило только аппрувнутых
        return gameService.getGamessByUserId(userId);
    }

    // only ADMIN can do this
    @PutMapping("/{treiderId}")
    public UserResponseDto approveTreider(@PathVariable Long treiderId) {
        return userService.approve(treiderId);
    }

    // only ADMIN can do this
    @PutMapping("/{treiderId}/feedback/{feedbackId}")
    public FeedbackDto approveFeedback(@PathVariable Long treiderId, @PathVariable Long feedbackId) {
        return feedbackService.approve(treiderId, feedbackId);
    }

    @GetMapping("/top")
    public List<UserTopResponseDto> getTopOfTreiders() {
        return userService.getTopOfTreiders();
    }
}
