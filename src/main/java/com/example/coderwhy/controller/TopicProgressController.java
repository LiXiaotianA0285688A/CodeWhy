package com.example.coderwhy.controller;

import com.example.coderwhy.entity.TopicProgress;
import com.example.coderwhy.service.TopicProgressService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TopicProgressController {

    @Autowired
    private TopicProgressService topicProgressService;

    @GetMapping("/topicProgress/getall")
    public List<TopicProgress> getTopicProgressList(){
        return topicProgressService.getAllTopicProgress();
    }

    @PostMapping("/topicProgress/getfil")
    public List<TopicProgress> getTopicProgresssByFilters(@RequestBody Map<String, Object> request){
        return topicProgressService.getTopicProgress(requestToTopicProgress(request));
    }

    @PostMapping("/topicProgress/create")
    public TopicProgress createTopicProgress(@RequestBody Map<String, Object> request){
        return topicProgressService.createTopicProgress(requestToTopicProgress(request));
    }

    @PostMapping("/topicProgress/update")
    public UpdateResult updateTopicProgress(@RequestBody Map<String, Object> request){
        return topicProgressService.updateTopicProgress(requestToTopicProgress(request));
    }

    @GetMapping("/topicProgress/delete/{topicProgress_id}")
    public DeleteResult deleteTopicProgress(@PathVariable String topicProgress_id){
        return topicProgressService.deleteTopicProgress(new ObjectId(topicProgress_id));
    }


    private TopicProgress requestToTopicProgress(Map<String, Object> request){
        TopicProgress topicProgress = new TopicProgress();
        if (request.get("_id") != null)
            topicProgress.set_id(new ObjectId((String) request.get("_id")));
        if (request.get("user_id") != null)
            topicProgress.setUser_id(new ObjectId((String) request.get("user_id")));
        if (request.get("topic_id") != null)
            topicProgress.setTopic_id(new ObjectId((String) request.get("topic_id")));
        if (request.get("progress") != null)
            topicProgress.setProgress((Double) request.get("progress"));
        return topicProgress;
    }
    
}
