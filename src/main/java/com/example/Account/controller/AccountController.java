package com.example.Account.controller;

import com.example.Account.domain.Account;
import com.example.Account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
    // 컨트롤러는 서비스만 의존하도록, 레포지토리로 바로 접속 불가능
    private final AccountService accountService;

    @GetMapping("/create-account")
    public String createAccount() {
        accountService.createAccount();

        return "😎success";
    }

    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }
}
