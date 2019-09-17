package ru.xfneo.twitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.xfneo.twitter.domain.User;
import ru.xfneo.twitter.domain.dto.MessageDto;
import ru.xfneo.twitter.repos.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Page<MessageDto> messageList(Pageable pageable, String filter, User user){
        if (filter != null && !filter.isEmpty()){
            return messageRepository.findByTag(filter, pageable, user);
        } else {
            return messageRepository.findAll(pageable, user);
        }
    }

    public Page<MessageDto> getMessageListForUser(Pageable pageable, User author, User user) {
        return messageRepository.findByUser(pageable, author, user);
    }
}
