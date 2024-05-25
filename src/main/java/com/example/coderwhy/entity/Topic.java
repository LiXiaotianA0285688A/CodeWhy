package com.example.coderwhy.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Topic")
public class Topic {

    @Id
    private ObjectId _id;
    private String name;
    private String description;
}