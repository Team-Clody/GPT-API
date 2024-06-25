package org.sopt.gptapi.controller;


import com.google.common.util.concurrent.RateLimiter;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.gptapi.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/")
public class testController {

    private final ChatService chatService;
    private final ChatgptService chatgptService;

    // 초당 1개의 요청만 허용
    private final RateLimiter rateLimiter = RateLimiter.create(1.0);

    // chat-gpt와 간단한 채팅 서비스 소스
    @PostMapping("chat-gpt")
    public String test(@RequestBody String question) {
        // 요청 빈도 조절
        if (rateLimiter.tryAcquire()) {
            return chatService.getChatResponse(question);
        } else {
            return "요청이 너무 빈번합니다. 잠시 후 다시 시도해 주세요.";
        }
    }
}