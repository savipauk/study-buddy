package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.Student;
import com.study_buddy.study_buddy.model.StudyGroup;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.repository.StudentRepository;
import com.study_buddy.study_buddy.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudyGroupService {
    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private StudentRepository studentRepository;

    public StudyGroup createStudyGroup(StudyGroup studyGroup){ return studyGroupRepository.save(studyGroup);}

    public List<StudyGroup> getAllStudyGroups(){ return studyGroupRepository.findAll();}

    public StudyGroup getStudyGroupById(Long groupId){ return studyGroupRepository.findByGroupId(groupId);}

    public List<StudyGroup> getAllStudyGroupsByCreator(User user){
        List<StudyGroup> studyGroups = studyGroupRepository.findByCreator_StudentId(user.getUserId());
        Student student = studentRepository.findByStudentId(0L);
        for (StudyGroup group : studyGroups) {
            group.setCreator(student); // Or set to a placeholder user
            studyGroupRepository.save(group);
        }
        return studyGroups;
    }

}
