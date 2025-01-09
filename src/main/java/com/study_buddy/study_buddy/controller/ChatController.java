package com.study_buddy.study_buddy.controller;

import com.study_buddy.study_buddy.service.ChatService;
import com.study_buddy.study_buddy.dto.ChatResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<ChatResponse> sendMessage(@RequestBody ChatRequest request) {
        String userMessage = request.getMessage();

        // Dobivanje odgovora od OpenAI API-ja
        String botResponse = chatService.getChatResponse(userMessage);

        ChatResponse response = new ChatResponse(botResponse);
        return ResponseEntity.ok(response);
    }

}
