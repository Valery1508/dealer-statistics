package ru.leverx.dealerStatistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackDto extends BaseDto {

    @NotBlank
    private String message;

    @NotNull
    private double rating;

    private Long user_id;
}
