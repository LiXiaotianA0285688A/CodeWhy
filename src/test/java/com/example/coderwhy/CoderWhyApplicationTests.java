package com.example.coderwhy;

import com.example.coderwhy.entity.Material;
import com.example.coderwhy.entity.Question;
import com.example.coderwhy.entity.User;
import com.example.coderwhy.service.MaterialService;
import com.example.coderwhy.service.QuestionService;
import com.example.coderwhy.service.UserService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

@SpringBootTest
class CoderWhyApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private QuestionService questionService;

    @Test
    void contextLoads() throws IOException {
        /*String filePath = "D:/Nornir/study materials/CoderWhyRelated/Introduction to Modular Programming in Java.pptx";
        FileInputStream fis = new FileInputStream(filePath);
        byte[] buffer = new byte[(int) new File(filePath).length()];
        fis.read(buffer);
        Binary b = new Binary(buffer);*/

        /*String fileName = "D:/Nornir/study materials/CoderWhyRelated/quiz5.txt";
        String s = Files.readString(Paths.get(fileName));
        String[] sp1 = s.split("Question:");
        for (String sp1c : sp1) {
            String[] sp2 = sp1c.split("Answer:");
            if (sp2.length < 2)
                continue;
            System.out.println(sp2[0]);
            String[] sp3 = sp2[1].split("Explanation:");
            System.out.println(sp2[1].substring(0, 1));
            System.out.println(sp3[1]);



            Question question = new Question();
            question.setMaterial_id(new ObjectId("6654b5de5fc80d7de7c760c1"));
            question.setTitle(sp2[0]);
            question.setLevel("quiz");
            question.setType("multi");
            question.setAnswer(sp2[1].substring(0, 1));
            question.setExplanation(sp3[1]);
            question.setIs_verified(true);
            Question q1 = questionService.createQuestion(question);
            System.out.println(q1);
        }*/




        /*Material material = new Material();
        material.setTopic_id(new ObjectId("6654b395f2a367061dcdd940"));
        material.setName("Introduction to Modular Programming in Java.pptx");
        material.setType("pptx");
        material.setUpdate_date(new Date(System.currentTimeMillis()));*/

        /*for (User user2 : user1) {
            System.out.println(user2);
        }*/
    }

}
