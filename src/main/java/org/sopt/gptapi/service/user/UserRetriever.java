package org.sopt.gptapi.service.user;

import lombok.RequiredArgsConstructor;
import org.sopt.gptapi.domain.user.User;
import org.sopt.gptapi.domain.user.UserRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserRetriever {

  private final UserRepository userRepository;

  public Mono<User> getUserById(Long userId) {
    return userRepository.findById(userId)
        .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
  }
}
