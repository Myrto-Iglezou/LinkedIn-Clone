package com.linkedin.linkedinclone.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.linkedin.linkedinclone.dto.NewUserInfo;
import com.linkedin.linkedinclone.dto.SkillsDTO;
import com.linkedin.linkedinclone.model.SkillsAndExperience;
import com.linkedin.linkedinclone.enumerations.RoleType;
import com.linkedin.linkedinclone.exceptions.EmailExistsAlreadyException;
import com.linkedin.linkedinclone.exceptions.PasswordsNotSameException;
import com.linkedin.linkedinclone.exceptions.UserNotFoundException;
import com.linkedin.linkedinclone.exceptions.WrongPasswordException;
import com.linkedin.linkedinclone.model.Picture;
import com.linkedin.linkedinclone.model.Role;
import com.linkedin.linkedinclone.model.User;
import com.linkedin.linkedinclone.repositories.PictureRepository;
import com.linkedin.linkedinclone.repositories.RoleRepository;
import com.linkedin.linkedinclone.repositories.UserRepository;
import com.linkedin.linkedinclone.security.SecurityConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static com.linkedin.linkedinclone.utils.PictureSave.*;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PictureRepository pictureRepository;

    @Autowired
    private final BCryptPasswordEncoder encoder;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> signup(@RequestPart("object") User user, @RequestPart(value = "imageFile", required=false) MultipartFile file) throws IOException {
        if(userRepository.findUserByUsername(user.getUsername()) == null) {
            if (user.getPassword().equals(user.getPasswordConfirm())) {
                user.setPassword(encoder.encode(user.getPassword()));
                Set<Role> roles = new HashSet<>();
                Role r = roleRepository.findByName(RoleType.PROFESSIONAL);
                roles.add(r);
                user.setRoles(roles);
                if(file!=null){
                    Picture pic = new Picture(file.getOriginalFilename() ,file.getContentType() ,compressBytes(file.getBytes()));
                    user.setProfilePicture(pic);
                    pictureRepository.save(pic);
                }
                userRepository.save(user);
                System.out.println("> New user signed up");
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

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/in/{id}")
    public User getProfile(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        Picture pic = user.getProfilePicture();
        if(pic != null){
            Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
            user.setProfilePicture(tempPicture);
        }
        return user;
    }

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/in/{id}/profile")
    public User getPersonalProfile(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        Picture pic = user.getProfilePicture();
        if(pic != null){
            Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
            user.setProfilePicture(tempPicture);
        }
        return user;
    }

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @PutMapping("/in/{id}/profile/new-info")
    public ResponseEntity informPersonalProfile(@PathVariable Long id, @RequestBody SkillsDTO skills) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        user.getEducation().addAll(skills.getEducation());
        user.getWorkExperience().addAll(skills.getWorkExperience());
        user.getSkills().addAll(skills.getSkills());

        userRepository.save(user);
        System.out.println("> All changes made with success!");
        return ResponseEntity.ok("\"All changes made with success!\"");
    }

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @GetMapping("/in/{id}/profile/{otherUserId}")
    public User showProfile(@PathVariable Long id, @PathVariable Long otherUserId) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        User userPreview = userRepository.findById(otherUserId).orElseThrow(() -> new UserNotFoundException("User with id "+otherUserId+"doesn't exist"));
        Picture pic = userPreview.getProfilePicture();
        if(pic != null){
            Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
            userPreview.setProfilePicture(tempPicture);
        }
        return userPreview;
    }

    @CrossOrigin(origins = "*")
    //@PreAuthorize("hasRole('PROFESSIONAL')")
    @PutMapping("in/{id}/settings")
    public ResponseEntity changePasswordOrUsername(@RequestBody NewUserInfo info) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findUserByUsername(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());

        System.out.println("> changePasswordOrUsername");
        String responseMessage = new String();
        System.out.println(info.getCurrentPassword());
        System.out.println(user.getPassword());
        //User user  = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User with id "+id+"doesn't exist"));
        if(encoder.matches(info.getCurrentPassword(),user.getPassword())){
            if(info.getNewPassword()!=null){
                if (info.getNewPassword().equals(info.getPasswordConfirm())) {
                    user.setPassword(encoder.encode(info.getNewPassword()));
                    responseMessage += "Password updated\n";
                    System.out.println("Password updated");
                }
            }
            if(info.getNewUsername()!=null){
                user.setUsername(info.getNewUsername());
                responseMessage += "Username updated\n";
            }
            userRepository.save(user);
        }else
            throw new WrongPasswordException();
        System.out.println("> All changes made with success!");
        return ResponseEntity.ok("\"All changes made with success!\"");
    }
}
