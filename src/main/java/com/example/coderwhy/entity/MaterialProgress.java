package com.example.coderwhy.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("MaterialProgress")
public class MaterialProgress {

    @Id
    private ObjectId _id;
    private ObjectId user_id;
    private ObjectId material_id;
    private Double progress;
}
