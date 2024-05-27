package com.example.coderwhy.service;

import com.example.coderwhy.entity.TopicProgress;
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
public class TopicProgressService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<TopicProgress> getAllTopicProgress(){
        return mongoTemplate.findAll(TopicProgress.class);
    }

    public List<TopicProgress> getTopicProgress(TopicProgress topicProgress){
        ArrayList<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("_id").exists(true));
        if (topicProgress.get_id() != null)
            criterias.add(Criteria.where("_id").is(topicProgress.get_id()));
        if (topicProgress.getUser_id() != null)
            criterias.add(Criteria.where("user_id").is(topicProgress.getUser_id()));
        if (topicProgress.getTopic_id() != null)
            criterias.add(Criteria.where("topic_id").is(topicProgress.getTopic_id()));
        Query qTotal = new Query(new Criteria().andOperator(criterias));

        return mongoTemplate.find(qTotal, TopicProgress.class);
    }

    public TopicProgress createTopicProgress(TopicProgress topicProgress) {
        topicProgress.set_id(null);
        Query qName = new Query(Criteria.where("user_id").is(topicProgress.getUser_id())
                .and("topic_id").is(topicProgress.getTopic_id()));
        List<TopicProgress> topicProgressCheck = mongoTemplate.find(qName, TopicProgress.class);
        if (topicProgressCheck.size() > 0){
            throw new IllegalStateException("TopicProgress has already existed");
        }
        else{
            TopicProgress result = mongoTemplate.insert(topicProgress);
            return result;
        }
    }

    public UpdateResult updateTopicProgress(TopicProgress topicProgress) {
        if (topicProgress.get_id() == null && (topicProgress.getUser_id() == null || topicProgress.getTopic_id() == null))
            throw new IllegalStateException("TopicProgress Id or Id pair is required");

        Query query;

        if (topicProgress.get_id() != null){
            Query qId = new Query(Criteria.where("_id").is(topicProgress.get_id()));
            List<TopicProgress> topicProgressCheck = mongoTemplate.find(qId, TopicProgress.class);
            if (topicProgressCheck.size() == 0){
                throw new IllegalStateException("TopicProgress did not exist");
            }

            query = new Query(Criteria.where("_id").is(topicProgress.get_id()));
        }
        else{
            Query qIdPair = new Query(Criteria.where("user_id").is(topicProgress.getUser_id())
                    .and("topic_id").is(topicProgress.getTopic_id()));
            List<TopicProgress> topicProgressCheck = mongoTemplate.find(qIdPair, TopicProgress.class);
            if (topicProgressCheck.size() == 0){
                throw new IllegalStateException("TopicProgress did not exist");
            }

            query = new Query(Criteria.where("user_id").is(topicProgress.getUser_id())
                    .and("topic_id").is(topicProgress.getTopic_id()));
        }

        Update update = new Update();
        update.set("progress",topicProgress.getProgress());

        UpdateResult upsert = mongoTemplate.upsert(query, update, TopicProgress.class);

        return upsert;
    }

    public DeleteResult deleteTopicProgress(ObjectId id){
        Query qId = new Query(Criteria.where("_id").is(id));
        List<TopicProgress> topicProgressCheck = mongoTemplate.find(qId, TopicProgress.class);
        if (topicProgressCheck.size() == 0){
            throw new IllegalStateException("TopicProgress did not exist");
        }

        Query query = new Query(Criteria.where("_id").is(id));

        DeleteResult remove = mongoTemplate.remove(query, TopicProgress.class);

        return remove;
    }
    
}
