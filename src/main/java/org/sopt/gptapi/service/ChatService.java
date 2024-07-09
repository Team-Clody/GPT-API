package org.sopt.gptapi.service;

import com.vane.badwordfiltering.BadWordFiltering;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.gptapi.common.dto.ErrorMessage;
import org.sopt.gptapi.common.dto.Prompt;
import org.sopt.gptapi.common.dto.WarningMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final AsyncChatgptService asyncChatgptService;

    BadWordFiltering filtering = new BadWordFiltering();
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);


    public Mono<String> getChatResponse(String content) {

        boolean containsBadWords = filtering.check(content);

        if (containsBadWords) {
            logger.warn("Inappropriate language detected: {}", content);
            return Mono.just(WarningMessage.WARNING_MESSAGE.getMessage());
        } else {
            String message = Prompt.MESSAGE.getMessage() + content + "\n칭찬 :";
            return asyncChatgptService.sendMessage(message)
                    .onErrorResume(HttpClientErrorException.BadRequest.class, e -> {
                        logger.error("BadRequest error: {}", ErrorMessage.BAD_REQUEST.getMessage(), e);
                        return Mono.just(ErrorMessage.BAD_REQUEST.getMessage());
                    })
                    .onErrorResume(e -> {
                        logger.error("General error: {}", ErrorMessage.GENERAL_ERROR.getMessage(), e);
                        return Mono.just(ErrorMessage.GENERAL_ERROR.getMessage());
                    });
        }
    }
}
