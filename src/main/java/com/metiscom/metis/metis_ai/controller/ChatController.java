package com.metiscom.metis.metis_ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping
    public String askOllama(@RequestParam String prompt) {
        return this.chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    // Per la vera chat in botta e risposta tramite Postman
    @PostMapping
    public ResponseEntity<String> chat(@RequestBody ChatRequest request) {
        String response = this.chatClient.prompt()
                .user(request.message())
                .call()
                .content();

        return ResponseEntity.ok(response);
    }
}

record ChatRequest(String message) {}