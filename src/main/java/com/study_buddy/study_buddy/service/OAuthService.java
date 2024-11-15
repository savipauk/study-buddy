package com.study_buddy.study_buddy.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import com.study_buddy.study_buddy.model.StudyRole;
import com.study_buddy.study_buddy.model.StudyRole;
import com.study_buddy.study_buddy.model.User;
import com.study_buddy.study_buddy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.util.Collections;


import java.time.LocalDateTime;

@Service
public class OAuthService {

    @Autowired
    private UserRepository userRepository;


    // Configure Google’s token verifier
    private final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
            new NetHttpTransport(),
            GsonFactory.getDefaultInstance()
    ).setAudience(Collections.singletonList("4143611273-h8v79jdefdqr65l0n23efpg84r5vospr.apps.googleusercontent.com"))
            .build();

    public User processGoogleTokenResponse(String credential) throws Exception {
        // Parse the token
        GoogleIdToken idToken = verifier.verify(credential);

        if (idToken == null) {
            throw new Exception("Invalid ID token.");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        // Extract information from payload
        String googleId = payload.getSubject();
        String email = payload.getEmail();
        String firstName = (String) payload.get("given_name");
        String lastName = (String) payload.get("family_name");

        // Check if the user already exists in the database
        User user = userRepository.findByOauthId(googleId);

        if (user == null) {
            // Appropriate constructor: User(String email, String username, String oauthProvider, String oauthId, String firstName, String lastName, StudyRole unassigned)
            user = new User(email, email,"google", googleId, firstName, lastName, StudyRole.UNASSIGNED);

        } else {
            // Update the user's updatedAt timestamp if they already exist
            user.setUpdatedAt(LocalDateTime.now());
        }
        return user;
    }
}
