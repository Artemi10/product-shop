package devanmejia.productshop.services.user;

import devanmejia.productshop.models.account.user.User;
import devanmejia.productshop.transfer.account.AccountForm;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User generateNewUser(AccountForm form);
    boolean isAdult(User user);
    int getAge(User user);
}
