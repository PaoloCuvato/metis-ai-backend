package com.metiscom.metis.metis_ai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatModel chatModel;
    private final ChatClient chatClient;

    public ChatService(ChatModel chatModel, ChatClient.Builder chatClientBuilder) {
        this.chatModel = chatModel;
        this.chatClient = chatClientBuilder.build();
    }

    public String sendMessage(String message) {
        return chatModel.call(message);
    }

    public String callAi(String message) {
        return this.chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}