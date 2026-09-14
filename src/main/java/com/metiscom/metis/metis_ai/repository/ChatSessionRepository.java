package com.metiscom.metis.metis_ai.repository;

import com.metiscom.metis.metis_ai.entity.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {
}
