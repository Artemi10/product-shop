package devanmejia.productshop.services.account;

import devanmejia.productshop.models.account.Account;
import devanmejia.productshop.transfer.account.AccountForm;
import org.springframework.stereotype.Service;

@Service
public interface AccountService{
    Account createNewAccount(AccountForm form);
    Account getAccountByLogin(String login);
}
