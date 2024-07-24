package com.example.Account.domain;

import com.example.Account.type.AccountStatus;
import com.example.Account.type.TransactionResultType;
import com.example.Account.type.TransactionType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // 일종의 설정 클래스
public class Transaction extends BaseEntity {

    // 거래의 종류(사용, 사용취소)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    // 거래 결과(성공, 실패)
    @Enumerated(EnumType.STRING)
    private TransactionResultType transactionResultType;

    // 거래가 발생한 계좌
    @ManyToOne
    private Account account;

    // 거래 금액
    private Long amount;

    // 거래 후 계좌 잔액
    private Long balanceSnapshot;

    // 거래 고유 id 별도의 id를 사용 해야한다 (해킹 위험)
    private String transactionId;
    // 거래 일시
    private LocalDateTime transactedAt;
}
