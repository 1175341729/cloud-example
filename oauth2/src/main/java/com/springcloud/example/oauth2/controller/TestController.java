package com.springcloud.example.oauth2.controller;

import com.springcloud.example.oauth2.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/***
 *  @Author dengwei
 *  @Description: TODO
 *  @Date 2019/3/12 16:14
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/test1")
    public String test1(){
        return "test1";
    }

    @GetMapping("/login")
    public String login(String username, String password) {
        String token = null;
        try {
            token = null;
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            token = jwtTokenUtil.generateToken("dengwei");
        } catch (AuthenticationException e) {
            log.error("error",e);
            e.printStackTrace();
        }
        return token;
    }
}
