package com.example.coderwhy.service;

import com.example.coderwhy.entity.Material;
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
public class MaterialService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Material> getAllMaterials(){
        return mongoTemplate.findAll(Material.class);
    }

    public List<Material> getMaterials(Material material){
        ArrayList<Criteria> criterias = new ArrayList<Criteria>();
        criterias.add(Criteria.where("_id").exists(true));
        if (material.get_id() != null)
            criterias.add(Criteria.where("_id").is(material.get_id()));
        if (material.getTopic_id() != null)
            criterias.add(Criteria.where("topic_id").is(material.getTopic_id()));
        if (material.getType() != null)
            criterias.add(Criteria.where("type").is(material.getType()));
        if (material.getName() != null)
            criterias.add(Criteria.where("name").is(material.getName()));
        if (material.getFile() != null)
            criterias.add(Criteria.where("file").is(material.getFile()));
        if (material.getUpdate_date() != null)
            criterias.add(Criteria.where("update_date").is(material.getUpdate_date()));
        Query qTotal = new Query(new Criteria().andOperator(criterias));

        return mongoTemplate.find(qTotal, Material.class);
    }

    public Material createMaterial(Material material) {
        material.set_id(null);
        Query qName = new Query(Criteria.where("name").is(material.getName()));
        List<Material> materialCheck = mongoTemplate.find(qName, Material.class);
        if (materialCheck.size() > 0){
            throw new IllegalStateException("Material has already existed");
        }
        else{
            Material result = mongoTemplate.insert(material);
            return result;
        }
    }

    public UpdateResult updateMaterial(Material material) {
        if (material.get_id() == null)
            throw new IllegalStateException("Material Id is required");

        Query qId = new Query(Criteria.where("_id").is(material.get_id()));
        List<Material> materialCheck = mongoTemplate.find(qId, Material.class);
        if (materialCheck.size() == 0){
            throw new IllegalStateException("Material did not exist");
        }

        Query query = new Query(Criteria.where("_id").is(material.get_id()));

        Update update = new Update();
        update.set("name",material.getName());
        update.set("type",material.getType());
        update.set("file",material.getFile());
        update.set("update_date",material.getUpdate_date());

        UpdateResult upsert = mongoTemplate.upsert(query, update, Material.class);

        return upsert;
    }

    public DeleteResult deleteMaterial(ObjectId id){
        Query qId = new Query(Criteria.where("_id").is(id));
        List<Material> materialCheck = mongoTemplate.find(qId, Material.class);
        if (materialCheck.size() == 0){
            throw new IllegalStateException("Material did not exist");
        }

        Query query = new Query(Criteria.where("_id").is(id));

        DeleteResult remove = mongoTemplate.remove(query, Material.class);

        return remove;
    }
    
}
