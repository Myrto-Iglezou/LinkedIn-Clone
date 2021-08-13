package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.Chat;
import com.linkedin.linkedinclone.model.Job;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.ChatRepository;
import com.linkedin.linkedinclone.repositories.MessageRepository;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@AllArgsConstructor
public class ChatController {

    @Autowired
    UserService userService;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/chats")
    public Set<Chat> getAllChats(@PathVariable Long id) {
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        return currentUser.getChats();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/chat/{otherUserId}")
    public Chat newJob(@PathVariable Long id, @PathVariable Long otherUserId) {
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        User chatUser = userRepository.findById(otherUserId).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));

        Chat chatFound = null;
        for(Chat chat: currentUser.getChats()){
            for(User cuser: chat.getUsers()){
                if(cuser == chatUser){
                    chatFound = chat;
                    break;
                }
                if(chatFound!=null)
                    break;
            }
        }

        /*
        Chat chat = chatRepository.findChatByUsers(id,otherUserId);
*/
        return chatFound;
    }
}


