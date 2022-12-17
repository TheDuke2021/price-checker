package com.damir.pricechecker.data;

import com.damir.pricechecker.models.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findByUsername(String username);
    Boolean existsByUsername(String username);
}
