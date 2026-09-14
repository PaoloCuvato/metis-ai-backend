package com.metiscom.metis.metis_ai.controller;

import com.metiscom.metis.metis_ai.entity.ChatMessage;
import com.metiscom.metis.metis_ai.entity.ChatSession;
import com.metiscom.metis.metis_ai.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;

    @Autowired
    ChatService chatService;

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

    // Endpoint per creare una nuova sessione di chat
    @PostMapping("/sessions")
    public ResponseEntity<ChatSession> createSession(@RequestBody(required = false) Map<String, String> payload) {
        String title = payload != null ? payload.get("title") : null;
        ChatSession session = chatService.createNewSession(title);
        return ResponseEntity.ok(session);
    }

    // Chat persistente collegata alla sessione e al database
    @PostMapping
    public ResponseEntity<String> chat(@RequestBody ChatSessionRequest request) {
        String response = chatService.sendMessageWithSession(request.sessionId(), request.message());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long sessionId) {
        chatService.deleteChatSession(sessionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{sessionId}/messages")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable Long sessionId) {
        List<ChatMessage> history = chatService.getChatHistory(sessionId);
        return ResponseEntity.ok(history);
    }
}

record ChatSessionRequest(Long sessionId, String message) {}