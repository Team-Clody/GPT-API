package org.sopt.gptapi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum WarningMessage {

    WARNING_MESSAGE("안녕! 이번 일기에 조금 부적절한 표현이 들어간 것 같아. "
        + "앞으로는 감사한 마음을 잘 담아서 일기를 써주면 내가 더 좋은 답변을 줄 수 있을 거야. "
        + "내일은 긍정적인 생각으로 하루를 시작할 수 있도록 네잎클로버🍀하나를 줄게, "
        + "또 언제든 나를 찾아줘! ")
    ;

    private final String message;
}
