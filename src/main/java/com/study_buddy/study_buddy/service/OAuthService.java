package com.study_buddy.study_buddy.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;

import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.study_buddy.study_buddy.dto.StudyRole;

import java.time.LocalDateTime;

@Service
public class OAuthService {

    @Autowired
    private UserRepository userRepository;

    public User processGoogleTokenResponse(GoogleTokenResponse tokenResponse) throws Exception {
        String accessToken = tokenResponse.getAccessToken();
        String refreshToken = tokenResponse.getRefreshToken();

        GoogleIdToken idToken = tokenResponse.parseIdToken();
        GoogleIdToken.Payload payload = idToken.getPayload();

        String googleId = payload.getSubject();
        String email = payload.getEmail();
        String firstName = (String) payload.get("given_name");
        String lastName = (String) payload.get("family_name");
        // TODO: add profile picture to User from Google (optional)
        //String profilePictureUrl = (String) payload.get("picture");
        LocalDateTime createdAt = LocalDateTime.now();

        // Check if user already exists in the database
        User user = userRepository.findByOauthId(googleId);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setOauthId(googleId);
            user.setOauthProvider("google");
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            user.setRole(StudyRole.STUDENT);  // Default
            user.setCreatedAt(createdAt);
            user.setUpdatedAt(createdAt);

            userRepository.save(user);
        } else {
            // Update the access token and refresh token if user already exists
            user.setAccessToken(accessToken);
            user.setRefreshToken(refreshToken);
            user.setUpdatedAt(LocalDateTime.now());

            userRepository.save(user);
        }

        return user;
    }
}