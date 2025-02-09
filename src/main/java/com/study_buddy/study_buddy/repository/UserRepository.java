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

    @Query("SELECT u FROM User u WHERE u.username LIKE %:name%")
    List<User> findByUsernameIgnoreCase(@Param("name") String name);

    @Query(value = "select profile_picture from users where user_id = :user_id", nativeQuery = true)
    byte[] getProfilePicture(@Param("user_id") Long userId);
}
