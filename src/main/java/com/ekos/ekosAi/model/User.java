package com.ekos.ekosAi.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Data
@Document
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private String name;
    private String lastname;
    private String phoneNumber;
    private ZonedDateTime crated;


}
