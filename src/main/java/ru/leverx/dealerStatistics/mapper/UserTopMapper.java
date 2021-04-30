package ru.leverx.dealerStatistics.mapper;

import org.springframework.stereotype.Component;
import ru.leverx.dealerStatistics.dto.UserTopResponseDto;
import ru.leverx.dealerStatistics.entity.User;

@Component
public class UserTopMapper {

    public UserTopResponseDto toDto(User user) {
        UserTopResponseDto userTopResponseDto = new UserTopResponseDto();
        userTopResponseDto.setId(user.getId());
        userTopResponseDto.setFirstName(user.getFirstName());
        userTopResponseDto.setLastName(user.getLastName());
        userTopResponseDto.setRating(user.getRating());
        return userTopResponseDto;
    }
}
