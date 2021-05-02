package ru.leverx.dealerStatistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.dto.UserResponseDto;
import ru.leverx.dealerStatistics.dto.UserTopResponseDto;
import ru.leverx.dealerStatistics.email.EmailService;
import ru.leverx.dealerStatistics.entity.User;
import ru.leverx.dealerStatistics.entity.UserRole;
import ru.leverx.dealerStatistics.exception.EntityNotFoundException;
import ru.leverx.dealerStatistics.mapper.UserMapper;
import ru.leverx.dealerStatistics.mapper.UserTopMapper;
import ru.leverx.dealerStatistics.repository.FeedbackRepository;
import ru.leverx.dealerStatistics.repository.UserRepository;
import ru.leverx.dealerStatistics.service.RedisServiceImpl;
import ru.leverx.dealerStatistics.service.UserService;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserTopMapper userTopMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisServiceImpl redisService;

    @Autowired
    private EmailService emailService;

    @Override
    public UserResponseDto create(UserDto userDto) {
        checkUserData(userDto);

        User userFromDB = userRepository.findByEmail(userDto.getEmail());
        if(userFromDB != null) {
            throw new BadCredentialsException("Type another email!");
        }

        User user = userMapper.toEntity(userDto);
        user.setApproved(false);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        String code = generateCode();
        System.out.println(code);
        redisService.addCodeToRedis(user.getEmail(), code);

        String message = String.format(
                "Hello, %s %s. Go to link http://localhost:8080/activate/%s to admit registration, please.",
                userDto.getFirstName(), userDto.getLastName(), code);
        emailService.sendMessage(userDto.getEmail(), "Activate registration code", message);

        User saved = userRepository.save(user);
        calculateUserRating(saved);
        return userMapper.toDto(saved);
    }

    public void checkUserData(UserDto userDto){
        if(userDto.getFirstName() == null || userDto.getLastName() == null || userDto.getEmail() == null
                || userDto.getPassword() == null || userDto.getUserRole() != UserRole.TREIDER){
            throw new BadCredentialsException("Something went wrong, please, check your data!");
        }
    }

    public String generateCode(){
        Calendar cal = Calendar.getInstance();
        int sum = cal.get(YEAR) * YEAR + cal.get(MONTH) * MONTH + cal.get(Calendar.DAY_OF_MONTH) * Calendar.DAY_OF_MONTH + cal.get(Calendar.HOUR) * Calendar.HOUR + cal.get(Calendar.MINUTE) * Calendar.MINUTE + cal.get(Calendar.SECOND);
        String code = sum + "-" + UUID.randomUUID().toString();
        return code;
    }

    @Override
    public UserResponseDto get(Long id) {
        calculateUserRating(userRepository.findById(id).get());
        return userRepository.findById(id).map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " doesn't exist!"));
    }

    public void calculateUserRating(User user) {
        user.setRating(feedbackRepository.findRatingByUserId(user.getId()));
    }

    @Override
    public List<UserResponseDto> getUsersByRole(UserRole role) {
        return listToDto(userRepository.findByUserRole(role));
    }

    @Override
    public UserResponseDto approve(Long id) {
        User user = userRepository.findById(id).get();
        user.setApproved(true);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public List<UserTopResponseDto> getTopOfTreiders() {
        return listToTopDto(userRepository.findAllTreidersOrderByRating(UserRole.TREIDER));
    }

    @Override
    public List<UserResponseDto> getUsersByRoleAndApproved(UserRole role) {
        return listToDto(userRepository.findByUserRoleAndApproved(role));
    }

    public List<UserResponseDto> listToDto(List<User> users) {
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public List<UserTopResponseDto> listToTopDto(List<User> users) {
        return users.stream().map(userTopMapper::toDto).collect(Collectors.toList());
    }
}
