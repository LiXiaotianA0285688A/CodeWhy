package com.example.coderwhy.controller;

import com.example.coderwhy.entity.Question;
import com.example.coderwhy.service.QuestionService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/getall")
    public List<Question> getQuestionList(){
        return questionService.getAllQuestions();
    }

    @PostMapping("/question/getfil")
    public List<Question> getQuestionsByFilters(@RequestBody Map<String, Object> request){
        return questionService.getQuestions(requestToQuestion(request));
    }

    @PostMapping("/question/create")
    public Question createQuestion(@RequestBody Map<String, Object> request){
        return questionService.createQuestion(requestToQuestion(request));
    }

    @PostMapping("/question/update")
    public UpdateResult updateQuestion(@RequestBody Map<String, Object> request){
        return questionService.updateQuestion(requestToQuestion(request));
    }

    @GetMapping("/question/delete/{question_id}")
    public DeleteResult deleteQuestion(@PathVariable String question_id){
        return questionService.deleteQuestion(new ObjectId(question_id));
    }


    private Question requestToQuestion(Map<String, Object> request){
        Question question = new Question();
        if (request.get("_id") != null)
            question.set_id(new ObjectId((String) request.get("_id")));
        if (request.get("material_id") != null)
            question.setMaterial_id(new ObjectId((String) request.get("material_id")));
        if (request.get("title") != null)
            question.setTitle((String) request.get("title"));
        if (request.get("level") != null)
            question.setLevel((String) request.get("level"));
        if (request.get("type") != null)
            question.setType((String) request.get("type"));
        if (request.get("answer") != null)
            question.setAnswer((String) request.get("answer"));
        if (request.get("is_verified") != null)
            question.setIs_verified((Boolean) request.get("is_verified"));
        return question;
    }
    
}
