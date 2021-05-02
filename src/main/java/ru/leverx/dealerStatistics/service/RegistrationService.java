package ru.leverx.dealerStatistics.service;

import org.springframework.stereotype.Service;
import ru.leverx.dealerStatistics.dto.EmailDto;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.dto.UserResponseDto;

@Service
public interface RegistrationService {
    UserResponseDto register(UserDto userDto);

    void checkUserData(UserDto userDto);

    String generateCode();

    UserResponseDto confirmRegistration(String code, EmailDto emailDto);
}
