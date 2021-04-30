package ru.leverx.dealerStatistics.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.leverx.dealerStatistics.dto.FeedbackDto;
import ru.leverx.dealerStatistics.service.FeedbackService;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<FeedbackDto> getFeedbackById(@PathVariable Long id){
        return ResponseEntity.ok(feedbackService.get(id));
    }

    @PostMapping
    public ResponseEntity<FeedbackDto> create(@Valid @RequestBody FeedbackDto feedbackDto){
        return ResponseEntity.ok(feedbackService.create(feedbackDto));
    }

    //TODO получать только APPROVED отзывы
    @GetMapping
    public List<FeedbackDto> getFeedbacks(){
        return feedbackService.getFeedbacks();
    }
}
