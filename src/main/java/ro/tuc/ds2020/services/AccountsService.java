package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.AccountsDTO;
import ro.tuc.ds2020.dtos.AccountsUpdateDTO;
import ro.tuc.ds2020.dtos.builders.AccountsBuilder;
import ro.tuc.ds2020.entities.Accounts;
import ro.tuc.ds2020.entities.Roles;
import ro.tuc.ds2020.repositories.AccountsRepository;
import ro.tuc.ds2020.repositories.RolesRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountsService.class);
    private final AccountsRepository accountsRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository, RolesRepository rolesRepository){
        this.accountsRepository = accountsRepository;
        this.rolesRepository = rolesRepository;
    }

    public List<AccountsDTO> findAllAccounts(){
        List<Accounts> accountsList = accountsRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return accountsList.stream()
                .map(AccountsBuilder::toAccountsDTO)
                .collect(Collectors.toList());
    }

    public AccountsDTO findAccByID(Long id){
        Optional<Accounts> optionalAccounts = accountsRepository.findById(id);
        if ( !optionalAccounts.isPresent() ){
            LOGGER.error("Account with id {} was not found in db", id);
            throw new ResourceNotFoundException(Accounts.class.getSimpleName() + " with id: " + id);
        }
        return AccountsBuilder.toAccountsDTO(optionalAccounts.get());
    }

    public void verifyAccount(AccountsDTO accountsDTO){
        String usernameToLookUp = accountsDTO.getUsername();
        String passToLookUp = accountsDTO.getPassword();
        Accounts trueAccount = accountsRepository.findAccountsByUsernameAndPassword(
                usernameToLookUp, passToLookUp);
        if ( trueAccount == null ){
            LOGGER.error("Account with username {} was not found in db", usernameToLookUp);
            throw new ResourceNotFoundException(Accounts.class.getSimpleName() + " with username: " + usernameToLookUp);
        }
    }

    public Long insert(AccountsDTO accountsDTO){
        String roleName = "client";
        Roles r = rolesRepository.findRolesByRoleName(roleName);

        Accounts searchAcc = accountsRepository.findAccountsByUsername(accountsDTO.getUsername());
        if(searchAcc != null){
            LOGGER.error("Account with name {} already exists in db", accountsDTO.getUsername());
            throw new ResourceNotFoundException(Accounts.class.getSimpleName() + " with id: " + searchAcc.getId());
        }

        Accounts newAccount = AccountsBuilder.toEntity(accountsDTO, r);
        newAccount = accountsRepository.save(newAccount);
        LOGGER.debug("Account with id {} and name {} was inserted in db", newAccount.getId(), newAccount.getUsername());
        return newAccount.getId();
    }

    public void delete(Long id){
        accountsRepository.deleteById(id);
    }

    public void update(AccountsUpdateDTO dto){
        Long id = dto.getId();

        //account to be updated
        Accounts account = accountsRepository.findAccountsById(id);

        //initialized with the current values
        //if no new value is provided the old one is preserved
        String newUsername = account.getUsername();
        String newPassword = account.getPassword();

        if(dto.getUsername() != null){
            newUsername = dto.getUsername();
        }
        if(dto.getPassword() != null){
            newPassword = dto.getPassword();
        }

        //update the acount with the new values
        account.setUsername(newUsername);
        account.setPassword(newPassword);

        accountsRepository.save(account);
        LOGGER.debug("Account updated");
    }

}
