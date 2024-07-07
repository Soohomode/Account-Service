package com.example.Account.service;

import com.example.Account.domain.Account;
import com.example.Account.domain.AccountStatus;
import com.example.Account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor // 필요한 생성자를 만들어주는 final
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public void createAccount() {
        Account account = Account.builder()
                .accountNumber("40000")
                .accountStatus(AccountStatus.IN_USE)
                .build();
        accountRepository.save(account);
    }

    @Transactional
    public Account getAccount(Long id) {
        if (id < 0) {
            throw new RuntimeException("마이너스");
        }
        return accountRepository.findById(id).get();
    }
}
