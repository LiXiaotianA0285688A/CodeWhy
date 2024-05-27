package com.example.coderwhy.service;

import com.example.coderwhy.entity.MaterialProgress;
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
public class MaterialProgressService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<MaterialProgress> getAllMaterialProgress(){
        return mongoTemplate.findAll(MaterialProgress.class);
    }

    public List<MaterialProgress> getMaterialProgress(MaterialProgress materialProgress){
        ArrayList<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("_id").exists(true));
        if (materialProgress.get_id() != null)
            criterias.add(Criteria.where("_id").is(materialProgress.get_id()));
        if (materialProgress.getUser_id() != null)
            criterias.add(Criteria.where("user_id").is(materialProgress.getUser_id()));
        if (materialProgress.getMaterial_id() != null)
            criterias.add(Criteria.where("material_id").is(materialProgress.getMaterial_id()));
        Query qTotal = new Query(new Criteria().andOperator(criterias));

        return mongoTemplate.find(qTotal, MaterialProgress.class);
    }

    public MaterialProgress createMaterialProgress(MaterialProgress materialProgress) {
        materialProgress.set_id(null);
        Query qName = new Query(Criteria.where("user_id").is(materialProgress.getUser_id())
                .and("material_id").is(materialProgress.getMaterial_id()));
        List<MaterialProgress> materialProgressCheck = mongoTemplate.find(qName, MaterialProgress.class);
        if (materialProgressCheck.size() > 0){
            throw new IllegalStateException("MaterialProgress has already existed");
        }
        else{
            MaterialProgress result = mongoTemplate.insert(materialProgress);
            return result;
        }
    }

    public UpdateResult updateMaterialProgress(MaterialProgress materialProgress) {
        if (materialProgress.get_id() == null && (materialProgress.getUser_id() == null || materialProgress.getMaterial_id() == null))
            throw new IllegalStateException("MaterialProgress Id or Id pair is required");

        Query query;

        if (materialProgress.get_id() != null){
            Query qId = new Query(Criteria.where("_id").is(materialProgress.get_id()));
            List<MaterialProgress> materialProgressCheck = mongoTemplate.find(qId, MaterialProgress.class);
            if (materialProgressCheck.size() == 0){
                throw new IllegalStateException("MaterialProgress did not exist");
            }

            query = new Query(Criteria.where("_id").is(materialProgress.get_id()));
        }
        else{
            Query qIdPair = new Query(Criteria.where("user_id").is(materialProgress.getUser_id())
                    .and("material_id").is(materialProgress.getMaterial_id()));
            List<MaterialProgress> materialProgressCheck = mongoTemplate.find(qIdPair, MaterialProgress.class);
            if (materialProgressCheck.size() == 0){
                throw new IllegalStateException("MaterialProgress did not exist");
            }

            query = new Query(Criteria.where("user_id").is(materialProgress.getUser_id())
                    .and("material_id").is(materialProgress.getMaterial_id()));
        }

        Update update = new Update();
        update.set("progress",materialProgress.getProgress());

        UpdateResult upsert = mongoTemplate.upsert(query, update, MaterialProgress.class);

        return upsert;
    }

    public DeleteResult deleteMaterialProgress(ObjectId id){
        Query qId = new Query(Criteria.where("_id").is(id));
        List<MaterialProgress> materialProgressCheck = mongoTemplate.find(qId, MaterialProgress.class);
        if (materialProgressCheck.size() == 0){
            throw new IllegalStateException("MaterialProgress did not exist");
        }

        Query query = new Query(Criteria.where("_id").is(id));

        DeleteResult remove = mongoTemplate.remove(query, MaterialProgress.class);

        return remove;
    }
    
}
