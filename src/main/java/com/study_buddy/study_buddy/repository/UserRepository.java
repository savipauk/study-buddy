package com.study_buddy.study_buddy.repository;

import com.study_buddy.study_buddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    User findByEmail(String email);
    User findByOauthId(String oauthId);

    //@Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :name, '%'))")
    @Query("SELECT u FROM User u WHERE u.username LIKE %:name%")
    List<User> findByUsernameIgnoreCase(@Param("name") String name);
}
