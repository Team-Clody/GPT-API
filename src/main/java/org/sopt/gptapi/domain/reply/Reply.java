package org.sopt.gptapi.domain.reply;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("replies")
public class Reply {

  @Id
  private Long id;

  private String content;

  @Column("is_read")
  private Boolean isRead;

  @Column("diary_created_date")
  private LocalDateTime diaryCreatedDate;

  private Long userId;

  @Column("created_at")
  private LocalDateTime createdAt;

  @Column("updated_at")
  private LocalDateTime updatedAt;

  public Reply( String content, Boolean isRead, LocalDateTime diaryCreatedDate, Long userId,
      LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = null;
    this.content = content;
    this.isRead = isRead;
    this.diaryCreatedDate = diaryCreatedDate;
    this.userId = userId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  @Builder
  public static Reply createDiary(String content, Boolean isRead, LocalDateTime createdDate, Long userId) {
    return new Reply(null,content, isRead, createdDate, userId, LocalDateTime.now(), LocalDateTime.now());
  }
}
