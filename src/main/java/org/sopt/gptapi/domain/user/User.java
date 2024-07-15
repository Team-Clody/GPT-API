package org.sopt.gptapi.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    private Long id;

    private String platformID;

    private String platform;

    private String email;

    private String nickName;

    private boolean isDeleted;

    @Builder
    public User(String platformID, String platform, String email, String nickName, boolean isDeleted) {
        this.platformID = platformID;
        this.platform = platform;
        this.email = email;
        this.nickName = nickName;
        this.isDeleted = isDeleted;
    }
}
