package com.example.task.user.services.controller;

import com.example.task.user.services.config.JwtProvider;
import com.example.task.user.services.model.User;
import com.example.task.user.services.repo.UserRepository;
import com.example.task.user.services.requests.LoginRequests;
import com.example.task.user.services.response.AuthResponse;
import com.example.task.user.services.services.CustomUserServicesImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")

public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserServicesImp customUserServicesImp;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception {

        String email=user.getEmail();
        String password=user.getPassword();
        String fullName=user.getFullName();

        String role=user.getRole();

        User isemailExist=userRepository.findByEmail(email);
        if(isemailExist!=null){
            throw new Exception("email is already used with another account");

        }
        User createUser=new User();
        createUser.setEmail(email);
        createUser.setFullName(fullName);
        createUser.setRole(role);
        createUser.setPassword(passwordEncoder.encode(password));

        User saveUser=userRepository.save(createUser);
        Authentication authentication=new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token= JwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessages("Register success");
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }
    @PostMapping("/signin")
public ResponseEntity<AuthResponse>signin(@RequestBody LoginRequests loginRequests){
        String username=loginRequests.getEmail();
        String password=loginRequests.getPassword();
    System.out.println(username+"......"+password);
    Authentication authentication=authenticate(username,password);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token=JwtProvider.generateToken(authentication);
    AuthResponse authResponse=new AuthResponse();
    authResponse.setMessages("login successfully");
    authResponse.setJwt(token);
    authResponse.setStatus(true);
    return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
}

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails=customUserServicesImp.loadUserByUsername(username);
        System.out.println("sign userDetails...."+userDetails);

        if(userDetails == null){
            System.out.println("sign in useDetails -null "+userDetails);
            throw new BadCredentialsException("invalied username and password");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            System.out.println("sign in userDetails- password not match"+userDetails);
            throw new BadCredentialsException("invalid username and password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }


}
