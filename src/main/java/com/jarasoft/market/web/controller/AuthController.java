package com.jarasoft.market.web.controller;

import com.jarasoft.market.domain.dto.AuthenticationRequest;
import com.jarasoft.market.domain.dto.AuthenticationResponse;
import com.jarasoft.market.domain.service.MarketUserDetailsService;
import com.jarasoft.market.web.security.JWTUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private MarketUserDetailsService marketUserDetailsService;
    private JWTUtil util;

    public AuthController(AuthenticationManager authenticationManager, MarketUserDetailsService marketUserDetailsService, JWTUtil util) {
        this.authenticationManager = authenticationManager;
        this.marketUserDetailsService = marketUserDetailsService;
        this.util = util;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> createToken(@RequestBody AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserDetails userDetails = marketUserDetailsService.loadUserByUsername(request.getUsername());
            String jwt = util.generateToken(userDetails);
            return new ResponseEntity<>(new AuthenticationResponse(jwt), HttpStatus.OK);
        }catch (BadCredentialsException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }
}
