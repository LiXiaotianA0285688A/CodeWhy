package com.example.coderwhy;

import com.example.coderwhy.entity.User;
import com.example.coderwhy.service.UserService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@SpringBootTest
class CoderWhyApplicationTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserService userService;
    @Test
    void contextLoads() {
        User user = new User();
        user.set_id(new ObjectId("665209bf9c2bac69a162f941"));
        user.setUsername("Fessta");
        user.setPassword("Fessta2560");
        user.setRole("admin");
        UpdateResult user1 = userService.updateUser(user);
        System.out.println(user1);
        /*for (User user2 : user1) {
            System.out.println(user2);
        }*/
    }

}
