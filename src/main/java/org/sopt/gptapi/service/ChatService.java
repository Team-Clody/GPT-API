package org.sopt.gptapi.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sopt.gptapi.config.YmlPropertyUtil;
import org.sopt.gptapi.service.reply.ReplyService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ChatService {

  public static final Logger logger = LoggerFactory.getLogger(ChatService.class);
  private final AsyncChatgptService asyncChatgptService;
  private final ReplyService replyService;
  private final YmlPropertyUtil ymlPropertyUtil;

  public Mono<String> getChatResponse(String content, Long userId, String createdDate) {
    String message = ymlPropertyUtil.getProperty("prompt")+ content + "\n칭찬 :";
    return asyncChatgptService.sendMessage(message)
        .flatMap(response -> {
          logger.info("ChatGPT response: {}", response);
          return replyService.saveReply(response, userId, createdDate);
        })
        .doOnError(BadRequest.class, e -> {
          logger.error("BadRequest error: {}", e.getMessage(), e);
        })
        .onErrorResume(BadRequest.class, e -> Mono.just("BadRequest error"))
        .doOnError(e -> {
          logger.error("General error: {}", e.getMessage(), e);
        })
        .onErrorResume(e -> Mono.just("General error"))
        .doOnSuccess(result -> logger.info("Final result: {}", result));
  }

}
