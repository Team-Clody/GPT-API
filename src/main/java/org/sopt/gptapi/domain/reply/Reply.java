package org.sopt.gptapi.domain.reply;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("replies")
public class Reply {

  @Id
  private Long id;

  private String content;

  private Boolean isRead;

  private LocalDateTime createdDate;

  private Long userId;

  @Builder
  public Reply(String content, Boolean isRead, LocalDateTime createdDate, Long userId) {
    this.content = content;
    this.isRead = isRead;
    this.createdDate = createdDate;
    this.userId = userId;
  }

  public static Reply createDiary(String content, Boolean isRead, LocalDateTime createdDate, Long userId) {
    return new Reply(content, isRead, createdDate, userId);
  }
}
