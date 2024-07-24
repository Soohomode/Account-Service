package com.example.Account.domain;

import com.example.Account.exception.AccountException;
import com.example.Account.type.AccountStatus;
import com.example.Account.type.ErrorCode;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity // 일종의 설정 클래스
@EntityListeners(AuditingEntityListener.class)
public class Account extends BaseEntity { // 자바 객체 클래스 처럼 보이지만 설정 클래스이다 테이블을 만드는것이다

    @ManyToOne
    private AccountUser accountUser;

    private String accountNumber;

    @Enumerated(EnumType.STRING) // enum 값의 문자열로 DB에 저장하기위해 디폴트는 숫자 0,1,2,3 ..
    private AccountStatus accountStatus; // ENUM

    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;

    // 중요한 데이터를 변경하는 로직은 객체 안에서 직접 수행 할수있도록 하는 편이 안전하다.
    public void useBalance(Long amount) {
        if (amount > balance) { // 가격이 잔액보다 큰 경우
            throw new AccountException(ErrorCode.AMOUNT_EXCEED_BALANCE);
        }

        balance -= amount; // 잔액에 가격을 빼서 잔액에 저장
    }

    // 중요한 데이터를 변경하는 로직은 객체 안에서 직접 수행 할수있도록 하는 편이 안전하다.
    public void cancelBalance(Long amount) {
        if (amount < 0) { // 캔슬할때 가격이 0보다 작으면 X
            throw new AccountException(ErrorCode.INVALID_REQUEST);
        }

        balance += amount; // 잔액에 가격을 더해서 잔액에 저장
    }
}
