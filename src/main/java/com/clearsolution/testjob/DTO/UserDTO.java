package com.clearsolution.testjob.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    @NotEmpty(message = "first name is required")
    private String firstName;
    @NotEmpty(message = "first name is required")
    private String lastName;
    @NotEmpty(message = "email field is required")
    @Email(message = "email was given in wrong format")
    private String email;
    @NotNull(message = "birth date is required")
    @Past(message = "invalid date of birth")
    private LocalDateTime birthDate;
    private String address;
    private String phoneNumber;
}
