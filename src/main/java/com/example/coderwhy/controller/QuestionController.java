package com.example.coderwhy.controller;

import com.example.coderwhy.entity.Question;
import com.example.coderwhy.service.QuestionService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    @GetMapping("/question/preexercise")
    public List<Question> getPreExercise(){
        Question question = new Question();
        question.setLevel("pre_exercise");
        return questionService.getQuestions(question);
    }

    @GetMapping("/question/exercise/{material_id}")
    public List<Question> getExercise(@PathVariable String material_id){
        Question question = new Question();
        question.setLevel("exercise");
        question.setMaterial_id(new ObjectId(material_id));
        return questionService.getQuestions(question);
    }

    @GetMapping("/question/quiz")
    public List<Question> getQuiz(){
        Question question = new Question();
        question.setLevel("quiz");
        List<Question> qList = questionService.getQuestions(question);
        List<Question> result = new ArrayList<Question>();

        Random rand = new Random();

        for (int k = 0; k < 10; k++) {
            int i = rand.nextInt(qList.size());
            result.add(qList.get(i));
            qList.remove(i);
        }

        return result;
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
        if (request.get("explanation") != null)
            question.setExplanation((String) request.get("explanation"));
        if (request.get("is_verified") != null)
            question.setIs_verified((Boolean) request.get("is_verified"));
        return question;
    }
    
}
