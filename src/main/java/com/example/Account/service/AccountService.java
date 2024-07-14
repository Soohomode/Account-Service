package com.example.Account.service;

import com.example.Account.domain.Account;
import com.example.Account.domain.AccountUser;
import com.example.Account.dto.AccountDto;
import com.example.Account.exception.AccountException;
import com.example.Account.repository.AccountRepository;
import com.example.Account.repository.AccountUserRepository;
import com.example.Account.type.AccountStatus;
import com.example.Account.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static com.example.Account.type.AccountStatus.IN_USE;

@Service
@RequiredArgsConstructor // 필요한 생성자를 만들어주는 final
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountUserRepository accountUserRepository;

    /**
     * 사용자가 있는지 조회
     * 계좌의 번호를 생성하고
     * 계좌를 저장하고, 그 정보를 넘긴다.
     * @param userId
     * @param initialBalance
     */
    @Transactional
    public AccountDto createAccount(Long userId, Long initialBalance) { // userId와 initialBalance 로 계좌 생성

        AccountUser accountUser = accountUserRepository.findById(userId) // 조회를 했을때, 나오는 디폴트 타입은 Optional
                .orElseThrow(() -> new AccountException(ErrorCode.USER_NOT_FOUND));

        validateCreateAccount(accountUser);

        // 계좌번호 10자리
        String newAccountNumber = accountRepository.findFirstByOrderByIdDesc() // 가장 최근 계좌번호
                .map(account -> (Integer.parseInt(account.getAccountNumber())) + 1 + "")
                .orElse("1000000000"); // 현재 계좌가 하나도 없으면

        return AccountDto.fromEntity(
                accountRepository.save(Account.builder()
                        .accountUser(accountUser)
                        .accountStatus(IN_USE)
                        .accountNumber(newAccountNumber)
                        .balance(initialBalance)
                        .registeredAt(LocalDateTime.now())
                        .build())
        );
    }

    // 1인 계좌 수가 10개이면
    private void validateCreateAccount(AccountUser accountUser) {
        if (accountRepository.countByAccountUser(accountUser) >= 10) {
            throw new AccountException(ErrorCode.MAX_ACCOUNT_PER_USER_10);
        }
    }

    @Transactional
    public Account getAccount(Long id) {
        if (id < 0) {
            throw new RuntimeException("마이너스");
        }
        return accountRepository.findById(id).get();
    }
}
