package ru.leverx.dealerStatistics.mapper;

import org.springframework.stereotype.Component;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.entity.User;

@Component
public class UserMapper {

    public User toEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword()); // TODO ???
        user.setUserRole(userDto.getUserRole());
        return user;
    }

    public UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword()); // TODO ???
        userDto.setUserRole(user.getUserRole());
        return userDto;
    }
}
