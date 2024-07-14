package org.sopt.gptapi.config;

import io.github.flashvayne.chatgpt.dto.ChatRequest;
import io.github.flashvayne.chatgpt.dto.ChatResponse;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatRequest;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatResponse;
import io.github.flashvayne.chatgpt.dto.image.ImageFormat;
import io.github.flashvayne.chatgpt.dto.image.ImageRequest;
import io.github.flashvayne.chatgpt.dto.image.ImageResponse;
import io.github.flashvayne.chatgpt.dto.image.ImageSize;
import io.github.flashvayne.chatgpt.property.ChatgptProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sopt.gptapi.service.AsyncChatgptService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AsyncChatgptServiceImpl implements AsyncChatgptService {
    private static final Logger log = LoggerFactory.getLogger(AsyncChatgptServiceImpl.class);
    private final ChatgptProperties chatgptProperties;
    private final WebClient webClient;

    public AsyncChatgptServiceImpl(ChatgptProperties chatgptProperties) {
        this.chatgptProperties = chatgptProperties;
        this.webClient = WebClient.builder()
            .baseUrl(chatgptProperties.getUrl())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + chatgptProperties.getApiKey())
            .build();
    }

    @Override
    public Mono<String> sendMessage(String message) {
        ChatRequest chatRequest = new ChatRequest(
            this.chatgptProperties.getModel(),
            message,
            this.chatgptProperties.getMaxTokens(),
            this.chatgptProperties.getTemperature(),
            this.chatgptProperties.getTopP()
        );
        log.info("Sending chat message : {}", message);
        return sendChatRequest(chatRequest)
            .map(chatResponse -> chatResponse.getChoices().get(0).getText());
    }

    @Override
    public Mono<ChatResponse> sendChatRequest(ChatRequest chatRequest) {
        return webClient.post()
            .uri(chatgptProperties.getUrl())
            .body(BodyInserters.fromValue(chatRequest))
            .retrieve()
            .bodyToMono(ChatResponse.class)
            .doOnError(e -> log.error("Error during chat request", e));
    }

    @Override
    public Mono<String> multiChat(List<MultiChatMessage> messages) {
        MultiChatRequest multiChatRequest = new MultiChatRequest(
            this.chatgptProperties.getMulti().getModel(),
            messages,
            this.chatgptProperties.getMulti().getMaxTokens(),
            this.chatgptProperties.getMulti().getTemperature(),
            this.chatgptProperties.getMulti().getTopP()
        );
        return multiChatRequest(multiChatRequest)
            .map(multiChatResponse -> multiChatResponse.getChoices().get(0).getMessage().getContent());
    }

    @Override
    public Mono<MultiChatResponse> multiChatRequest(MultiChatRequest multiChatRequest) {
        return webClient.post()
            .uri(chatgptProperties.getMulti().getUrl())
            .body(BodyInserters.fromValue(multiChatRequest))
            .retrieve()
            .bodyToMono(MultiChatResponse.class)
            .doOnError(e -> log.error("Error during multi chat request", e));
    }

    @Override
    public Mono<String> imageGenerate(String prompt) {
        ImageRequest imageRequest = new ImageRequest(prompt, null, null, null, null);
        return imageGenerateRequest(imageRequest)
            .map(imageResponse -> imageResponse.getData().get(0).getUrl());
    }

    @Override
    public Mono<List<String>> imageGenerate(String prompt, Integer n, ImageSize size, ImageFormat format) {
        ImageRequest imageRequest = new ImageRequest(prompt, n, size.getSize(), format.getFormat(), null);
        return imageGenerateRequest(imageRequest)
            .map(imageResponse -> {
                List<String> list = new ArrayList<>();
                imageResponse.getData().forEach(imageData -> {
                    if (format.equals(ImageFormat.URL)) {
                        list.add(imageData.getUrl());
                    } else {
                        list.add(imageData.getB64Json());
                    }
                });
                return list;
            });
    }

    @Override
    public Mono<ImageResponse> imageGenerateRequest(ImageRequest imageRequest) {
        return webClient.post()
            .uri(chatgptProperties.getImage().getUrl())
            .body(BodyInserters.fromValue(imageRequest))
            .retrieve()
            .bodyToMono(ImageResponse.class)
            .doOnError(e -> log.error("Error during image generate request", e));
    }
}
