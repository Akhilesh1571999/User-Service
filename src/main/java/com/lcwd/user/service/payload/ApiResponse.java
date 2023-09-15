package com.lcwd.user.service.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApiResponse {

    private String message;

    private boolean status;

    private HttpStatus httpStatus;
}
