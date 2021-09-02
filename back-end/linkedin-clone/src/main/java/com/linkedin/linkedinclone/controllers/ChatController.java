package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.model.*;
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

import static com.linkedin.linkedinclone.utils.PictureSave.decompressBytes;

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
        System.out.println("\n\n\n");
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        for(Chat c: currentUser.getChats())
            System.out.println(c);

        for(Chat c: currentUser.getChats()) {
            System.out.println(c);
            for(User u: c.getUsers()){
                Picture pic = u.getProfilePicture();
                if(pic != null){
                    if(pic.isCompressed()){
                        Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
                        pic.setCompressed(false);
                        u.setProfilePicture(tempPicture);
                    }
                }

            }

            for(Message m: c.getMessages()){
                User u = m.getUserMadeBy();
                Picture pic = u.getProfilePicture();
                if(pic != null){
                    if(pic.isCompressed()){
                        Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
                        pic.setCompressed(false);
                        u.setProfilePicture(tempPicture);
                    }
                }
            }
        }
        return currentUser.getChats();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/chat/{chatId}")
    public Chat getChat(@PathVariable Long id, @PathVariable Long chatId) {
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Chat chat = chatRepository.findById(chatId).orElseThrow(()->new UserNotFoundException("Chat with "+id+" not found"));
        return chat;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/in/{id}/chat/{chatId}/new-message")
    public ResponseEntity<?> newMessage(@PathVariable Long id, @PathVariable Long chatId, @RequestBody Message message) {
        User currentUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with "+id+" not found"));
        Chat chat = chatRepository.findById(chatId).orElseThrow(()->new UserNotFoundException("Chat with "+id+" not found"));
        message.setUserMadeBy(currentUser);
        message.setChat(chat);

        messageRepository.save(message);

        return ResponseEntity.ok(message);
    }

}


