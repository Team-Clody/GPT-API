package org.sopt.gptapi.service;

import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import org.sopt.gptapi.common.dto.ErrorMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatgptService chatgptService;


    public String getChatResponse(String content) {
        try {
            return chatgptService.sendMessage(content);
        } catch (HttpClientErrorException.BadRequest e) {
            return ErrorMessage.BAD_REQUEST.getMessage();
        } catch (Exception e) {
            return ErrorMessage.GENERAL_ERROR.getMessage();
        }
    }

}