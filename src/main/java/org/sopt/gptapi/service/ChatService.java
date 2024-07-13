package org.sopt.gptapi.service;

import com.vane.badwordfiltering.BadWordFiltering;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.gptapi.common.dto.ErrorMessage;
import org.sopt.gptapi.common.dto.Prompt;
import org.sopt.gptapi.common.dto.WarningMessage;
import org.sopt.gptapi.domain.reply.Reply;
import org.sopt.gptapi.domain.reply.ReplyRepository;
import org.sopt.gptapi.domain.user.User;
import org.sopt.gptapi.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final AsyncChatgptService asyncChatgptService;
    private final BadWordFiltering filtering;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    public static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    public Mono<String> getChatResponse(String content, Long userId, String createdDate) {
        boolean containsBadWords = filtering.check(content);

        if (containsBadWords) {
            logger.warn("Inappropriate language detected: {}", content);
            return Mono.just(WarningMessage.WARNING_MESSAGE.getMessage());
        } else {
            String message = Prompt.MESSAGE.getMessage() + content + "\n칭찬 :";
            return asyncChatgptService.sendMessage(message)
                .flatMap(response -> saveReply(response, userId, createdDate))
                .onErrorResume(WebClientResponseException.BadRequest.class, e -> {
                    logger.error("BadRequest error: {}", ErrorMessage.BAD_REQUEST.getMessage(), e);
                    return Mono.just(ErrorMessage.BAD_REQUEST.getMessage());
                })
                .onErrorResume(e -> {
                    logger.error("General error: {}", ErrorMessage.GENERAL_ERROR.getMessage(), e);
                    return Mono.just(ErrorMessage.GENERAL_ERROR.getMessage());
                });
        }
    }

    private Mono<String> saveReply(String content, Long userId, String createdDate) {
        return Mono.fromCallable(() -> {
            User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
            Reply reply = new Reply();
            reply.setContent(content);
            reply.setIsRead(false);
            reply.setCreatedDate(LocalDateTime.parse(createdDate));
            reply.setUser(user);
            replyRepository.save(reply);
            return content;
        });
    }
}
