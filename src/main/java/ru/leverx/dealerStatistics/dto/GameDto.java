package ru.leverx.dealerStatistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDto extends BaseDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Long user_id;
}
