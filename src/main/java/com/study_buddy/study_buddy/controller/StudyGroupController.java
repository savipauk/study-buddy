package com.study_buddy.study_buddy.controller;

import com.study_buddy.study_buddy.dto.LessonDto;
import com.study_buddy.study_buddy.dto.StudyGroupDto;
import com.study_buddy.study_buddy.model.*;
import com.study_buddy.study_buddy.service.GroupMemberService;
import com.study_buddy.study_buddy.service.StudentService;
import com.study_buddy.study_buddy.service.StudyGroupService;
import com.study_buddy.study_buddy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

    // Get all study groups created by one user
    @GetMapping("/createdBy/{what}/{usernameOrEmail}")
    public ResponseEntity<List<StudyGroupDto>> getStudyGroupsByCreator(@PathVariable("usernameOrEmail") String usernameOrEmail,
                                                                       @PathVariable("what") String what) {
        User user;
        if(what.equals("email")){ user = userService.getUserByEmail(usernameOrEmail);}
        else if (what.equals("username")) { user = userService.getUserByUsername(usernameOrEmail);}
        else {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}

        if (user==null) {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}

        Student creator = studentService.getStudentByUserId(user.getUserId());
        if (creator == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); }

        List<StudyGroup> studyGroups = studyGroupService.getStudyGroupsByCreator(creator);
        List<StudyGroupDto> studyGroupDtos = studyGroups.stream()
                .map(studyGroupService::convertToDto)
                .toList();

        return ResponseEntity.ok(studyGroupDtos);
    }


    // Get all active studyGroups
    @GetMapping("/active")
    public ResponseEntity<List<StudyGroupDto>> getAllActiveStudyGroups() {
        List<StudyGroup> activeStudyGroups = studyGroupService.getActiveStudyGroups();
        if (activeStudyGroups.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<StudyGroupDto> activeStudyGroupDtos = activeStudyGroups.stream()
                .map(studyGroupService::convertToDto)
                .toList();
        return ResponseEntity.ok(activeStudyGroupDtos);
    }

    // Get all active studyGroups
    @GetMapping("/active/{username}")
    public ResponseEntity<List<StudyGroupDto>> getAllActiveStudyGroupsForMember(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        if (user==null) {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}

        Student student = studentService.getStudentByUserId(user.getUserId());
        List<StudyGroup> activeStudyGroupsForMember = studyGroupService.getActiveStudyGroupsForMember(student);

        if (activeStudyGroupsForMember.isEmpty()) { return ResponseEntity.noContent().build(); }

        List<StudyGroupDto> activeStudyGroupDtos = activeStudyGroupsForMember.stream()
                .map(studyGroupService::convertToDto)
                .toList();
        return ResponseEntity.ok(activeStudyGroupDtos);
    }

    // Get all filtered lessons
    @GetMapping("/filter/{input}")
    public ResponseEntity<List<StudyGroupDto>> getAllFilteredStudyGroups(@PathVariable("input") String input) {
        // Check if groupName or location or description LIKE%:input
        List<StudyGroup> filteredStudyGroup = studyGroupService.getAllFilteredStudyGroups(input);

        // Check if creator's username LIKE%:input
        List<User> users = userService.getUserByCompareUsername(input);
        List<Student> students = users.stream()
                .map(user -> studentService.getStudentByUserId(user.getUserId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<StudyGroup> usernameStudyGroups = students.stream()
                .flatMap(student -> studyGroupService.getStudyGroupsByCreator(student).stream())
                .collect(Collectors.toList());;


        // Get only unique lessons
        List<StudyGroup> combinedStudyGroups = new ArrayList<>();
        Set<StudyGroup> uniqueStudyGroups = new HashSet<>(filteredStudyGroup);
        uniqueStudyGroups.addAll(usernameStudyGroups);
        combinedStudyGroups.addAll(uniqueStudyGroups);


        List<StudyGroupDto> activeStudyGroupDtos = combinedStudyGroups.stream()
                .map(studyGroupService::convertToDto)
                .toList();
        return ResponseEntity.ok(activeStudyGroupDtos);
    }

    // Get a study group by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudyGroup> getStudyGroupById(@PathVariable Long id) {
        Optional<StudyGroup> studyGroup = Optional.ofNullable(studyGroupService.getStudyGroupById(id));
        return studyGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new study group
    @PostMapping("/create")
    public ResponseEntity<StudyGroupDto> createStudyGroup(@RequestBody StudyGroupDto dto) {
        // Creating new studyGroup
        User creator = userService.getUserByEmail(dto.getEmail());
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

        return ResponseEntity.status(HttpStatus.CREATED).body(studyGroupService.convertToDto(studyGroup));
    }

    // Add a student to a study group
    @PostMapping("/{groupId}/add-student/{email}")
    public ResponseEntity<Map<String, String>> addStudentToGroup(@PathVariable("groupId") Long groupId, @PathVariable("email") String email) {
        try{
            User user = userService.getUserByEmail(email);
            Student student = studentService.getStudentByUserId(user.getUserId());
            StudyGroup studyGroup = studyGroupService.getStudyGroupById(groupId);
            if(studyGroup.getParticipants()==null){
                groupMemberService.addStudentToGroup(student, studyGroup);
                return ResponseEntity.ok(Map.of("message","OK"));
            } else if (studyGroup.getParticipants().size()<studyGroup.getMaxMembers()){
                groupMemberService.addStudentToGroup(student, studyGroup);
                return ResponseEntity.ok(Map.of("message","OK"));
            } else {
                return ResponseEntity.ok(Map.of("message","GROUP_FULL"));
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

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
    }*/

    // Remove a student from a study group
    @DeleteMapping("/{groupId}/remove-student/{email}")
    public ResponseEntity<Void> removeStudentFromGroup(@PathVariable("groupId") Long groupId, @PathVariable("email") String email) {
        try {
            StudyGroup studyGroup = studyGroupService.findByGroupId(groupId);

            User user = userService.getUserByEmail(email);
            Student student = studentService.getStudentByUserId(user.getUserId());
            groupMemberService.deleteByGroupIdAndStudentId(groupId, student.getStudentId());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }



}
