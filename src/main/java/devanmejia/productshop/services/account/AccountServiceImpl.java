package devanmejia.productshop.services.account;

import devanmejia.productshop.models.account.Account;
import devanmejia.productshop.models.account.user.User;
import devanmejia.productshop.repositories.AccountRepository;
import devanmejia.productshop.services.user.UserService;
import devanmejia.productshop.transfer.account.AccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private UserService userService;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createNewAccount(AccountForm form){
        User user = userService.generateNewUser(form);
        Account account = Account.builder()
                .orders(new ArrayList<>())
                .user(user).build();
        return accountRepository.save(account);
    }

    @Override
    public Account getAccountByLogin(String login) {
        return accountRepository.getAccountByUserLogin(login)
                .orElseThrow(() -> new IllegalArgumentException(String.format("There is not account for user %s", login)));
    }
}
