package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.AccountsDTO;
import ro.tuc.ds2020.entities.Accounts;
import ro.tuc.ds2020.entities.Roles;

public class AccountsBuilder {

    private AccountsBuilder(){

    }

    public static AccountsDTO toAccountsDTO(Accounts account){
        return new AccountsDTO(account.getId(), account.getUsername(), account.getPassword());
    }

    public static Accounts toEntity(AccountsDTO accountsDTO, Roles role){
        return new Accounts(accountsDTO.getId(), accountsDTO.getUsername(), accountsDTO.getPassword(), role);
    }
}
