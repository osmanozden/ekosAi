package com.ekos.ekosAi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document
public class Book {
    private String name;
    private String author;
    private int stock ;

}
