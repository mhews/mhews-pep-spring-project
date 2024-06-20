package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateAccountException;
import com.example.exception.InvalidFormatException;
import com.example.exception.NonexistantMessageException;
import com.example.exception.NonexistantUserException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
	AccountService accountService;
    
    @Autowired
    MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody Account newUser) {
        try {
            return ResponseEntity.status(200).body(accountService.register(newUser));
        }
        catch (DuplicateAccountException e){
            return ResponseEntity.status(409).body("Username already exists");
        }
        catch (InvalidFormatException e){
            return ResponseEntity.status(400).body("Invalid format");
        }
        
    }
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account account) {
        Account lookup = accountService.login(account);
        if (lookup != null){
            return ResponseEntity.status(200).body(lookup);
        }
        else{
            return ResponseEntity.status(401).body("Account not found");
        }
    }
    @PostMapping("/messages")
    public ResponseEntity createMessage(@RequestBody Message message) {
        try{
            return ResponseEntity.status(200).body(messageService.createMessage(message));
        }
        catch (InvalidFormatException e){
            return ResponseEntity.status(400).body("Invalid format");
        }
        catch (NonexistantUserException e){
            return ResponseEntity.status(400).body("User not found");
        }
    }
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity deleteMessage(@PathVariable int message_id) {
        long deletedCount = messageService.deleteMessage(message_id);
        if (deletedCount == 0){
            return ResponseEntity.status(200).body("");
        }
        else{
            return ResponseEntity.status(200).body(deletedCount);
        }
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity updateMessage(@PathVariable int message_id, @RequestBody Message message) {
        try{
            return ResponseEntity.status(200).body(messageService.updateMessage(message_id, message.getMessageText()));
        }
        catch (InvalidFormatException e){
            return ResponseEntity.status(400).body("Invalid format");
        }
        catch (NonexistantMessageException e){
            return ResponseEntity.status(400).body("Message not found");
        }
    }

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity getAccountMessages(@PathVariable int account_id) {
        return ResponseEntity.status(200).body(messageService.getAccountMessages(account_id));
    }

    @GetMapping("/messages")
    public ResponseEntity getMessages() {
        return ResponseEntity.status(200).body(messageService.getMessages());
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity getMessageById(@PathVariable int message_id) {
        return ResponseEntity.status(200).body(messageService.getMessageById(message_id));
    }
}
