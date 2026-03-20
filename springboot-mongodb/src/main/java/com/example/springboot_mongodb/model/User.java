package com.example.springboot_mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class User {

    @Id
    private String id;

    @Indexed
    private String name;

    @Indexed(unique = true)
    private String email;


    private Address address;   // embedded
}
