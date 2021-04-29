package ru.leverx.dealerStatistics.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.dto.UserResponseDto;
import ru.leverx.dealerStatistics.entity.User;

@Component
public class UserMapper {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Autowired
    private GameMapper gameMapper;

    public User toEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // TODO ???
        user.setUserRole(userDto.getUserRole());
        return user;
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPassword(user.getPassword()); // TODO ???
        userResponseDto.setUserRole(user.getUserRole());
        userResponseDto.setFeedbacks(feedbackMapper.toDtos(user.getFeedbacks()));
        userResponseDto.setGames(gameMapper.toDtos(user.getGames()));
        return userResponseDto;
    }
}
