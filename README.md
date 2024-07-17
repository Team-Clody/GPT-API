# 🍀 Clody
> ### 사용자의 감사일기에 AI(클로디)가 칭찬으로 답장해주는 서비스

**클로디**는
사용자의 감사일기에 AI 캐릭터가 칭찬으로 답장해주는 서비스입니다.

</div>

<br>

## 🧠 GPT-API
클로디의 **두뇌** 구현

=> 사용자가 감사일기를 작성하면, 이 일기를 GPT API를 통해 요청하여 칭찬 메시지를 생성하고 출력하도록 설계했습니다. 

[AI Brain of Clody ; Notion](https://petite-blizzard-e22.notion.site/f2ea976ea6044d05972f8a6ce27676f4?v=1bf109975be64b52a1f4f5c5d3984cb6)

</div>

<br>

## 📌GPT-API 브랜치 설명
AI 답장 기능의 성능을 높이기 위해 여러 방법으로 구현하였고, 가장 성능이 좋은 방법을 사용하였습니다.

* [Feat/#1](https://github.com/Yangdaehan/GPT-API/tree/Feat/%231) => 영어로 번역한 후 gpt에 되묻기 (사용 x)
* [Feat/#2](https://github.com/Yangdaehan/GPT-API/tree/Feat/%232) => Few shot skill을 이용해서 gpt에 요청할 때 칭찬의 예시도 함께 전송
* [Feat/#3](https://github.com/Yangdaehan/GPT-API/tree/Feat/%233) => WebClient 방식으로 외부 api 사용하기 
* [Feat/#5](https://github.com/Yangdaehan/GPT-API/tree/Feat/%235) => GPT 4.0으로 변경
* [Feat/#6](https://github.com/Yangdaehan/GPT-API/tree/Feat/%236) => #5(GPT4.0)로 부터 branch 생성. #2의 Few shot skill을 적용해서 여러 감사일기에도 독립된 칭찬이 나오게 구현
* [Feat/#8](https://github.com/Yangdaehan/GPT-API/tree/Feat/%238) => #3(WebClient)로 부터 branch 생성. 비속어 필터링 구현
* Feat/#9 => #8(비속어 필터링)로 부터 branch 생성. AMQP 구현을 위한 RabbitMQ 적용 (사용 x)
* [Feat/#10](https://github.com/Yangdaehan/GPT-API/tree/Feat/%2310) => Postgresql 데이터베이스에 칭찬 저장 **GPT 3.5 최종본**
* [Feat/#12](https://github.com/Yangdaehan/GPT-API/tree/Feat/%2312)=> **GPT 4.0 최종본**

</div>

<br>

## 🖥️ Foldering
```
.
├── main
│   ├── java
│   │   └── org
│   │       └── sopt
│   │           └── gptapi
│   │               ├── GptApiApplication.java
│   │               ├── common
│   │               │   └── dto
│   │               │       ├── ErrorMessage.java
│   │               │       └── WarningMessage.java
│   │               ├── config
│   │               │   ├── AsyncChatgptServiceImpl.java
│   │               │   ├── AsyncConfig.java
│   │               │   ├── PromptProperty.java
│   │               │   ├── RedisConfig.java
│   │               │   ├── ThreadPoolConfig.java
│   │               │   └── YamlPropertySourceFactory.java
│   │               ├── controller
│   │               │   └── TestController.java
│   │               ├── domain
│   │               │   ├── reply
│   │               │   │   ├── Reply.java
│   │               │   │   └── ReplyRepository.java
│   │               │   └── user
│   │               │       ├── Platform.java
│   │               │       ├── User.java
│   │               │       └── UserRepository.java
│   │               ├── dto
│   │               │   ├── DiaryEntry.java
│   │               │   └── UserRequest.java
│   │               ├── listener
│   │               │   ├── RedisLockService.java
│   │               │   └── RedisMessageListener.java
│   │               └── service
│   │                   ├── AsyncChatgptService.java
│   │                   ├── ChatService.java
│   │                   ├── dto
│   │                   │   └── DiaryListenedMessage.java
│   │                   ├── reply
│   │                   │   └── ReplyService.java
│   │                   └── user
│   │                       ├── UserRetriever.java
│   │                       └── UserService.java
│   └── resources
│       
│       
│       
│       
└── test
    
```

## Teck Stack ✨

| IDE | IntelliJ |
|:---|:---|
| Language | Java 21 |
| Framework | Spring Boot 3.3.1, Gradle |
| ORM | Spring R2DBC |
| Database | PostgreSQL, Redis |
| External | AWS EC2, AWS RDS, Nginx, Docker, Docker-Compose, FCM, Webflux |
| CI/CD | Github Action |
| API Docs | Notion, Swagger |
| Other Tool | Discord, Postman, Figma |
