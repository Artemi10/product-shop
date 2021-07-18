package devanmejia.productshop.services.user;

import devanmejia.productshop.models.account.Account;
import devanmejia.productshop.models.account.user.User;
import devanmejia.productshop.repositories.AccountRepository;
import devanmejia.productshop.transfer.account.AccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public User generateNewUser(AccountForm form){
        Optional<Account> userOptional = accountRepository.getAccountByUserLogin(form.getUserLogin());
        if (!userOptional.isPresent()){
            User user = User.builder()
                    .login(form.getUserLogin())
                    .firstName(form.getUserFirstName())
                    .lastName(form.getUserLastName())
                    .birthDate(form.getUserBirthDate())
                    .email(form.getUserEmail()).build();
            if (isAdult(user)) {
                return user;
            }
            else{
                String message = String.format("%s is under 18 years", form.getUserLogin());
                throw new IllegalArgumentException(message);
            }
        }
        else {
            String message = String.format("%s has already been registered", form.getUserLogin());
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public boolean isAdult(User user){
        return getAge(user) >= 18;
    }

    @Override
    public int getAge(User user){
        LocalDate birthDate = user.getBirthDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
}
