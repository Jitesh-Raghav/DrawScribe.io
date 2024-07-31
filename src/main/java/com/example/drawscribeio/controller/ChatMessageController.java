package com.example.drawscribeio.controller;

import com.example.drawscribeio.dto.ChatMessageDto;
import com.example.drawscribeio.entity.ChatMessage;
import com.example.drawscribeio.entity.GameSession;
import com.example.drawscribeio.entity.User;
import com.example.drawscribeio.repository.GameSessionRepository;
import com.example.drawscribeio.repository.UserRepository;
import com.example.drawscribeio.service.ChatMessageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameSessionRepository gameSessionRepository;

    @GetMapping("/{chatMessageId}")
    public ResponseEntity<ChatMessage> getChatMessageById(@PathVariable Long chatMessageId) {

        Optional<ChatMessage> chatMessageById= chatMessageService.getChatMessageById(chatMessageId);
        return new ResponseEntity<>(chatMessageById.get(), HttpStatus.OK);
    }

    @GetMapping
    public List<ChatMessage> getAllChatMessages(){
        return chatMessageService.getAllChatMessages();
    }

    @PostMapping
    public ResponseEntity<ChatMessageDto> createChatMessage(@RequestBody ChatMessageDto chatMessageDTO) {
        // Convert DTO to entity
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageText(chatMessageDTO.getMessageText());
        chatMessage.setTimestamp(chatMessageDTO.getTimestamp());

        // Call the service to save the entity
        ChatMessage savedChatMessage = chatMessageService.createChatMessage(chatMessage, chatMessageDTO.getUserId(), chatMessageDTO.getGameSessionId());

        // Convert the saved entity back to DTO
        ChatMessageDto savedChatMessageDTO = new ChatMessageDto();
        savedChatMessageDTO.setMessageId(savedChatMessage.getMessageId());
        savedChatMessageDTO.setMessageText(savedChatMessage.getMessageText());
        savedChatMessageDTO.setTimestamp(savedChatMessage.getTimestamp());
        savedChatMessageDTO.setUserId(savedChatMessage.getUser().getUserId());
        savedChatMessageDTO.setGameSessionId(savedChatMessage.getGameSession().getGame_sessionId());

        return new ResponseEntity<>(savedChatMessageDTO, HttpStatus.CREATED);
    }

}
