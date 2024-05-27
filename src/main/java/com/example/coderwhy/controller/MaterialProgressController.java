package com.example.coderwhy.controller;

import com.example.coderwhy.entity.MaterialProgress;
import com.example.coderwhy.service.MaterialProgressService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MaterialProgressController {

    @Autowired
    private MaterialProgressService materialProgressService;

    @GetMapping("/materialProgress/getall")
    public List<MaterialProgress> getMaterialProgressList(){
        return materialProgressService.getAllMaterialProgress();
    }

    @PostMapping("/materialProgress/getfil")
    public List<MaterialProgress> getMaterialProgresssByFilters(@RequestBody Map<String, Object> request){
        return materialProgressService.getMaterialProgress(requestToMaterialProgress(request));
    }

    @PostMapping("/materialProgress/create")
    public MaterialProgress createMaterialProgress(@RequestBody Map<String, Object> request){
        return materialProgressService.createMaterialProgress(requestToMaterialProgress(request));
    }

    @PostMapping("/materialProgress/update")
    public UpdateResult updateMaterialProgress(@RequestBody Map<String, Object> request){
        return materialProgressService.updateMaterialProgress(requestToMaterialProgress(request));
    }

    @GetMapping("/materialProgress/delete/{materialProgress_id}")
    public DeleteResult deleteMaterialProgress(@PathVariable String materialProgress_id){
        return materialProgressService.deleteMaterialProgress(new ObjectId(materialProgress_id));
    }


    private MaterialProgress requestToMaterialProgress(Map<String, Object> request){
        MaterialProgress materialProgress = new MaterialProgress();
        if (request.get("_id") != null)
            materialProgress.set_id(new ObjectId((String) request.get("_id")));
        if (request.get("user_id") != null)
            materialProgress.setUser_id(new ObjectId((String) request.get("user_id")));
        if (request.get("material_id") != null)
            materialProgress.setMaterial_id(new ObjectId((String) request.get("material_id")));
        if (request.get("progress") != null)
            materialProgress.setProgress((Double) request.get("progress"));
        return materialProgress;
    }
    
}
