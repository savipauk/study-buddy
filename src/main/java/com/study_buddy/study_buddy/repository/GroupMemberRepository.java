package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.GroupMember;
import com.study_buddy.study_buddy.model.GroupMemberID;
import com.study_buddy.study_buddy.model.Student;
import com.study_buddy.study_buddy.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberID> {
    List<GroupMember> findByMemberId(Student memberId);

    @Modifying
    @Transactional
    @Query("DELETE FROM GroupMember gm WHERE gm.memberId.studentId = :studentId AND gm.groupId.groupId = :groupId")
    void deleteByStudentIdAndGroupId(@Param("studentId") Long studentId, @Param("groupId") Long groupId);

}

