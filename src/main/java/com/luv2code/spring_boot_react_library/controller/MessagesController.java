package com.luv2code.spring_boot_react_library.controller;

import com.luv2code.spring_boot_react_library.entity.Message;
import com.luv2code.spring_boot_react_library.requestmodels.AdminQuestionsRequest;
import com.luv2code.spring_boot_react_library.service.MessagesService;
import com.luv2code.spring_boot_react_library.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://luvtoread.vercel.app")
@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private final MessagesService messagesService;

    @Autowired
    public MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @PostMapping("/secure/add/message")
    public void postMessage(@RequestHeader(value = "Authorization") String token,
                            @RequestBody Message messageRequest) {
        String userMail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        messagesService.postMessage(messageRequest, userMail);
    }

    @PutMapping("/secure/admin/message")
    public void putMessage(@RequestHeader(value = "Authorization") String token,
                           @RequestBody AdminQuestionsRequest adminQuestionsRequest) throws Exception {
        String userMail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        /*
        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if(admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only.");
        }
         */
        messagesService.putMessage(adminQuestionsRequest, userMail);
    }
}
