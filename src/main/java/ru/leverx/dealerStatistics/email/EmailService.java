package ru.leverx.dealerStatistics.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    public void sendMessage(String to, String subject, String message);
}
