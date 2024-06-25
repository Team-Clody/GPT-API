package org.sopt.gptapi.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatgptService chatgptService;

    @Cacheable("chatResponses")
    public String getChatResponse(String prompt) {
        try {

            return chatgptService.sendMessage(prompt);
        } catch (HttpClientErrorException.TooManyRequests e) {

            return "The request rate is too high. Please try again later.";
        } catch (HttpClientErrorException.BadRequest e) {

            return "Bad request. Please check the endpoint.";
        } catch (Exception e) {

            return "An error occurred. Please try again.";
        }
    }

}