package org.sopt.gptapi.service;

import io.github.flashvayne.chatgpt.dto.chat.MultiChatMessage;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatRequest;
import io.github.flashvayne.chatgpt.dto.chat.MultiChatResponse;
import io.github.flashvayne.chatgpt.dto.image.ImageFormat;
import io.github.flashvayne.chatgpt.dto.image.ImageRequest;
import io.github.flashvayne.chatgpt.dto.image.ImageResponse;
import io.github.flashvayne.chatgpt.dto.image.ImageSize;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AsyncChatgptService {
    Mono<String> sendMessage(String message);
    Mono<MultiChatResponse> sendChatRequest(MultiChatRequest multiChatRequest);
    Mono<String> multiChat(List<MultiChatMessage> messages);
    Mono<MultiChatResponse> multiChatRequest(MultiChatRequest multiChatRequest);
    Mono<String> imageGenerate(String prompt);
    Mono<List<String>> imageGenerate(String prompt, Integer n, ImageSize size, ImageFormat format);
    Mono<ImageResponse> imageGenerateRequest(ImageRequest imageRequest);
}
