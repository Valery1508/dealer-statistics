package ru.leverx.dealerStatistics.service;

import org.springframework.stereotype.Service;
import ru.leverx.dealerStatistics.dto.FeedbackDto;

import java.util.List;

@Service
public interface FeedbackService {
    FeedbackDto create(FeedbackDto feedbackDto);

    FeedbackDto get(Long id);

    List<FeedbackDto> getFeedbacks();

    List<FeedbackDto> getFeedbacksByUserId(Long userId);

    FeedbackDto approve(Long userId, Long feedbackId);
}
