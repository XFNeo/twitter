package ru.xfneo.twitter.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.xfneo.twitter.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
