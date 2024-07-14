package org.sopt.gptapi.domain.reply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.gptapi.domain.user.User;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "replies")
public class Reply {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "content")
  private String content;

  @Column(name = "is_read")
  private Boolean isRead;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public Reply(String content, Boolean isRead, LocalDateTime createdDate, User user) {
    this.content = content;
    this.isRead = isRead;
    this.createdDate = createdDate;
    this.user = user;
  }

  public static Reply createDiary(String content, Boolean isRead, LocalDateTime createdDate,
      User user) {
    return new Reply(content, isRead, createdDate, user);
  }
}
