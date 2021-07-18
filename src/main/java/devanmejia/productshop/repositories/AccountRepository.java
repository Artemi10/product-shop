package devanmejia.productshop.repositories;

import devanmejia.productshop.models.account.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @EntityGraph(attributePaths = {"orders"})
    List<Account> getAllBy();
    Optional<Account> getAccountByUserLogin(String userLogin);
}
