package org.sopt.gptapi.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.gptapi.common.dto.ErrorMessage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@Service
public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private final ChatgptService chatgptService;

    @Cacheable("chatResponses")
    public String getChatResponse(String prompt) {
        try {

            return chatgptService.sendMessage(prompt);
        } catch (HttpClientErrorException.TooManyRequests e) {

            log.error("Too many requests error: {}", e.getMessage());
            return ErrorMessage.TOO_MANY_REQUEST.getMessage();
        } catch (HttpClientErrorException.BadRequest e) {

            log.error("Bad request error: {}", e.getMessage());
            return ErrorMessage.BAD_REQUEST.getMessage();
        } catch (Exception e) {

            log.error("General error: {}", e.getMessage());
            return ErrorMessage.GENERAL_ERROR.getMessage();
        }
    }

}