package com.example.coderwhy.service;

import com.example.coderwhy.entity.Topic;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Topic> getAllTopics(){
        return mongoTemplate.findAll(Topic.class);
    }

    public List<Topic> getTopics(Topic topic){
        ArrayList<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("_id").exists(true));
        if (topic.get_id() != null)
            criterias.add(Criteria.where("_id").is(topic.get_id()));
        if (topic.getName() != null)
            criterias.add(Criteria.where("name").is(topic.getName()));
        if (topic.getDescription() != null)
            criterias.add(Criteria.where("password").is(topic.getDescription()));
        Query qTotal = new Query(new Criteria().andOperator(criterias));

        return mongoTemplate.find(qTotal, Topic.class);
    }

    public Topic createTopic(Topic topic) {
        topic.set_id(null);
        Query qName = new Query(Criteria.where("name").is(topic.getName()));
        List<Topic> topicCheck = mongoTemplate.find(qName, Topic.class);
        if (topicCheck.size() > 0){
            throw new IllegalStateException("Topic has already existed");
        }
        else{
            Topic result = mongoTemplate.insert(topic);
            return result;
        }
    }

    public UpdateResult updateTopic(Topic topic) {
        if (topic.get_id() == null)
            throw new IllegalStateException("Topic Id is required");

        Query qId = new Query(Criteria.where("_id").is(topic.get_id()));
        List<Topic> topicCheck = mongoTemplate.find(qId, Topic.class);
        if (topicCheck.size() == 0){
            throw new IllegalStateException("Topic did not exist");
        }

        Query query = new Query(Criteria.where("_id").is(topic.get_id()));

        Update update = new Update();
        update.set("name",topic.getName());
        update.set("password",topic.getDescription());

        UpdateResult upsert = mongoTemplate.upsert(query, update, Topic.class);

        return upsert;
    }

    public DeleteResult deleteTopic(ObjectId id){
        Query qId = new Query(Criteria.where("_id").is(id));
        List<Topic> topicCheck = mongoTemplate.find(qId, Topic.class);
        if (topicCheck.size() == 0){
            throw new IllegalStateException("Topic did not exist");
        }

        Query query = new Query(Criteria.where("_id").is(id));

        DeleteResult remove = mongoTemplate.remove(query, Topic.class);

        return remove;
    }
}
