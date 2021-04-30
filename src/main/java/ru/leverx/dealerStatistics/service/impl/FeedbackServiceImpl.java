package ru.leverx.dealerStatistics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leverx.dealerStatistics.dto.FeedbackDto;
import ru.leverx.dealerStatistics.entity.Feedback;
import ru.leverx.dealerStatistics.exception.EntityNotFoundException;
import ru.leverx.dealerStatistics.mapper.FeedbackMapper;
import ru.leverx.dealerStatistics.repository.FeedbackRepository;
import ru.leverx.dealerStatistics.service.FeedbackService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    //@Qualifier("userRepository")
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public FeedbackDto create(FeedbackDto feedbackDto) {
        Feedback feedback = feedbackMapper.toEntity(feedbackDto);
        feedback.setApproved(false);
        Feedback saved = feedbackRepository.save(feedback);
        return feedbackMapper.toDto(saved);
    }

    @Override
    public FeedbackDto get(Long id) {
        return feedbackRepository.findById(id).map(feedbackMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Feedback with id = " + id + " doesn't exist!"));
    }

    @Override
    public List<FeedbackDto> getFeedbacks() {
        return listToDto(feedbackRepository.findAll());
    }

    @Override
    public List<FeedbackDto> getFeedbacksByUserId(Long userId) {
        return listToDto(feedbackRepository.findAllByUserId(userId));
    }

    public List<FeedbackDto> listToDto(List<Feedback> feedbacks) {
        return feedbacks.stream().map(feedbackMapper::toDto).collect(Collectors.toList());
    }
}
