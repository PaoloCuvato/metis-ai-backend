package com.metiscom.metis.metis_ai.service;

import com.metiscom.metis.metis_ai.entity.ChatMessage;
import com.metiscom.metis.metis_ai.entity.ChatSession;
import com.metiscom.metis.metis_ai.repository.ChatMessageRepository;
import com.metiscom.metis.metis_ai.repository.ChatSessionRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatModel chatModel;
    private final ChatClient chatClient;

    @Autowired
    ChatSessionRepository chatSessionRepository;

    @Autowired
    ChatMessageRepository chatMessageRepository;

    public ChatService(ChatModel chatModel, ChatClient.Builder chatClientBuilder) {
        this.chatModel = chatModel;
        this.chatClient = chatClientBuilder.build();
    }

    public String sendMessage(String message) {
        return chatModel.call(message);
    }

    public String callAi(String message) {
        return this.chatClient.prompt()
                .system("Sei Metis AI, un assistente virtuale avanzato e specializzato al 100% nello sviluppo software " +
                        "e nella creazione di asset di ogni tipo (inclusi asset grafici, logiche di gioco, script, " +
                        "strutture dati, componenti UI e file di configurazione). " +
                        "Il tuo compito è assistermi in modo tecnico, diretto, operativo e senza fronzoli. " +
                        "Fornisci sempre codice pulito, best practices e soluzioni pronte all'uso per il progetto.")
                .user(message)
                .call()
                .content();
    }

    // Crea una nuova sessione di chat
    public ChatSession createNewSession(String title) {
        ChatSession session = new ChatSession();
        session.setTitle(title != null ? title : "Nuova Chat");
        return chatSessionRepository.save(session);
    }

    // Gestisce l'invio del messaggio associato a una sessione salvando utente e AI sul DB
    public String sendMessageWithSession(Long sessionId, String userMessage) {
        ChatSession session = chatSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Sessione non trovata con ID: " + sessionId));

        // Salva messaggio utente
        ChatMessage userMsg = new ChatMessage();
        userMsg.setSession(session);
        userMsg.setRole("user");
        userMsg.setContent(userMessage);
        chatMessageRepository.save(userMsg);

        // Chiama l'AI
        String aiResponse = callAi(userMessage);

        // Salva risposta AI
        ChatMessage aiMsg = new ChatMessage();
        aiMsg.setSession(session);
        aiMsg.setRole("assistant");
        aiMsg.setContent(aiResponse);
        chatMessageRepository.save(aiMsg);

        return aiResponse;
    }

    public void deleteChatSession(Long sessionId) {
        chatSessionRepository.deleteById(sessionId);
    }

    public List<ChatMessage> getChatHistory(Long sessionId) {
        return chatMessageRepository.findBySessionIdOrderByTimestampAsc(sessionId);
    }
}