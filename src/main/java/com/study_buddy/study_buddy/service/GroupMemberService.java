package com.study_buddy.study_buddy.service;

import com.study_buddy.study_buddy.model.GroupMember;
import com.study_buddy.study_buddy.model.GroupMemberID;
import com.study_buddy.study_buddy.model.Student;
import com.study_buddy.study_buddy.model.StudyGroup;
import com.study_buddy.study_buddy.repository.GroupMemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;

    public GroupMemberService(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
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
}
