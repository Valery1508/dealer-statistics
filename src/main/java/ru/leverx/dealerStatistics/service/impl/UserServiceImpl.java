package ru.leverx.dealerStatistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.dto.UserResponseDto;
import ru.leverx.dealerStatistics.dto.UserTopResponseDto;
import ru.leverx.dealerStatistics.entity.User;
import ru.leverx.dealerStatistics.entity.UserRole;
import ru.leverx.dealerStatistics.exception.EntityNotFoundException;
import ru.leverx.dealerStatistics.mapper.UserMapper;
import ru.leverx.dealerStatistics.mapper.UserTopMapper;
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

    @Autowired
    private UserTopMapper userTopMapper;

    @Override
    public UserResponseDto create(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        user.setApproved(false);
        User saved = userRepository.save(user);
        calculateUserRating(saved);
        return userMapper.toDto(saved);
    }

    @Override
    public UserResponseDto get(Long id) {   //TODO убрать из dto пароль
        calculateUserRating(userRepository.findById(id).get());
        return userRepository.findById(id).map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " doesn't exist!"));
    }

    public void calculateUserRating(User user) {
        user.setRating(feedbackRepository.findRatingByUserId(user.getId()));
    }

    @Override
    public List<UserResponseDto> getUsersByRole(UserRole role) {    //TODO убрать из dto пароль
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

    public List<UserResponseDto> listToDto(List<User> users) {
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    public List<UserTopResponseDto> listToTopDto(List<User> users) {
        return users.stream().map(userTopMapper::toDto).collect(Collectors.toList());
    }
}
