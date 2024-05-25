package com.example.coderwhy.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("TopicProgress")
public class TopicProgress {

    @Id
    private ObjectId _id;
    private ObjectId user_id;
    private ObjectId topic_id;
    private Double progress;
}
