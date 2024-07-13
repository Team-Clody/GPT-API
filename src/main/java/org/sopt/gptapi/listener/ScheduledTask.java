package org.sopt.gptapi.listener;

import lombok.RequiredArgsConstructor;
import org.sopt.gptapi.dto.DiaryEntry;
import org.sopt.gptapi.service.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class ScheduledTask {

    private final ChatService chatService;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Scheduled(fixedRate = 7200000) // 2시간
    public void processDiaryEntries() {

        List<DiaryEntry> diaryEntries = fetchDiaryEntriesFromRedis();

        for (DiaryEntry entry : diaryEntries) {
            chatService.getChatResponse(entry.getContent(), entry.getUserId(), entry.getCreatedDate())
                .subscribe(response -> {
                    removeDiaryEntryFromRedis(entry);
                }, error -> {

                    logger.error("Error processing diary entry: {}", error.getMessage());
                });
        }
    }

    private List<DiaryEntry> fetchDiaryEntriesFromRedis() {
        Set<String> keys = redisTemplate.keys("diaryEntry:*");
        List<DiaryEntry> diaryEntries = new ArrayList<>();
        if (keys != null) {
            for (String key : keys) {
                DiaryEntry entry = (DiaryEntry) redisTemplate.opsForValue().get(key);
                if (entry != null) {
                    diaryEntries.add(entry);
                }
            }
        }
        return diaryEntries;
    }

    private void removeDiaryEntryFromRedis(DiaryEntry entry) {
        String key = "diaryEntry:" + entry.getUserId() + ":" + entry.getCreatedDate();
        redisTemplate.delete(key);
    }
}
