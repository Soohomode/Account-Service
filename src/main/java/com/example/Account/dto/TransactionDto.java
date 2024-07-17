package com.example.Account.dto;

import com.example.Account.domain.Transaction;
import com.example.Account.type.TransactionResultType;
import com.example.Account.type.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    // 계좌번호
    private String accountNumber;
    // 거래의 종류(사용, 사용취소)
    private TransactionType transactionType;
    // 거래 결과(성공, 실패)
    private TransactionResultType transactionResultType;
    // 거래 금액
    private Long amount;
    // 거래 후 계좌 잔액
    private Long balanceSnapshot;
    // 거래 고유 id 별도의 id를 사용 해야한다 (해킹 위험)
    private String transactionId;
    // 거래 일시
    private LocalDateTime transactedAt;

    public static TransactionDto fromEntity(Transaction transaction) {
        return TransactionDto.builder()
                .accountNumber(transaction.getAccount().getAccountNumber())
                .transactionType(transaction.getTransactionType())
                .transactionResultType(transaction.getTransactionResultType())
                .amount(transaction.getAmount())
                .balanceSnapshot(transaction.getBalanceSnapshot())
                .transactionId(transaction.getTransactionId())
                .transactedAt(transaction.getTransactedAt())
                .build();
    }
}
