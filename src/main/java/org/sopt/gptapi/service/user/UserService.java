package org.sopt.gptapi.service.user;

import lombok.RequiredArgsConstructor;
import org.sopt.gptapi.domain.user.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRetriever userRetriever;

  public Mono<User> getUserById(Long userId) {
    return userRetriever.getUserById(userId);
  }

}
