package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

    long deleteByMessageId(int messageId);

    Message findMessageByMessageId(int messageId);

    List<Message> findMessagesByPostedBy(int postedBy);
}
