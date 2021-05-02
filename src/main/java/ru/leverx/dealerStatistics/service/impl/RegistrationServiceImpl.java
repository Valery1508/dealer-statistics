package ru.leverx.dealerStatistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leverx.dealerStatistics.dto.EmailDto;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.dto.UserResponseDto;
import ru.leverx.dealerStatistics.email.EmailService;
import ru.leverx.dealerStatistics.entity.User;
import ru.leverx.dealerStatistics.entity.UserRole;
import ru.leverx.dealerStatistics.mapper.UserMapper;
import ru.leverx.dealerStatistics.repository.UserRepository;
import ru.leverx.dealerStatistics.service.RedisServiceImpl;
import ru.leverx.dealerStatistics.service.RegistrationService;
import ru.leverx.dealerStatistics.service.UserService;

import javax.imageio.spi.RegisterableService;
import java.util.Calendar;
import java.util.UUID;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

@Service
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisServiceImpl redisService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Override
    public UserResponseDto register(UserDto userDto) {
        checkUserData(userDto);

        User userFromDB = userRepository.findByEmail(userDto.getEmail());
        if (userFromDB != null) {
            throw new BadCredentialsException("Type another email!");
        }

        User user = userMapper.toEntity(userDto);
        user.setApproved(false);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setConfirmed(false);

        String code = generateCode();
        System.out.println(code);   //todo delete
        redisService.addCodeToRedis(user.getEmail(), code);

        String message = String.format(
                "Hello, %s %s. Go to link http://localhost:8080/users/auth/confirm/%s to confirm registration, please.",
                userDto.getFirstName(), userDto.getLastName(), code);
        emailService.sendMessage(userDto.getEmail(), "Activate registration code", message);

        User saved = userRepository.save(user);
        userService.calculateUserRating(saved);
        return userMapper.toDto(saved);
    }

    @Override
    public void checkUserData(UserDto userDto) {
        if (userDto.getFirstName() == null || userDto.getLastName() == null || userDto.getEmail() == null
                || userDto.getPassword() == null || userDto.getUserRole() != UserRole.TREIDER) {
            throw new BadCredentialsException("Something went wrong, please, check your data!");
        }
    }

    @Override
    public String generateCode() {
        Calendar cal = Calendar.getInstance();
        int sum = cal.get(YEAR) * YEAR + cal.get(MONTH) * MONTH + cal.get(Calendar.DAY_OF_MONTH) * Calendar.DAY_OF_MONTH + cal.get(Calendar.HOUR) * Calendar.HOUR + cal.get(Calendar.MINUTE) * Calendar.MINUTE + cal.get(Calendar.SECOND);
        String code = sum + "-" + UUID.randomUUID().toString();
        return code;
    }

    @Override
    public UserResponseDto confirmRegistration(String code, EmailDto emailDto) {
        User user = userRepository.findByEmail(emailDto.getEmail());
        if (user == null) {
            throw new BadCredentialsException("Invalid email");
        }

        if (!redisService.checkCodeFromRedis(emailDto.getEmail(), code)) {
            throw new BadCredentialsException("Invalid code");
        }
        user.setConfirmed(true);
        return userMapper.toDto(userRepository.save(user));
    }
}
