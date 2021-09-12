package com.linkedin.linkedinclone.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.linkedin.linkedinclone.dto.NewUserInfo;
import com.linkedin.linkedinclone.dto.SkillsDTO;
import com.linkedin.linkedinclone.enumerations.SkillType;
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
import com.linkedin.linkedinclone.repositories.SkillsAndExperienceRepository;
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
    private final SkillsAndExperienceRepository skillsAndExperienceRepository;

    @Autowired
    private final BCryptPasswordEncoder encoder;

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/signup", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> signup(@RequestPart("object") User user, @RequestPart(value = "imageFile") MultipartFile file) throws IOException {
        if(userRepository.findUserByUsername(user.getUsername()) == null) {
            if (user.getPassword().equals(user.getPasswordConfirm())) {
                user.setPassword(encoder.encode(user.getPassword()));
                Set<Role> roles = new HashSet<>();
                Role r = roleRepository.findByName(RoleType.PROFESSIONAL);
                roles.add(r);
                user.setRoles(roles);
                if(file!=null){
                    Picture pic = new Picture(file.getOriginalFilename() ,file.getContentType() ,compressBytes(file.getBytes()));
                    pic.setCompressed(true);
                    user.setProfilePicture(pic);
                    System.out.println(pic);
                    System.out.println("> Picture saved");
                }
                userRepository.save(user);
                System.out.println("> New user signed up");
            } else
                return ResponseEntity
                        .badRequest()
                        .body("{\"timestamp\": " + "\"" + new Date().toString() + "\","
                                + "\"status\": 400, "
                                + "\"error\": \"Bad Request\", "
                                + "\"message\": \"Passwords do not match!\"}"
                        );
        } else
            return ResponseEntity
                    .badRequest()
                    .body("{\"timestamp\": " + "\"" + new Date().toString() + "\","
                            + "\"status\": 400, "
                            + "\"error\": \"Bad Request\", "
                            + "\"message\": \"Email exists already!\"}"
                    );

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
    @GetMapping("/in/{id}")
    public User getProfile(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        Picture pic = user.getProfilePicture();
        if(pic != null && pic.isCompressed()){
            Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
            tempPicture.setCompressed(false);
            user.setProfilePicture(tempPicture);
        }
        return user;
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/user-profile/{otherId}")
    public User getPersonalProfile(@PathVariable Long id,@PathVariable Long otherId) {
        User user = userRepository.findById(otherId).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        Picture pic = user.getProfilePicture();
        if(pic != null && pic.isCompressed()){
            Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
            pic.setCompressed(false);
            user.setProfilePicture(tempPicture);
        }
        return user;
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/in/{id}/profile/new-info")
    public ResponseEntity informPersonalProfile(@PathVariable Long id, @RequestBody SkillsAndExperience skill) {
        System.out.println("\n\n> informPersonalProfile");
        System.out.println(skill);
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        Set<SkillsAndExperience> skillsList;
        if(skill.getType() == SkillType.EXPERIENCE){
            skill.setUserExp(user);
        } else if(skill.getType() == SkillType.SKILL) {
            skill.setUserSk(user);
        } else if(skill.getType() == SkillType.EDUCATION) {
            skill.setUserEdu(user);
        }

        skillsAndExperienceRepository.save(skill);
        System.out.println(user);
        System.out.println("> All changes made with success!");
        return ResponseEntity.ok("\"All changes made with success!\"");
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/in/{id}/editJob")
    public ResponseEntity editUserJob(@PathVariable Long id, @RequestBody User user) {

        System.out.println("\n\n> Edit User's Job");
        User newUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));

        if(user.getCurrentJob()!= null )
            newUser.setCurrentJob(user.getCurrentJob());
        if(user.getCurrentCompany()!= null)
            newUser.setCurrentCompany(user.getCurrentCompany());
        if(user.getCity()!= null)
            newUser.setCity(user.getCity());
        if(user.getWebsite()!= null )
            newUser.setWebsite(user.getWebsite());
        if(user.getGithub()!= null )
            newUser.setGithub(user.getGithub());
        if(user.getTwitter()!= null )
            newUser.setTwitter(user.getTwitter());

        userRepository.save(newUser);
        return ResponseEntity.ok("\"All changes made with success!\"");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/in/{id}/profile/{otherUserId}")
    public User showProfile(@PathVariable Long id, @PathVariable Long otherUserId) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        User userPreview = userRepository.findById(otherUserId).orElseThrow(() -> new UserNotFoundException("User with id "+otherUserId+"doesn't exist"));
        Picture pic = userPreview.getProfilePicture();
        if(pic != null && pic.isCompressed()){
            Picture tempPicture = new Picture(pic.getId(),pic.getName(),pic.getType(),decompressBytes(pic.getBytes()));
            userPreview.setProfilePicture(tempPicture);
            pic.setCompressed(false);
        }
        return userPreview;
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/in/{id}/settings/change-password")
    public ResponseEntity changePassword(@PathVariable Long id , @RequestBody NewUserInfo pwdDetails) {
        if (!pwdDetails.getNewPassword().equals(pwdDetails.getPasswordConfirm())) {
            System.out.println("\"Passwords do not match!\"");
            return ResponseEntity
                    .badRequest()
                    .body("{\"timestamp\": " + "\"" + new Date().toString()+ "\","
                            + "\"status\": 400, "
                            + "\"error\": \"Bad Request\", "
                            + "\"message\": \"Passwords do not match!\", "
                            + "\"path\": \"/users/"+ id.toString() +"/passwordchange\"}"
                    );
        }
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));
        if(!encoder.matches(pwdDetails.getCurrentPassword(),user.getPassword())){
            System.out.println("\"Wrong password\"");
            return ResponseEntity
                    .badRequest()
                    .body("{\"timestamp\": " + "\"" + new Date().toString()+ "\","
                            + "\"status\": 400, "
                            + "\"error\": \"Bad Request\", "
                            + "\"message\": \"Wrong password!\", "
                            + "\"path\": \"/users/"+ id.toString() +"/passwordchange\"}"
                    );
        }

        user.setPassword(encoder.encode(pwdDetails.getNewPassword()));
        userRepository.save(user);
        System.out.println("\"Password Changed!\"");
        return ResponseEntity.ok("\"Password Changed!\"");
    }


    @CrossOrigin(origins = "*")
    @PutMapping("/in/{id}/settings/change-username")
    public ResponseEntity changeUserName(@PathVariable Long id , @RequestBody NewUserInfo details) {
        String token = null;
        User existingUser = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+"doesn't exist"));

        if(!encoder.matches(details.getCurrentPassword(),existingUser.getPassword())){
            System.out.println("\"Wrong password\"");
            return ResponseEntity
                    .badRequest()
                    .body("{\"timestamp\": " + "\"" + new Date().toString()+ "\","
                            + "\"status\": 400, "
                            + "\"error\": \"Bad Request\", "
                            + "\"message\": \"Wrong password!\", "
                            + "\"path\": \"/users/"+ id.toString() +"/passwordchange\"}"
                    );
        }
        token = JWT.create()
                .withSubject(details.getNewUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        token = SecurityConstants.TOKEN_PREFIX + token;
        existingUser.setUsername(details.getNewUsername());
        userRepository.save(existingUser);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(SecurityConstants.HEADER_STRING, token);
        return ResponseEntity.ok().headers(responseHeaders).body("\"Successful edit!\"");
    }

}
