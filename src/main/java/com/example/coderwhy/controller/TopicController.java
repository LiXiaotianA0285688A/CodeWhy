package com.example.coderwhy.controller;

import com.example.coderwhy.entity.Topic;
import com.example.coderwhy.service.TopicService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/topic/getall")
    public List<Topic> getTopicList(){
        return topicService.getAllTopics();
    }

    @PostMapping("/topic/getfil")
    public List<Topic> getTopicsByFilters(@RequestBody Map<String, Object> request){
        return topicService.getTopics(requestToTopic(request));
    }

    @PostMapping("/topic/create")
    public Topic createTopic(@RequestBody Map<String, Object> request){
        return topicService.createTopic(requestToTopic(request));
    }

    @PostMapping("/topic/update")
    public UpdateResult updateTopic(@RequestBody Map<String, Object> request){
        return topicService.updateTopic(requestToTopic(request));
    }

    @GetMapping("/topic/delete/{topic_id}")
    public DeleteResult deleteTopic(@PathVariable String topic_id){
        return topicService.deleteTopic(new ObjectId(topic_id));
    }


    private Topic requestToTopic(Map<String, Object> request){
        Topic topic = new Topic();
        if (request.get("_id") != null)
            topic.set_id(new ObjectId((String) request.get("_id")));
        if (request.get("name") != null)
            topic.setName((String) request.get("name"));
        if (request.get("description") != null)
            topic.setDescription((String) request.get("description"));
        return topic;
    }
    
}
