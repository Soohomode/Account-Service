package com.example.Account.dto;

import com.example.Account.aop.AccountLockIdInterface;
import com.example.Account.type.TransactionResultType;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class UseBalance {
    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request implements AccountLockIdInterface {
        @NonNull // userId는 필수값!
        @Min(1)
        private Long userId;

        @NotBlank // @NotBlank 는 null 과 "" 과 " " 모두 허용하지 않습니다.
        @Size(min = 10, max = 10)
        private String accountNumber;

        @NotNull // 필수값
        @Min(10) // 0원 이상
        @Max(1_000_000_000) // 최대 거래 금액 10억
        private Long amount; // 거래 금액
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String accountNumber;
        private TransactionResultType transactionResult;
        private String transactionId;
        private Long amount;
        private LocalDateTime transactedAt;

        public static Response from(TransactionDto transactionDto) {
            return Response.builder()
                    .accountNumber(transactionDto.getAccountNumber())
                    .transactionResult(transactionDto.getTransactionResultType())
                    .transactionId(transactionDto.getTransactionId())
                    .amount(transactionDto.getAmount())
                    .transactedAt(transactionDto.getTransactedAt())
                    .build();
        }
    }
}
