package ru.leverx.dealerStatistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.dto.UserResponseDto;
import ru.leverx.dealerStatistics.entity.User;
import ru.leverx.dealerStatistics.entity.UserRole;
import ru.leverx.dealerStatistics.exception.EntityNotFoundException;
import ru.leverx.dealerStatistics.mapper.UserMapper;
import ru.leverx.dealerStatistics.repository.FeedbackRepository;
import ru.leverx.dealerStatistics.repository.UserRepository;
import ru.leverx.dealerStatistics.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    //@Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponseDto create(UserDto userDto) {
        userDto.setUserRole(UserRole.TREIDER);
        User user = userMapper.toEntity(userDto);
        User saved = userRepository.save(user);
        calculateUserRating(saved);
        return userMapper.toDto(saved);
    }

    @Override
    public UserResponseDto get(Long id) {
        calculateUserRating(userRepository.findById(id).get());
        return userRepository.findById(id).map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " doesn't exist!"));
    }

    public void calculateUserRating(User user) {
        user.setRaiting(feedbackRepository.findRatingByUserId(user.getId()));
    }

    @Override
    public List<UserResponseDto> getUsersByRole(UserRole role) {
        return listToDto(userRepository.findByUserRole(role));
    }

    public List<UserResponseDto> listToDto(List<User> users) {
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }
}
