package org.sopt.gptapi.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.gptapi.common.dto.ErrorMessage;

import org.sopt.gptapi.dto.Prompt;
import org.sopt.gptapi.service.reply.ReplyService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ChatService {

  public static final Logger logger = LoggerFactory.getLogger(ChatService.class);
  private final AsyncChatgptService asyncChatgptService;
  private final ReplyService replyService;

  public Mono<String> getChatResponse(String content, Long userId, String createdDate) {
    String message = Prompt.MESSAGE.getMessage() + content + "\n칭찬 :";
    return asyncChatgptService.sendMessage(message)
        .flatMap(response -> replyService.saveReply(response, userId, createdDate))
        .onErrorResume(WebClientResponseException.BadRequest.class, e -> {
          logger.error("BadRequest error: {}", ErrorMessage.BAD_REQUEST.getMessage(), e);
          return Mono.just(ErrorMessage.BAD_REQUEST.getMessage());
        })
        .onErrorResume(e -> {
          logger.error("General error: {}", ErrorMessage.GENERAL_ERROR.getMessage(), e);
          return Mono.just(ErrorMessage.GENERAL_ERROR.getMessage());
        });
  }

//  public Mono<String> saveReply(String content, Long userId, String createdDate) {
//    return userService.getUserById(userId)
//        .flatMap(user -> {
//          Reply reply = Reply.createDiary(content, false, LocalDateTime.parse(createdDate), user);
//          return replyRepository.save(reply)
//              .thenReturn(content);
//        });
//  }

//  public Mono<String> saveReply(String content, Long userId, String createdDate) {
//    return userRepository.findById(userId)
//        .switchIfEmpty(Mono.error(new RuntimeException("User not found")))
//        .flatMap(user -> Mono.fromCallable(() -> {
//          Reply reply = Reply.createDiary(content, false, LocalDateTime.parse(createdDate), user);
//          replyRepository.save(reply);
//          return content;
//        }).subscribeOn(Schedulers.boundedElastic()));
//  }
}
