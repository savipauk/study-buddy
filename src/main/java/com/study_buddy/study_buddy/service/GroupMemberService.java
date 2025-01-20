package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.GroupMember;
import com.study_buddy.study_buddy.model.GroupMemberID;
import com.study_buddy.study_buddy.model.Student;
import com.study_buddy.study_buddy.model.StudyGroup;
import com.study_buddy.study_buddy.repository.GroupMemberRepository;
import com.study_buddy.study_buddy.repository.StudentRepository;
import com.study_buddy.study_buddy.repository.StudyGroupRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMemberService {

    @PersistenceContext
    private EntityManager entityManager;


    private final GroupMemberRepository groupMemberRepository;
    private final StudyGroupRepository studyGroupRepository;
    private final StudentRepository studentRepository;

    public GroupMemberService(GroupMemberRepository groupMemberRepository, StudyGroupRepository studyGroupRepository, StudentRepository studentRepository) {
        this.groupMemberRepository = groupMemberRepository;
        this.studyGroupRepository = studyGroupRepository;
        this.studentRepository = studentRepository;
    }

    //Add a student to a study group.
    @Transactional
    public void addStudentToGroup(Student student, StudyGroup studyGroup) {
        // Check if the student is already a member
        GroupMemberID groupMemberID = new GroupMemberID(student.getStudentId(), studyGroup.getGroupId());
        if (groupMemberRepository.existsById(groupMemberID)) {
            throw new IllegalStateException("Student is already a member of this group!");
        }

        // Create a new GroupMember entity
        GroupMember groupMember = new GroupMember(student, studyGroup, LocalDateTime.now());

        // Persist the relationship
        groupMemberRepository.save(groupMember);
    }

    public List<GroupMember> getStudyGroupsByMemberId(Student memberId){ return groupMemberRepository.findByMemberId(memberId);}

    @Transactional
    public void deleteByGroupIdAndStudentId(Long groupId, Long studentId) {
        // Remove directly via repository if applicable
        groupMemberRepository.deleteByStudentIdAndGroupId(studentId, groupId);

        // Flush and clear the persistence context to avoid conflicts
        /*entityManager.flush();
        entityManager.clear();*/

        // Optionally fetch and update entities
        StudyGroup studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Study group not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));


        // Update relationships

        studyGroup.getParticipants().remove(student);
        student.getStudyGroups().remove(studyGroup);

        // Persist changes
        studyGroupRepository.save(studyGroup);
        studentRepository.save(student);
    }

    @Transactional
    public void deleteByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        
        List<GroupMember> groupMembersStudyGroups = groupMemberRepository.findByMemberId(student);

        for (GroupMember gm:groupMembersStudyGroups){
            StudyGroup studyGroup = gm.getGroup();

            // Update relationships
            studyGroup.getParticipants().remove(student);
            student.getStudyGroups().remove(studyGroup);

            // Persist changes
            studyGroupRepository.save(studyGroup);
            studentRepository.save(student);
        }
        
        // Remove directly via repository if applicable
        groupMemberRepository.deleteByStudentId(studentId);

        // Flush and clear the persistence context to avoid conflicts
        /*entityManager.flush();
        entityManager.clear();*/
    }

}
