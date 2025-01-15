package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.*;
import com.study_buddy.study_buddy.repository.MaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.IOException;

@Service
public class MaterialService {
    private final MaterialRepository materialRepository;

    @Autowired
    public MaterialService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Material getMaterialById(Long materialId) {
        return materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material not found with id: " + materialId));
    }

    public Material getMaterialByFileName(String fileName) {
        return materialRepository.findByFileName(fileName);
    }

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }

    public List<Material> getAllMaterialsByUser(User user) {
        return materialRepository.findByUser(user);
    }

    public List<Material> getAllMaterialsByGroup(StudyGroup group) {
        return materialRepository.findByGroup(group);
    }

    public List<Material> getAllMaterialsByLesson(Lesson lesson) {
        return materialRepository.findByLesson(lesson);
    }

    public Material createMaterial(Material material){
        if (material.getUser() == null || material.getFileName() == null || material.getFileData() == null) {
            throw new IllegalArgumentException("User, file name, and file data are required.");
        }
        return materialRepository.save(material);
    }

    public void deleteMaterial(Long materialId) {
        if (!materialRepository.existsById(materialId)) {
            throw new IllegalArgumentException("Material not found with id: " + materialId);
        }
        materialRepository.deleteById(materialId);
    }
    
    public void deleteMaterialsByLesson(Long lessonId) {
        Lesson lesson = new Lesson();
        lesson.setLessonId(lessonId);
        List<Material> materials = materialRepository.findByLesson(lesson);
        if (materials.isEmpty()) {
            throw new IllegalArgumentException("No materials found for lesson with id: " + lessonId);
        }
        materialRepository.deleteAll(materials);
    }
    
    public void deleteMaterialsByGroup(Long groupId) {
        StudyGroup group = new StudyGroup();
        group.setGroupId(groupId);
        List<Material> materials = materialRepository.findByGroup(group);
        if (materials.isEmpty()) {
            throw new IllegalArgumentException("No materials found for group with id: " + groupId);
        }
        materialRepository.deleteAll(materials);
    }
}

