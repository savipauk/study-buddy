package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.Student;
import com.study_buddy.study_buddy.model.StudyGroup;
import com.study_buddy.study_buddy.repository.StudentRepository;
import com.study_buddy.study_buddy.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyGroupService {
    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private StudentRepository studentRepository;

    /*public void addStudentToGroup(Long groupId, Long studentId) {
        StudyGroup studyGroup = studyGroupRepository.findByStudyGroupId(groupId);
                //.orElseThrow(() -> new RuntimeException("Study group not found"));
        Student student = studentRepository.findByStudentId(studentId);
                //.orElseThrow(() -> new RuntimeException("Student not found"));

        studyGroup.addParticipant(student);
        studyGroupRepository.save(studyGroup); // Save the updated study group
    }*/

    public StudyGroup createStudyGroup(StudyGroup studyGroup){ return studyGroupRepository.save(studyGroup);}

    public List<StudyGroup> getAllStudyGroups(){ return studyGroupRepository.findAll();}

    public StudyGroup getStudyGroupById(Long studyGroupId){ return studyGroupRepository.findByStudyGroupId(studyGroupId);}

}
