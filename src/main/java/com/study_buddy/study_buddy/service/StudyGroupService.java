package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.dto.StudyGroupDto;
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

    public List<StudyGroup> getStudyGroupsByCreator(Student creator) { return studyGroupRepository.findByCreator(creator); }

    public StudyGroup getStudyGroupById(Long groupId){ return studyGroupRepository.findByGroupId(groupId);}

    public List<StudyGroup> deleteAllStudyGroupsByCreator(User user){
        Student old_student = studentRepository.findByUserId(user.getUserId());
        List<StudyGroup> studyGroups = studyGroupRepository.findByCreator_StudentId(old_student.getStudentId());
        Student student = studentRepository.findByStudentId(0L);
        for (StudyGroup group : studyGroups) {
            group.setCreator(student); // Or set to a placeholder user
            studyGroupRepository.save(group);
        }
        return studyGroups;
    }

    public StudyGroupDto convertToDto(StudyGroup studyGroup, String username) {
        StudyGroupDto dto = new StudyGroupDto();
        dto.setStudyGroupId(studyGroup.getGroupId());
        dto.setEmail(studyGroup.getCreator().getUser().getEmail()); // Assuming the creator's User is linked
        dto.setGroupName(studyGroup.getGroupName());
        dto.setLocation(studyGroup.getLocation());
        dto.setxCoordinate(studyGroup.getxCoordinate());
        dto.setyCoordinate(studyGroup.getyCoordinate());
        dto.setDate(studyGroup.getDate());
        dto.setTime(studyGroup.getTime());
        dto.setMaxMembers(studyGroup.getMaxMembers());
        dto.setDescription(studyGroup.getDescription());
        dto.setExpirationDate(studyGroup.getExpirationDate());
        dto.setUsername(username);
        return dto;
    }

}
