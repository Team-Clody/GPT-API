package org.sopt.gptapi.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.gptapi.domain.user.User;
import org.sopt.gptapi.domain.user.UserRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserRetriever {

  private final UserRepository userRepository;

  public Mono<User> getUserById(Long userId) {
    return userRepository.findById(userId)
        .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
        .doOnNext(user -> log.info("User found: {}", user))
        .doOnError(e -> log.error("Error finding user: {}", e.getMessage(), e));
  }
}
