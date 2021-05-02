package ru.leverx.dealerStatistics.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.leverx.dealerStatistics.entity.BaseEntity;
import ru.leverx.dealerStatistics.entity.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends BaseEntity {

    @NotBlank
    @Size(min = 1, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 50)
    private String email;

    @NotBlank
    @Size(message = "password is too short (min = 8) or too long (max = 25)", min = 8, max = 70)
    private String password;

    @NotNull
    private UserRole userRole;
}
