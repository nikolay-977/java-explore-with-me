package ru.practicum.explorewithme.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private String errorMessage;
    private Integer status;
}
