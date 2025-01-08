package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.GroupMember;
import com.study_buddy.study_buddy.model.GroupMemberID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberID> {
    // Custom queries if needed
}

