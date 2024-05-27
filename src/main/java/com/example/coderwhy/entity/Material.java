package com.example.coderwhy.entity;

import lombok.Data;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("Material")
public class Material {

    @Id
    private ObjectId _id;
    private ObjectId topic_id;
    private String type;
    private String name;
    private Binary file;
    private Date update_date;
}