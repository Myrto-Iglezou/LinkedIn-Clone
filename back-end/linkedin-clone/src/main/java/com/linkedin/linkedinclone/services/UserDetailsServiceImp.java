package com.linkedin.linkedinclone.services;

import com.linkedin.linkedinclone.exceptions.EmailNotFoundException;
import com.linkedin.linkedinclone.model.Role;
import com.linkedin.linkedinclone.repositories.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private UserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws EmailNotFoundException {
        com.linkedin.linkedinclone.model.User applicationUser = applicationUserRepository.findUserByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : applicationUser.getRoles())
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName().name()));
        
        return new User(applicationUser.getUsername(), applicationUser.getPassword(), grantedAuthorities);
    }
}
