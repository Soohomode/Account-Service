package com.example.Account.controller;

import com.example.Account.aop.AccountLock;
import com.example.Account.dto.CancelBalance;
import com.example.Account.dto.QueryTransactionResponse;
import com.example.Account.dto.UseBalance;
import com.example.Account.exception.AccountException;
import com.example.Account.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 잔액 관련 컨트롤러
 * 1. 잔액 사용
 * 2. 잔액 사용 취소
 * 3. 거래 확인
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    // 잔액 사용
    @PostMapping("/transaction/use")
    @AccountLock
    public UseBalance.Response useBalance(@Valid @RequestBody UseBalance.Request request) throws InterruptedException {

        try {
            Thread.sleep(3000L);
            return UseBalance.Response.from(
                    transactionService.useBalance(request.getUserId(),
                            request.getAccountNumber(), request.getAmount())
            );
        } catch (AccountException e) { // 실패가 됐다면
            log.error("Failed to use balance.🥲");

            transactionService.saveFailedUseTransaction(
                    request.getAccountNumber(),
                    request.getAmount()
            );

            throw e;
        }
    }

    // 잔액 사용 취소
    @PostMapping("/transaction/cancel")
    @AccountLock
    public CancelBalance.Response cancelBalance(@Valid @RequestBody CancelBalance.Request request) {

        try {
            return CancelBalance.Response.from(
                    transactionService.cancelBalance(request.getTransactionId(),
                            request.getAccountNumber(), request.getAmount())
            );
        } catch (AccountException e) { // 실패가 됐다면
            log.error("Failed to use balance.🥲");

            transactionService.saveFailedCancelTransaction(
                    request.getAccountNumber(),
                    request.getAmount()
            );

            throw e;
        }
    }

    // 잔액 사용 확인
    @GetMapping("/transaction/{transactionId}")
    public QueryTransactionResponse queryTransactionResponse(@PathVariable String transactionId) {

        return QueryTransactionResponse.from(
                transactionService.queryTransaction(transactionId)
        );
    }
}
