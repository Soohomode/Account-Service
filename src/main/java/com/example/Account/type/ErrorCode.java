package com.example.Account.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // description 이 들어간 생성자를 만들어준다
public enum ErrorCode {
    USER_NOT_FOUND("사용자가 없습니다."),
    MAX_ACCOUNT_PER_USER_10("사용자 최대 계좌는 10개입니다.");

    private final String description;
}
