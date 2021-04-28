package ru.leverx.dealerStatistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leverx.dealerStatistics.dto.UserDto;
import ru.leverx.dealerStatistics.mapper.UserMapper;
import ru.leverx.dealerStatistics.entity.User;
import ru.leverx.dealerStatistics.exception.EntityNotFoundException;
import ru.leverx.dealerStatistics.repository.UserRepository;
import ru.leverx.dealerStatistics.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    //@Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public UserDto get(Long id) {
        return userRepository.findById(id).map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User with id = " + id + " doesn't exist!"));
    }

}