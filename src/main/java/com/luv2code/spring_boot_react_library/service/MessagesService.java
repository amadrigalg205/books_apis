package com.luv2code.spring_boot_react_library.service;

import com.luv2code.spring_boot_react_library.dao.MessageRepository;
import com.luv2code.spring_boot_react_library.entity.Message;
import com.luv2code.spring_boot_react_library.requestmodels.AdminQuestionsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class MessagesService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessagesService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void postMessage(Message messageRequest, String userEmail) {
        Message message = new Message(messageRequest.getTitle(), messageRequest.getQuestion());
        message.setUserEmail(userEmail);
        messageRepository.save(message);
    }

    public void putMessage(AdminQuestionsRequest adminQuestionsRequest, String userEmail) throws Exception {
        Optional<Message> message = messageRepository.findById(adminQuestionsRequest.getId());
        if(!message.isPresent()) {
            throw new Exception("Message not found");
        }

        message.get().setAdminEmail(userEmail);
        message.get().setResponse(adminQuestionsRequest.getResponse());
        message.get().setClosed(true);
        messageRepository.save(message.get());
    }
}
