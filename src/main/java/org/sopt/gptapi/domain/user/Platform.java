package org.sopt.gptapi.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Platform {

  KAKAO("kakao"),
  GOOGLE("google"),
  APPLE("apple");

  private final String name;

//  public static Platform fromString(String name) {
//    return Arrays.stream(Platform.values())
//        .filter(p -> p.name.equals(name))
//        .findFirst()
//        .orElseThrow(() -> new InternalServerException(ErrorType.INTERNAL_SERVER_ERROR));
//  }
}
