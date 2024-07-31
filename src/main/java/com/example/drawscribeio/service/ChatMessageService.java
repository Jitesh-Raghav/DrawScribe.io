package com.example.drawscribeio.service;

import com.example.drawscribeio.dto.ChatMessageDto;
import com.example.drawscribeio.entity.ChatMessage;

import java.util.List;
import java.util.Optional;

public interface ChatMessageService {

    public ChatMessage createChatMessage(ChatMessage chatMessage, Long userId, Long gameSessionId);
    public Optional<ChatMessage> getChatMessageById(Long chatMessageId);
    public List<ChatMessage> getAllChatMessages();


}
