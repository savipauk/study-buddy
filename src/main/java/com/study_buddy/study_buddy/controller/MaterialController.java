package com.study_buddy.study_buddy.controller;


import com.study_buddy.study_buddy.dto.MaterialDto;
import com.study_buddy.study_buddy.model.*;
import com.study_buddy.study_buddy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/material")
public class MaterialController {

    private final MaterialService materialService;
    private final UserService userService;
    private final StudyGroupService studyGroupService;
    private final LessonService lessonService;

    @Autowired
    public MaterialController(MaterialService materialService, UserService userService, StudyGroupService studyGroupService, LessonService lessonService) {
        this.materialService = materialService;
        this.userService = userService;
        this.studyGroupService = studyGroupService;
        this.lessonService = lessonService;
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<MaterialDto>> getAllGroupMaterials(@PathVariable Long groupId) {
        StudyGroup group = studyGroupService.getStudyGroupById(groupId);
        List<MaterialDto> materials = materialService.getAllMaterialsByGroup(group)
            .stream()
            .map(MaterialDto::convertToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(materials);
    }

    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity<List<MaterialDto>> getAllLessonMaterials(@PathVariable Long lessonId) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        List<MaterialDto> materials = materialService.getAllMaterialsByLesson(lesson)
                .stream()
                .map(MaterialDto::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(materials);
    }

    @GetMapping("/{materialId}")
    public ResponseEntity<MaterialDto> getMaterialById(@PathVariable Long materialId) {
        Material material = materialService.getMaterialById(materialId);
        return ResponseEntity.ok(MaterialDto.convertToDto(material));
    }
    /* 
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MaterialDto>> getAllMaterialsByUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        List<MaterialDto> materials = materialService.getAllMaterialsByUser(user)
                .stream()
                .map(MaterialDto::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(materials);
    }
    */

    @GetMapping("/preview/{materialId}")
    public ResponseEntity<byte[]> previewFile(@PathVariable("materialId") Long materialId) {
        Material material = materialService.getMaterialById(materialId);
        if(MaterialDto.convertToDto(material).getFileData() == null){throw new IllegalArgumentException("File data is missing.");}
        
        return ResponseEntity.ok()
                .header("Content-Type", material.getMimeType())
                .header("Content-Disposition", "inline; filename=\"" + material.getFileName() + "\"")
                .body(MaterialDto.convertToDto(material).getFileData());
    }

    @GetMapping("/download/{materialId}")
        public ResponseEntity<byte[]> downloadFile(@PathVariable("materialId") Long materialId) {
        Material material = materialService.getMaterialById(materialId);
        if(MaterialDto.convertToDto(material).getFileData() == null){throw new IllegalArgumentException("File data is missing.");}

        return ResponseEntity.ok()
                .header("Content-Type", material.getMimeType())
                .header("Content-Disposition", "attachment; filename=\"" + material.getFileName() + "\"")
                .body(MaterialDto.convertToDto(material).getFileData());
    }

    @GetMapping("/download/group/{groupId}")
    public ResponseEntity<List<MaterialDto>> downloadAllGroupFiles(@PathVariable("groupId") Long groupId) {
        StudyGroup group = studyGroupService.getStudyGroupById(groupId);
        List<Material> materials = materialService.getAllMaterialsByGroup(group);

        List<MaterialDto> materialDtos = materials.stream()
                .map(MaterialDto::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(materialDtos);
    }

    @GetMapping("/download/lesson/{lessonId}")
    public ResponseEntity<List<MaterialDto>> downloadAllLessonFiles(@PathVariable("lessonId") Long lessonId) {
        Lesson lesson = lessonService.getLessonById(lessonId);
        List<Material> materials = materialService.getAllMaterialsByLesson(lesson);

        List<MaterialDto> materialDtos = materials.stream()
                .map(MaterialDto::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(materialDtos);
    }



   @PostMapping("/upload")
    public ResponseEntity<Material> uploadMaterial(@RequestBody MaterialDto materialDto) {
        if (materialDto == null) {throw new IllegalArgumentException("Request body is missing.");}
        User creator = userService.getUserById(materialDto.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));
        StudyGroup group = materialDto.getGroupId() != null ? studyGroupService.getStudyGroupById(materialDto.getGroupId()) : null;
        Lesson lesson = materialDto.getLessonId() != null ? lessonService.getLessonById(materialDto.getLessonId()) : null;

        if(lesson.getProfessor().getUser().getUserId() != materialDto.getUserId() && materialDto.getLessonId() != null){
            throw new IllegalArgumentException("You can not upload files if you are not a creator of the lesson");
            }

        Material material = new Material();
        material.setUser(creator);
        material.setGroup(group);
        material.setLesson(lesson);
        material.setFileData(materialDto.getFileData());
        material.setFileName(materialDto.getFileName());
        material.setMimeType(materialDto.getMimeType());
        material.setDescription(materialDto.getDescription());
        material.setUploadDate(materialDto.getUploadDate());

        Material savedMaterial = materialService.createMaterial(material);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMaterial);
    }
    @DeleteMapping("/{materialId}")
    public ResponseEntity<Void> deleteMaterialById(@PathVariable("materialId") Long materialId, @RequestParam Long userId) {
        try {
            Material material = materialService.getMaterialById(materialId);

            if (material == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (material.getLesson() != null) {
                Lesson lesson = material.getLesson();
                if (!lesson.getProfessor().getUser().getUserId().equals(userId)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }

            materialService.deleteMaterial(materialId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/lesson/{lessonId}")
    public ResponseEntity<Void> deleteMaterialsByLesson(@PathVariable("lessonId") Long lessonId, @RequestParam Long userId) {
        try {
            Lesson lesson = lessonService.getLessonById(lessonId);

            if (lesson == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            if (!lesson.getProfessor().getUser().getUserId().equals(userId)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            materialService.deleteMaterialsByLesson(lessonId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/group/{groupId}")
    public ResponseEntity<Void> deleteMaterialsByGroup(@PathVariable("groupId") Long groupId) {
        try {
            StudyGroup group = studyGroupService.getStudyGroupById(groupId);

            if (group == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            materialService.deleteMaterialsByGroup(groupId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
