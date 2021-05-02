package ru.leverx.dealerStatistics.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.leverx.dealerStatistics.dto.*;
import ru.leverx.dealerStatistics.email.EmailService;
import ru.leverx.dealerStatistics.entity.UserRole;
import ru.leverx.dealerStatistics.service.FeedbackService;
import ru.leverx.dealerStatistics.service.GameService;
import ru.leverx.dealerStatistics.service.UserDetailsServiceImpl;
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
    public List<UserResponseDto> getUsersByRole(@RequestParam(value = "role") UserRole role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/treiders")
    public List<UserResponseDto> getTreiders() {
        return userService.getUsersByRoleAndApproved(UserRole.TREIDER);
    }

    @GetMapping("/{userId}/feedbacks")
    public List<FeedbackDto> getUserFeedbacks(@PathVariable Long userId) {  //TODO чтобы выводило только аппрувнутых
        return feedbackService.getFeedbacksByUserId(userId);
    }

    @GetMapping("/{userId}/games")
    public List<GameDto> getUserGames(@PathVariable Long userId) {  //TODO чтобы выводило только аппрувнутых
        return gameService.getGamesByUserId(userId);
    }

    @PutMapping("/approve/{treiderId}")
    public UserResponseDto approveTreider(@PathVariable Long treiderId) {
        return userService.approve(treiderId);
    }

    @PutMapping("/approve/{treiderId}/feedback/{feedbackId}")
    public FeedbackDto approveFeedback(@PathVariable Long treiderId, @PathVariable Long feedbackId) {
        return feedbackService.approve(treiderId, feedbackId);
    }

    @GetMapping("/top")
    public List<UserTopResponseDto> getTopOfTreiders() {
        return userService.getTopOfTreiders();
    }

    @GetMapping("/my/games")
    public List<GameDto> getAuthenticationUserGames(Authentication authentication) {
        return gameService.getUserGames(authentication);
    }

    @GetMapping("/my/feedbacks")
    public List<FeedbackDto> getAuthenticationUserFeedbacks(Authentication authentication) {
        return feedbackService.getUserFeedbacks(authentication);
    }
}
