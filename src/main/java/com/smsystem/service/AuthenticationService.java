package com.smsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smsystem.exception.UnauthorizedException;
import com.smsystem.model.User;
import com.smsystem.repository.UserRepository;
import com.smsystem.util.JwtUtil;
import com.smsystem.util.PasswordUtil;

@Service
public class AuthenticationService {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired UserRepository userRepository;
    



    public boolean validateToken(String token)  {
        return jwtUtil.validateToken(token);
    }
    
    
    public String getUsername(String token) {
        return jwtUtil.extractUsername(token);
    }
    
    
    public User authenticateDb(String username, String password) throws RuntimeException {
    	
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()&& PasswordUtil.matchesPassword(password,user.get().getPassword())) {
            return user.get();
        }
        throw new RuntimeException("Invalid credentials");
    }
    
    public User authenticateUser(String username, String role) throws UnauthorizedException {
    	  
   	 Optional<User> user = userRepository.findByUsername(username);
   	 
     if (user.isEmpty() || !user.get().getRole().equals(role)) {
         throw new UnauthorizedException("No Access provided");
     }

            return user.get();

   }
}

