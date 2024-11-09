package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByOauthId(String oauthId);

}
