package com.example.drawscribeio.service.impl;

import com.example.drawscribeio.dto.ChatMessageDto;
import com.example.drawscribeio.entity.ChatMessage;
import com.example.drawscribeio.entity.GameSession;
import com.example.drawscribeio.entity.User;
import com.example.drawscribeio.repository.ChatMessageRepository;
import com.example.drawscribeio.repository.GameSessionRepository;
import com.example.drawscribeio.repository.UserRepository;
import com.example.drawscribeio.service.ChatMessageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Override
    public ChatMessage createChatMessage(ChatMessage chatMessage, Long userId, Long gameSessionId) {

        if (userId == null || gameSessionId == null) {
            throw new IllegalArgumentException("User ID and Game Session ID must not be null");
        }
//
//        User chatUser= chatMessage.getUser();
//        if(chatUser!=null){
//            chatUser.setChatMessages((List<ChatMessage>) chatMessage);
//        }
//
//        GameSession chatSession= chatMessage.getGameSession();
//        if(chatSession!=null){
//            chatSession.
//        }
//        return chatMessageRepository.save(chatMessage);

        // Fetch the user and game session from the database
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        GameSession gameSession = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new EntityNotFoundException("GameSession not found with id: " + gameSessionId));

        // Set the user and game session to the chat message
        chatMessage.setUser(user);
        chatMessage.setGameSession(gameSession);

        // Set the timestamp
        chatMessage.setTimestamp(LocalDateTime.now());

        // Save and return the chat message
        ChatMessage savedChatMessage = chatMessageRepository.save(chatMessage);
        return savedChatMessage;
    }

    @Override
    public Optional<ChatMessage> getChatMessageById(Long chatMessageId) {
        return chatMessageRepository.findById(chatMessageId);
    }

    @Override
    public List<ChatMessage> getAllChatMessages() {
        List<ChatMessage> chats=  chatMessageRepository.findAll();
        return chats;
    }
}
