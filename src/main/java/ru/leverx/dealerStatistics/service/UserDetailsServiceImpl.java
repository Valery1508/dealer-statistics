package ru.leverx.dealerStatistics.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.leverx.dealerStatistics.entity.AuthenticatedUser;
import ru.leverx.dealerStatistics.entity.User;
import ru.leverx.dealerStatistics.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    /*задача: по имени пользователя выдергивать самого пользователя из базы
    (если он есть)*/

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found!");
        }

        return new AuthenticatedUser(
                (org.springframework.security.core.userdetails.User) org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getUserRole().name())
                        .build(),
                user.getId());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
