package com.study_buddy.study_buddy.controller;

import com.study_buddy.study_buddy.dto.StudyGroupDto;
import com.study_buddy.study_buddy.model.Student;
import com.study_buddy.study_buddy.model.StudyGroup;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.service.GroupMemberService;
import com.study_buddy.study_buddy.service.StudentService;
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

    private final StudentService studentService;
    private final StudyGroupService studyGroupService;
    private final GroupMemberService groupMemberService;
    private final UserService userService;

    @Autowired
    public StudyGroupController(StudentService studentService, StudyGroupService studyGroupService, GroupMemberService groupMemberService, UserService userService) {
        this.studentService = studentService;
        this.studyGroupService = studyGroupService;
        this.groupMemberService = groupMemberService;
        this.userService = userService;
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
        Student creatorStudent = studentService.getStudentByUserId(creator.getUserId());

        StudyGroup studyGroup = new StudyGroup();
        studyGroup.setCreator(creatorStudent);
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
        groupMemberService.addStudentToGroup(creatorStudent, studyGroup);

        return ResponseEntity.status(HttpStatus.CREATED).body(studyGroup);
    }

    // Add a student to a study group
    @PostMapping("/{groupId}/add-student/{studentId}")
    public ResponseEntity<String> addStudentToGroup(@PathVariable("groupId") Long groupId, @PathVariable("studentId") Long studentId) {
        Student student = studentService.getStudentById(studentId);
        StudyGroup studyGroup = studyGroupService.getStudyGroupById(groupId);

        groupMemberService.addStudentToGroup(student, studyGroup);

        return ResponseEntity.ok("Student added to the group successfully.");
    }

    // TODO - edit this functions
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
