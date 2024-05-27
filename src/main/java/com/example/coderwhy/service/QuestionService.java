package com.example.coderwhy.service;

import com.example.coderwhy.entity.Question;
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
public class QuestionService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Question> getAllQuestions(){
        return mongoTemplate.findAll(Question.class);
    }

    public List<Question> getQuestions(Question question){
        ArrayList<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("_id").exists(true));
        if (question.get_id() != null)
            criterias.add(Criteria.where("_id").is(question.get_id()));
        if (question.getMaterial_id() != null)
            criterias.add(Criteria.where("material_id").is(question.getMaterial_id()));
        if (question.getTitle() != null)
            criterias.add(Criteria.where("title").is(question.getTitle()));
        if (question.getLevel() != null)
            criterias.add(Criteria.where("level").is(question.getLevel()));
        if (question.getType() != null)
            criterias.add(Criteria.where("type").is(question.getType()));
        if (question.getAnswer() != null)
            criterias.add(Criteria.where("answer").is(question.getAnswer()));
        if (question.getIs_verified() != null)
            criterias.add(Criteria.where("is_verified").is(question.getIs_verified()));
        Query qTotal = new Query(new Criteria().andOperator(criterias));

        return mongoTemplate.find(qTotal, Question.class);
    }

    public Question createQuestion(Question question) {
        question.set_id(null);
        Query qName = new Query(Criteria.where("title").is(question.getTitle()));
        List<Question> questionCheck = mongoTemplate.find(qName, Question.class);
        if (questionCheck.size() > 0){
            throw new IllegalStateException("Question has already existed");
        }
        else{
            Question result = mongoTemplate.insert(question);
            return result;
        }
    }

    public UpdateResult updateQuestion(Question question) {
        if (question.get_id() == null)
            throw new IllegalStateException("Question Id is required");

        Query qId = new Query(Criteria.where("_id").is(question.get_id()));
        List<Question> questionCheck = mongoTemplate.find(qId, Question.class);
        if (questionCheck.size() == 0){
            throw new IllegalStateException("Question did not exist");
        }

        Query query = new Query(Criteria.where("_id").is(question.get_id()));

        Update update = new Update();
        update.set("title",question.getTitle());
        update.set("type",question.getType());
        update.set("level",question.getLevel());
        update.set("answer",question.getAnswer());
        update.set("is_verified",question.getIs_verified());

        UpdateResult upsert = mongoTemplate.upsert(query, update, Question.class);

        return upsert;
    }

    public DeleteResult deleteQuestion(ObjectId id){
        Query qId = new Query(Criteria.where("_id").is(id));
        List<Question> questionCheck = mongoTemplate.find(qId, Question.class);
        if (questionCheck.size() == 0){
            throw new IllegalStateException("Question did not exist");
        }

        Query query = new Query(Criteria.where("_id").is(id));

        DeleteResult remove = mongoTemplate.remove(query, Question.class);

        return remove;
    }
    
}
