package com.ekos.ekosAi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastname;
    private String phoneNumber;
    private LocalDateTime crated;

}
