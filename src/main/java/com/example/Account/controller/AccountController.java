package com.example.Account.controller;

import com.example.Account.domain.Account;
import com.example.Account.dto.CreateAccount;
import com.example.Account.service.AccountService;
import com.example.Account.service.RedisTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountController {
    // 컨트롤러는 서비스만 의존하도록, 레포지토리로 바로 접속 불가능
    private final AccountService accountService;
    private final RedisTestService redisTestService;

    @PostMapping("/account?")
    public CreateAccount.Response createAccount(
            @RequestBody @Valid CreateAccount.Request request
    ) {
        accountService.createAccount();

        return "😎success";
    }

    @GetMapping("/get-lock")
    public String getLock() {
        return redisTestService.getLock();
    }

    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }
}
