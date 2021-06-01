package com.flightapp.service;

import com.flightapp.model.ConfirmationToken;
import com.flightapp.model.ExecutiveUser;
import com.flightapp.model.Role;
import com.flightapp.repository.ConfirmationTokenRepository;
import com.flightapp.repository.RoleRepository;
import com.flightapp.repository.ExecutiveUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private ExecutiveUserRepository userRepository;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void save(ExecutiveUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("EXECUTIVEUSER");
        Set<Role> roleSet = new HashSet<Role>();
        roleSet.add(userRole);
        user.setRoles(roleSet);
        userRepository.save(user);
    }

    @Override
    public ExecutiveUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public ExecutiveUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(ConfirmationToken token) {

        // Save it
        confirmationTokenRepository.save(token);
    }

    @Override
    public ExecutiveUser isValidToken(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            if(!isTokenExpired(token.getCreatedDate()) && !token.isUsed()) {
                ExecutiveUser user = userRepository.findByEmail(token.getUser().getEmail());
                token.setUsed(true);
                confirmationTokenRepository.save(token);


                return user;
            }
        }
        return null;
    }

    @Override
    public void savePassword(String email, String password) {
        ExecutiveUser tokenUser = userRepository.findByEmail(email);
        tokenUser.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(tokenUser);
    }



    private boolean isTokenExpired(Date createdDate){
        Date now = new Date();

        long milliseconds1 = createdDate.getTime();
        long milliseconds2 = now.getTime();
        long diffMinutes = (milliseconds2 - milliseconds1)/ (60 * 1000);
        if(diffMinutes <= 20 ) return false;


        return true;
    }
}
