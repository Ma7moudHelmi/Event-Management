package com.iti.eventmanagementbackend.service;

import com.iti.eventmanagementbackend.model.Users;
import com.iti.eventmanagementbackend.model.UserPrincipal;
import com.iti.eventmanagementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsServices implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        Users usersData = userRepository.findByEmail(email);

        if(usersData == null){
            throw new UsernameNotFoundException("email is incorrect");
        }

        return new UserPrincipal(usersData);
    }
}
