package com.linkedin.linkedinclone.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.linkedin.linkedinclone.enumerations.RoleType;
import com.linkedin.linkedinclone.exceptions.EmailExistsAlreadyException;
import com.linkedin.linkedinclone.exceptions.PasswordsNotSameException;
import com.linkedin.linkedinclone.model.Picture;
import com.linkedin.linkedinclone.model.Role;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.security.SecurityConstants;
import com.linkedin.linkedinclone.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.linkedin.linkedinclone.utils.PictureSave.compressBytes;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Autowired
    private final BCryptPasswordEncoder encoder;

    @CrossOrigin(origins = "*") // CrossOrigin: For connecting with angular
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> all() {
        return userRepository.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> signup(@RequestPart("object") User user, @RequestPart("imageFile") MultipartFile file) throws IOException {

        if(userRepository.findUserByUsername(user.getUsername()) == null) {
            if (!user.getPassword().equals(user.getPasswordConfirm())) {
                user.setPassword(encoder.encode(user.getPassword()));
                Role role = roleRepository.findById(RoleType.PROFESSIONAL).orElseThrow(() -> new RuntimeException("Role not found"));
                Set<Role> roles = user.getRoles();
                roles.add(role);
                user.setRoles(roles);
                Picture pic = new Picture(file.getOriginalFilename() ,file.getContentType() ,compressBytes(file.getBytes()));
                user.setProfilePicture(pic);
                userRepository.save(user);
            } else
                throw new PasswordsNotSameException();
        } else
            throw new EmailExistsAlreadyException(user.getUsername());

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        responseHeaders.set("Content-Type","application/json");
        user.setPassword(null);
        return ResponseEntity.ok().headers(responseHeaders).body(user);
    }



}
