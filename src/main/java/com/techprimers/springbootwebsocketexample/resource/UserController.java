package com.techprimers.springbootwebsocketexample.resource;

import com.techprimers.springbootwebsocketexample.model.User;
import com.techprimers.springbootwebsocketexample.model.UserResponse;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
	 @Autowired
	 SimpMessagingTemplate template;

    @MessageMapping("/user")
    @SendTo("/topic/user")
    public UserResponse getUser(User user,Principal pri) {
        return new UserResponse("Hi " + user.getName()+pri.getName());
    }
    
    @MessageMapping("/users")
    public void getUsers(User user,Principal pri) {
    	System.out.println(user.getName()+"  "+pri.getName());
    	template.convertAndSend("/user/"+user.getName()+"/queue/private", new UserResponse("hi "+ pri.getName()));
    }
}
