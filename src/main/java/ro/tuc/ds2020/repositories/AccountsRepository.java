package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {

    Accounts findAccountsByUsername(String username);

    Accounts findAccountsByUsernameAndPassword(String username, String password);

    Accounts findAccountsById(Long id);
}
