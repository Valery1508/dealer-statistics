/*
package ru.leverx.dealerStatistics.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.leverx.dealerStatistics.email.EmailService;
import ru.leverx.dealerStatistics.entity.User;
import ru.leverx.dealerStatistics.exception.MailException;
import ru.leverx.dealerStatistics.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class RecoveryController {

    private final UserRepository userRepository;
    private final DealerTokenRepository dealerTokenRepository;
    private final EmailService emailService;

    @PostMapping("/forgot_password")
    public String sendMessage(@RequestBody String email) {   //отправить письмо с кодом из редиса на мейл
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new MailException("User with email: " + email + " doesn't exist.");
        }

        DealerToken dealerToken = new DealerToken(user.getEmail());
        String message = String.format("Hello, %s! \n" +
                "Password recovery code: %s", user.getFirstName(), dealerToken.getToken());
        emailService.sendMessage(user.getEmail(), "Activation code", message);

        dealerTokenRepository.save(dealerToken);
        return "Done";
    }

    @PostMapping("/reset")
    public String reset(@RequestBody RecoveryResponse recoveryResponse) throws Exception {
        DealerToken dealerToken = dealerTokenRepository.findById(recoveryResponse.getCode()).get();
        if (Objects.isNull(dealerToken)) return "error";

        VerificationToken newVerificationToken = new VerificationToken();
        VerificationToken oldVerificationToken = new VerificationToken(dealerToken.getToken());

        if (newVerificationToken.checkToken(oldVerificationToken)) {
            User user = userRepository.findByEmail(dealerToken.getEmail());
            user.setPassword(Password.getSaltedHash(recoveryResponse.getPassword()));
            userRepository.save(user);
            dealerTokenRepository.delete(dealerToken);
            return "New password";
        }
        return "Old password";
    }

    @GetMapping("/check_code")
    public String checkCode(@RequestBody String code) {
        DealerToken dealerToken;
        try {
            dealerToken = dealerTokenRepository.findById(code).get();
        }catch (NoSuchElementException exception){
            return "There is no such code";
        }

        VerificationToken newVerificationToken = new VerificationToken();
        VerificationToken oldVerificationToken = new VerificationToken(dealerToken.getToken());

        if (newVerificationToken.checkToken(oldVerificationToken)) {
            return code;
        } else {
            dealerTokenRepository.delete(dealerToken);
            return "Code is deprecated";
        }
    }

}
*/
