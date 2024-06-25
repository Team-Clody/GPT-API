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
            // ChatGPT 에게 질문을 던집니다.
            return chatgptService.sendMessage(prompt);
        } catch (HttpClientErrorException.TooManyRequests e) {
            // 쿼터 초과 시 처리
            return "현재 요청이 너무 많습니다. 잠시 후 다시 시도해 주세요.";
        } catch (Exception e) {
            // 기타 예외 처리
            return "오류가 발생했습니다. 다시 시도해 주세요.";
        }
    }
}