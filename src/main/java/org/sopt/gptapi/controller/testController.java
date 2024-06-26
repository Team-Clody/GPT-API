package org.sopt.gptapi.controller;


import com.google.common.util.concurrent.RateLimiter;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.gptapi.common.dto.ErrorMessage;
import org.sopt.gptapi.dto.UserRequest;
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


    @PostMapping("chat-gpt")
    public String handleChatRequest(
        @RequestBody UserRequest userRequest
    ) {
        String content = userRequest.getContent();
        try {
            return chatService.getChatResponse(content);
        }catch (Exception e){
            log.error("Error during processing: {}",e.getMessage());
            return "An error has occurred. Please try again.";
        }


    }
}