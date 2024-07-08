package org.sopt.gptapi.service;

import lombok.RequiredArgsConstructor;
import org.sopt.gptapi.common.dto.ErrorMessage;
import org.sopt.gptapi.common.dto.Prompt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final AsyncChatgptService asyncChatgptService;

    public Mono<String> getChatResponse(String content) {
        String message = Prompt.MESSAGE.getMessage() + content + "\n칭찬 :";
        return asyncChatgptService.sendMessage(message)
            .onErrorResume(HttpClientErrorException.BadRequest.class, e -> Mono.just(ErrorMessage.BAD_REQUEST.getMessage()))
            .onErrorResume(e -> Mono.just(ErrorMessage.GENERAL_ERROR.getMessage()));
    }
}
