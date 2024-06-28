package io.github.flashvayne.chatgpt.service.impl;

import io.github.flashvayne.chatgpt.dto.ChatRequest;
import io.github.flashvayne.chatgpt.dto.ChatResponse;
import io.github.flashvayne.chatgpt.dto.Choice;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatRequest;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatResponse;
import io.github.flashvayne.chatgpt.dto.chat.MultiResponseChoice;
import io.github.flashvayne.chatgpt.dto.image.ImageData;
import io.github.flashvayne.chatgpt.dto.image.ImageFormat;
import io.github.flashvayne.chatgpt.dto.image.ImageRequest;
import io.github.flashvayne.chatgpt.dto.image.ImageResponse;
import io.github.flashvayne.chatgpt.exception.ChatgptException;
import io.github.flashvayne.chatgpt.property.ChatgptProperties;
import io.github.flashvayne.chatgpt.service.ChatgptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class AsyncChatgptService implements ChatgptService {
    private static final Logger log = LoggerFactory.getLogger(AsyncChatgptService.class);
    protected final ChatgptProperties chatgptProperties;
    private final WebClient webClient;

    public AsyncChatgptService(ChatgptProperties chatgptProperties) {
        this.chatgptProperties = chatgptProperties;
        this.webClient = WebClient.builder()
            .baseUrl(chatgptProperties.getUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + chatgptProperties.getApiKey())
            .build();
    }

    public Mono<String> sendMessage(String message) {
        ChatRequest chatRequest = new ChatRequest(
            this.chatgptProperties.getModel(),
            message,
            this.chatgptProperties.getMaxTokens(),
            this.chatgptProperties.getTemperature(),
            this.chatgptProperties.getTopP()
        );
        return sendChatRequest(chatRequest)
            .map(chatResponse -> chatResponse.getChoices().get(0).getText());
    }

    public Mono<ChatResponse> sendChatRequest(ChatRequest chatRequest) {
        return webClient.post()
            .uri(chatgptProperties.getUrl())
            .body(BodyInserters.fromValue(chatRequest))
            .retrieve()
            .bodyToMono(ChatResponse.class)
            .doOnError(e -> log.error("Error during chat request", e));
    }


}