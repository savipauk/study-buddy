package com.study_buddy.study_buddy.controller;

import com.study_buddy.study_buddy.dto.StudyGroupDto;
import com.study_buddy.study_buddy.model.StudyGroup;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.service.StudyGroupService;
import com.study_buddy.study_buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/studyGroup")
public class StudyGroupController {

    private final UserService userService;
    private final StudyGroupService studyGroupService;

    @Autowired
    public StudyGroupController(UserService userService, StudyGroupService studyGroupService) {
        this.userService = userService;
        this.studyGroupService = studyGroupService;
    }


    // Get all study groups
    @GetMapping
    public List<StudyGroup> getAllStudyGroups() {
        return studyGroupService.getAllStudyGroups();
    }

    // Get a study group by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudyGroup> getStudyGroupById(@PathVariable Long id) {
        Optional<StudyGroup> studyGroup = Optional.ofNullable(studyGroupService.getStudyGroupById(id));
        return studyGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new study group
    @PostMapping("/create")
    public ResponseEntity<StudyGroup> createStudyGroup(@RequestBody StudyGroupDto dto) {
        // Creating new studyGroup
        User creator = userService.getUserByEmail(dto.getCreatorEmail());

        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setCreator(creator);
        studyGroup.setGroupName(dto.getGroupName());
        studyGroup.setLocation(dto.getLocation());
        studyGroup.setxCoordinate(dto.getxCoordinate());
        studyGroup.setyCoordinate(dto.getyCoordinate());
        studyGroup.setDate(dto.getDate());
        studyGroup.setTime(dto.getTime());
        studyGroup.setMaxMembers(dto.getMaxMembers());
        studyGroup.setDescription(dto.getDescription());
        studyGroup.setExpirationDate(dto.getDate().minusDays(3));

        studyGroupService.createStudyGroup(studyGroup);

        // Inserting studyGroup creator into GroupMember table


        return ResponseEntity.status(HttpStatus.CREATED).body(studyGroup);
    }

    /*// Update a study group
    @PutMapping("/{id}")
    public ResponseEntity<StudyGroup> updateStudyGroup(@PathVariable Long id, @RequestBody StudyGroup updatedStudyGroup) {
        Optional<StudyGroup> updatedGroup = studyGroupService.updateStudyGroup(id, updatedStudyGroup);
        return updatedGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a study group
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudyGroup(@PathVariable Long id) {
        if (studyGroupService.deleteStudyGroup(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Add a student to a study group
    @PostMapping("/{groupId}/add-student/{studentId}")
    public ResponseEntity<Void> addStudentToGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        try {
            studyGroupService.addStudentToGroup(groupId, studentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Remove a student from a study group
    @DeleteMapping("/{groupId}/remove-student/{studentId}")
    public ResponseEntity<Void> removeStudentFromGroup(@PathVariable Long groupId, @PathVariable Long studentId) {
        try {
            studyGroupService.removeStudentFromGroup(groupId, studentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }*/
}
