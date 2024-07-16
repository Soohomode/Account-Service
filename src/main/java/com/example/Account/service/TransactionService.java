package com.example.Account.service;

import com.example.Account.domain.Account;
import com.example.Account.domain.AccountUser;
import com.example.Account.dto.TransactionDto;
import com.example.Account.exception.AccountException;
import com.example.Account.repository.AccountRepository;
import com.example.Account.repository.AccountUserRepository;
import com.example.Account.repository.TransactionRepository;
import com.example.Account.type.AccountStatus;
import com.example.Account.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountUserRepository accountUserRepository;
    private final AccountRepository accountRepository;

    /**
     * 사용자 없는 경우, 계좌가 없는 경우, 사용자 아이디와 계좌 소유주가 다른 경우,
     * 계좌가 이미 해지 상태인 경우, 거래금액이 잔액보다 큰 경우,
     * 거래금액이 너무 작거나 큰 경우 실패 응답 <- UseBalance.Request 에 이미 validate 함.
     */
    @Transactional
    public TransactionDto useBalance(Long userId, String accountNumber, Long amount) {
        // 유저를 찾아서 user 변수에 저장
        AccountUser user = accountUserRepository.findById(userId)
                // 사용자 없는 경우
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));
        // 계좌를 찾아서 account 변수에 저장
        Account account = accountRepository.findByAccountNumber(accountNumber)
                // 계좌가 없는 경우
                .orElseThrow(() -> new AccountException(ErrorCode.ACCOUNT_NOT_FOUND));

        validateUseBalance(user, account, amount);
    }

    // 확인 검증 작업
    private void validateUseBalance(AccountUser user, Account account, Long amount) {
        // 사용자 아이디와 계좌 소유주가 다른 경우
        if (!Objects.equals(user.getId(), account.getAccountUser().getId())) {
            throw new AccountException(ErrorCode.USER_ACCOUNT_UN_MATCH);
        }
        // 계좌가 이미 해지 상태인 경우
        if (account.getAccountStatus() != AccountStatus.IN_USE) {
            throw new AccountException(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED);
        }
        // 거래금액이 잔액보다 큰 경우
        if (account.getBalance() < amount) {
            throw new AccountException(ErrorCode.AMOUNT_EXCEED_BALANCE);
        }
    }
}
