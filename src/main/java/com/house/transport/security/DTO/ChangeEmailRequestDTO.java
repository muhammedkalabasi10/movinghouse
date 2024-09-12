package com.house.transport.security.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEmailRequestDTO {
    private String oldEmail;
    private String newEmail;
    private String password;
}
