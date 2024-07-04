package com.example.Account.repository;

import com.example.Account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Account 엔티티클래스 JPA 저장을 위해 필요한 레포지토리
public interface AccountRepository extends JpaRepository<Account, Long> { // <레포지토리가 활용하게될 엔티티, 엔티티의 PK 의 타입>


}
