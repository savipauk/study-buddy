package com.study_buddy.study_buddy.controller;


import com.study_buddy.study_buddy.dto.LessonDto;
import com.study_buddy.study_buddy.model.*;
import com.study_buddy.study_buddy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final StudentService studentService;
    private final LessonService lessonService;
    private final LessonParticipantService lessonParticipantService;
    private final UserService userService;
    private final ProfessorService professorService;

    @Autowired
    public LessonController(StudentService studentService, ProfessorService professorService, LessonService lessonService, LessonParticipantService lessonParticipantService, UserService userService) {
        this.studentService = studentService;
        this.lessonService = lessonService;
        this.lessonParticipantService = lessonParticipantService;
        this.userService = userService;
        this.professorService = professorService;
    }


    // Get all lessons
    @GetMapping
    public List<Lesson> getAllLessonGroups() {
        return lessonService.getAllLessons();
    }

    // Get all lessons created by one professor
    @GetMapping("/createdBy/{what}/{usernameOrEmail}")
    public ResponseEntity<List<LessonDto>> getAllLessonsByProfessor(@PathVariable("usernameOrEmail") String usernameOrEmail,
                                                                    @PathVariable("what") String what) {
        User user;
        if(what.equals("email")){ user = userService.getUserByEmail(usernameOrEmail);}
        else if (what.equals("username")) { user = userService.getUserByUsername(usernameOrEmail);}
        else {return ResponseEntity.notFound().build();}

        if (user==null) {return ResponseEntity.notFound().build();}

        Professor professor = professorService.getProfessorByUserId(user.getUserId());
        if (professor == null) { return ResponseEntity.notFound().build(); }

        List<Lesson> lessons = lessonService.getAllLessonsByProfessor(professor);
        List<LessonDto> lessonDtos = lessons.stream()
                .map(lessonService::convertToDto)
                .toList();
        return ResponseEntity.ok(lessonDtos);
    }

    // Get all active lessons
    @GetMapping("/active")
    public ResponseEntity<List<LessonDto>> getAllActiveLessons() {
        List<Lesson> activeLessons = lessonService.getAllActiveLessons();
        if (activeLessons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<LessonDto> lessonDtos = activeLessons.stream()
                .map(lessonService::convertToDto)
                .toList();
        return ResponseEntity.ok(lessonDtos);
    }

    // Get all active studyGroups
    @GetMapping("/active/{username}")
    public ResponseEntity<List<LessonDto>> getAllLessonsForLessonParticipant(@PathVariable("username") String username) {
        User user = userService.getUserByUsername(username);
        if (user==null) {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}

        Student student = studentService.getStudentByUserId(user.getUserId());
        List<Lesson> activeLessonsForLessonParticipant = lessonService.getAllActiveLessonsForLessonParticipant(student);

        if (activeLessonsForLessonParticipant.isEmpty()) { return ResponseEntity.noContent().build(); }

        List<LessonDto> activeLessonsForLessonParticipantDto = activeLessonsForLessonParticipant.stream()
                .map(lessonService::convertToDto)
                .toList();
        return ResponseEntity.ok(activeLessonsForLessonParticipantDto);
    }

    // Get all active mass or one_on_one lessons
    @GetMapping("/active/lessonType/{lessonType}")
    public ResponseEntity<List<LessonDto>> getAllActiveMassLessons(@PathVariable("lessonType") LessonType lessonType) {
        List<Lesson> activeLessons = lessonService.getAllActiveLessonsByLessonType(lessonType);
        if (activeLessons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<LessonDto> lessonDtos = activeLessons.stream()
                .map(lessonService::convertToDto)
                .toList();
        return ResponseEntity.ok(lessonDtos);
    }

    // Get all filtered lessons
    @GetMapping("/filter/{input}")
    public ResponseEntity<List<LessonDto>> getAllFilteredLessons(@PathVariable("input") String input) {
        // Check if subject or location LIKE%:input
        List<Lesson> filteredLessons = lessonService.getAllFilteredLessons(input);

        // Check if professor's username LIKE%:input
        List<User> users = userService.getUserByCompareUsername(input);
        List<Professor> professors = users.stream()
                .map(user -> professorService.getProfessorByUserId(user.getUserId()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Lesson> usernameLessons = professors.stream()
                        .flatMap(professor -> lessonService.getAllLessonsByProfessor(professor).stream())
                .collect(Collectors.toList());;


        // Get only unique lessons
        List<Lesson> combinedLessons = new ArrayList<>();
        Set<Lesson> uniqueLessons = new HashSet<>(filteredLessons);
        uniqueLessons.addAll(usernameLessons);
        combinedLessons.addAll(uniqueLessons);


        List<LessonDto> lessonDtos = combinedLessons.stream()
                .map(lessonService::convertToDto)
                .toList();
        return ResponseEntity.ok(lessonDtos);
    }



    // Get a study group by ID
    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable Long id) {
        Optional<Lesson> lesson = Optional.ofNullable(lessonService.getLessonById(id));
        return lesson.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new study group
    @PostMapping("/create")
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto dto) {
        User creator = userService.getUserByEmail(dto.getEmail());
        Professor creatorProfessor = professorService.getProfessorByUserId(creator.getUserId());
        // Create lesson and store it into database
        Lesson lesson = new Lesson();
        lesson.setProfessor(creatorProfessor);
        lesson.setSubject(dto.getSubject());
        lesson.setDuration(dto.getDuration());
        lesson.setMaxMembers(dto.getMaxMembers());
        lesson.setMinMembers(dto.getMinMembers());
        lesson.setxCoordinate(dto.getxCoordinate());
        lesson.setyCoordinate(dto.getyCoordinate());
        lesson.setLocation(dto.getLocation());
        lesson.setLessonType(dto.getType());
        lesson.setPrice(dto.getPrice());
        lesson.setDate(dto.getDate());
        lesson.setTime(dto.getTime());
        lesson.setRegistrationDeadline(dto.getDate().minusDays(2));

        lessonService.createLesson(lesson);

        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.convertToDto(lesson));
    }

    // Add a student to a lesson
    @PostMapping("/{lessonId}/add-student/{email}")
    public ResponseEntity<Map<String, String>> addStudentToLesson(@PathVariable("lessonId") Long lessonId, @PathVariable("email") String email) {
        try{
            User user = userService.getUserByEmail(email);
            Student student = studentService.getStudentByUserId(user.getUserId());
            Lesson lesson = lessonService.getLessonById(lessonId);

            if(lesson.getStudentParticipants()==null){
                lessonParticipantService.addStudentToLesson(student, lesson);
                return ResponseEntity.ok(Map.of("message", "OK"));
            } else if (lesson.getStudentParticipants().size()<lesson.getMaxMembers()) {
                lessonParticipantService.addStudentToLesson(student, lesson);
                return ResponseEntity.ok(Map.of("message", "OK"));
            } else {
                lessonParticipantService.addStudentToLesson(student, lesson);
                return ResponseEntity.ok(Map.of("message", "GROUP_FULL"));
            }


        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{lessonId}/remove-student/{email}")
    public ResponseEntity<Void> removeStudentFromLesson(@PathVariable("lessonId") Long lessonId, @PathVariable("email") String email) {
        try {
            Lesson lesson = lessonService.findByLessonId(lessonId);
            User user = userService.getUserByEmail(email);
            Student student = studentService.getStudentByUserId(user.getUserId());
            lessonParticipantService.deleteByLessonIdAndStudentId(lessonId, student.getStudentId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
