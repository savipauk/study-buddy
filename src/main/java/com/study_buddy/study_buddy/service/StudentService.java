package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.Student;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.repository.StudentRepository;
import com.study_buddy.study_buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(User user){
        Student student = new Student();
        student.setUser(user);
        studentRepository.save(student);
        return student;
    }
}
