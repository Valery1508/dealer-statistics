package ru.leverx.dealerStatistics.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.leverx.dealerStatistics.dto.FeedbackDto;
import ru.leverx.dealerStatistics.entity.Feedback;
import ru.leverx.dealerStatistics.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedbackMapper {

    @Autowired
    private UserRepository userRepository;

    public Feedback toEntity(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setId(feedbackDto.getId());
        feedback.setMessage(feedbackDto.getMessage());
        feedback.setRating(feedbackDto.getRating());
        feedback.setUser(userRepository.findById(feedbackDto.getUser_id()).get());
        return feedback;
    }

    public FeedbackDto toDto(Feedback feedback) {
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setId(feedback.getId());
        feedbackDto.setMessage(feedback.getMessage());
        feedbackDto.setRating(feedback.getRating());
        feedbackDto.setUser_id(feedback.getUser().getId());
        return feedbackDto;
    }

    public List<FeedbackDto> toDtos(List<Feedback> feedbacks) {
        return feedbacks.stream().map(this::toDto).collect(Collectors.toList());
    }
}
