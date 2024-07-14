package org.sopt.gptapi.service.reply;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.sopt.gptapi.domain.reply.Reply;
import org.sopt.gptapi.domain.reply.ReplyRepository;
import org.sopt.gptapi.service.user.UserService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReplyService {

  private final ReplyRepository replyRepository;
  private final UserService userService;

  public Mono<String> saveReply(String content, Long userId, String createdDate) {
    return userService.getUserById(userId)
        .flatMap(user -> {
          Reply reply = Reply.createDiary(content, false, LocalDateTime.parse(createdDate), user.getId());
          return replyRepository.save(reply)
              .thenReturn(content);
        });
  }

}
