package ru.xfneo.twitter.repos;

import org.springframework.data.repository.CrudRepository;
import ru.xfneo.twitter.domain.Message;

import java.util.List;
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
