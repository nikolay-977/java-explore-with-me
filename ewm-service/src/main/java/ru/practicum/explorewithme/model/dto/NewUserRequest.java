package ru.practicum.explorewithme.model.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewUserRequest {
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
}
