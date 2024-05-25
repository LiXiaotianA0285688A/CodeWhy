package com.example.coderwhy.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Question")
public class Question {

    @Id
    private ObjectId _id;
    private ObjectId material_id;
    private String title;
    private String level;
    private String type;
    private String answer;
    private Boolean is_verified;
}