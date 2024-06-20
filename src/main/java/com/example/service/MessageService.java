package com.example.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.exception.InvalidFormatException;
import com.example.exception.NonexistantMessageException;
import com.example.exception.NonexistantUserException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Transactional
@Service
public class MessageService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) throws InvalidFormatException, NonexistantUserException{
        if (message.getMessageText().length() == 0 || message.getMessageText().length() > 255){
            throw new InvalidFormatException();
        }
        if (accountRepository.findAccountByAccountId(message.getPostedBy()) == null){
            throw new NonexistantUserException();
        }
        else{
            return messageRepository.save(message);
        }
    }

    public long deleteMessage(int messageId){
        return messageRepository.deleteByMessageId(messageId);
    }

    public int updateMessage(int messageId, String messageText) throws InvalidFormatException, NonexistantMessageException{
        if (messageText.length() == 0 || messageText.length() > 255){
            throw new InvalidFormatException();
        }
        Message message = messageRepository.findMessageByMessageId(messageId);
        if (message == null){
            throw new NonexistantMessageException();
        }
        message.setMessageText(messageText);
        messageRepository.save(message);
        return 1;
    }

    public List<Message> getAccountMessages(int account_id){
        return messageRepository.findMessagesByPostedBy(account_id);
    }

    public List<Message> getMessages(){
        return messageRepository.findAll();
    }

    public Message getMessageById(int messageId){
        return messageRepository.findMessageByMessageId(messageId);
    }
}
