package org.sopt.gptapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryEntry {
    private String content;
    private Long userId;
    private String createdDate;
}
