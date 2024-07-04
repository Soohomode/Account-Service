package com.example.Account.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 일종의 설정 클래스
public class Account { // 자바 객체 클래스 처럼 보이지만 설정 클래스이다 테이블을 만드는것이다
    @Id // pk primary key
    @GeneratedValue
    private Long id;

    private String accountNumber;

    @Enumerated(EnumType.STRING) // enum 값의 문자열로 DB에 저장하기위해 디폴트는 숫자 1,2,3 ..
    private AccountStatus accountStatus; // ENUM
}
