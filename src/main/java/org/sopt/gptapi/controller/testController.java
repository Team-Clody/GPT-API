package org.sopt.gptapi.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.gptapi.dto.UserRequest;
import org.sopt.gptapi.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/")
public class testController {

    private final ChatService chatService;

    @PostMapping("chat-gpt")
    public Mono<String> handleChatRequest(
        @RequestBody UserRequest userRequest
    ) {
        String content = userRequest.getContent();
        return chatService.getChatResponse(content)
            .doOnError(e -> log.error("Error during processing: {}", e.getMessage()))
            .onErrorReturn("An error has occurred. Please try again.");
    }
}
