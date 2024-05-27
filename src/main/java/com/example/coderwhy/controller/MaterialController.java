package com.example.coderwhy.controller;

import com.example.coderwhy.entity.Material;
import com.example.coderwhy.service.MaterialService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/material/getall")
    public List<Material> getMaterialList(){
        return materialService.getAllMaterials();
    }

    @PostMapping("/material/getfil")
    public List<Material> getMaterialsByFilters(@RequestBody Map<String, Object> request){
        return materialService.getMaterials(requestToMaterial(request));
    }

    @PostMapping("/material/create")
    public Material createMaterial(@RequestBody Map<String, Object> request){
        return materialService.createMaterial(requestToMaterial(request));
    }

    @PostMapping("/material/update")
    public UpdateResult updateMaterial(@RequestBody Map<String, Object> request){
        return materialService.updateMaterial(requestToMaterial(request));
    }

    @GetMapping("/material/delete/{material_id}")
    public DeleteResult deleteMaterial(@PathVariable String material_id){
        return materialService.deleteMaterial(new ObjectId(material_id));
    }


    private Material requestToMaterial(Map<String, Object> request){
        Material material = new Material();
        if (request.get("_id") != null)
            material.set_id(new ObjectId((String) request.get("_id")));
        if (request.get("topic_id") != null)
            material.setTopic_id(new ObjectId((String) request.get("topic_id")));
        if (request.get("name") != null)
            material.setName((String) request.get("name"));
        if (request.get("type") != null)
            material.setType((String) request.get("type"));
        if (request.get("file") != null)
            material.setFile((String) request.get("file"));
        if (request.get("update_date") != null)
            material.setUpdate_date((Date) request.get("update_date"));
        return material;
    }
    
}
