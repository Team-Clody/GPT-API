package org.sopt.gptapi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum WarningMessage {

    WARNING_MESSAGE("안녕하세요! 저는 감사일기에 대해 칭찬을 해드리는 클로디입니다. " +
            "사용해주셔서 감사합니다. 다만, 부적절한 언어가 감지되었습니다. " +
            "긍정적인 표현으로 감사일기를 작성해주시면 더 나은 피드백을 드릴 수 있습니다. 감사합니다!")
    ;

    private final String message;
}
