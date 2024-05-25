package com.example.coderwhy.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("User")
public class User {

    @Id
    private ObjectId _id;
    private String username;
    private String password;
    private String role;
}
