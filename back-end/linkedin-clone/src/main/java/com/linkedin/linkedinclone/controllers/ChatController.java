package com.linkedin.linkedinclone.controllers;

import com.linkedin.linkedinclone.repositories.ChatRepository;
import com.linkedin.linkedinclone.repositories.MessageRepository;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ChatController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

}


