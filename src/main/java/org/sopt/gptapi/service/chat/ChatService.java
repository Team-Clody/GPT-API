package org.sopt.gptapi.service.chat;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.sopt.gptapi.config.PromptProperty;
import org.sopt.gptapi.service.AsyncChatgptService;
import org.sopt.gptapi.service.reply.ReplyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ChatService {

  public static final Logger logger = LoggerFactory.getLogger(ChatService.class);
  private final AsyncChatgptService asyncChatgptService;
  private final ReplyService replyService;
  private final PromptProperty promptProperty;

  public Mono<String> getChatResponse(String content, Long userId, String createdDate) {
    String message = promptProperty.getPrompt() + content + "\n칭찬 :";
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
