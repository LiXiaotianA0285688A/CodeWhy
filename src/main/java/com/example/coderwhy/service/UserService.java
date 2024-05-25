package com.example.coderwhy.service;

import com.example.coderwhy.entity.User;
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
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getAllUsers(){
        return mongoTemplate.findAll(User.class);
    }

    public List<User> getUsers(User user){
        ArrayList<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("_id").exists(true));
        if (user.get_id() != null)
            criterias.add(Criteria.where("_id").is(user.get_id()));
        if (user.getUsername() != null)
            criterias.add(Criteria.where("username").is(user.getUsername()));
        if (user.getPassword() != null)
            criterias.add(Criteria.where("password").is(user.getPassword()));
        if (user.getRole() != null)
            criterias.add(Criteria.where("role").is(user.getRole()));
        Query qTotal = new Query(new Criteria().andOperator(criterias));

        return mongoTemplate.find(qTotal, User.class);
    }

    public User createUser(User user) {
        user.set_id(null);
        Query qName = new Query(Criteria.where("username").is(user.getUsername()));
        List<User> userCheck = mongoTemplate.find(qName, User.class);
        if (userCheck.size() > 0){
            throw new IllegalStateException("User has already existed");
        }
        else{
            User result = mongoTemplate.insert(user);
            return result;
        }
    }

    public UpdateResult updateUser(User user) {
        if (user.get_id() == null)
            throw new IllegalStateException("User Id is required");

        Query qId = new Query(Criteria.where("_id").is(user.get_id()));
        List<User> userCheck = mongoTemplate.find(qId, User.class);
        if (userCheck.size() == 0){
            throw new IllegalStateException("User did not exist");
        }

        Query query = new Query(Criteria.where("_id").is(user.get_id()));

        Update update = new Update();
        update.set("username",user.getUsername());
        update.set("password",user.getPassword());
        update.set("role",user.getRole());

        UpdateResult upsert = mongoTemplate.upsert(query, update, User.class);

        return upsert;
    }

    public DeleteResult deleteUser(ObjectId id){
        Query qId = new Query(Criteria.where("_id").is(id));
        List<User> userCheck = mongoTemplate.find(qId, User.class);
        if (userCheck.size() == 0){
            throw new IllegalStateException("User did not exist");
        }

        Query query = new Query(Criteria.where("_id").is(id));

        DeleteResult remove = mongoTemplate.remove(query, User.class);

        return remove;
    }




}
