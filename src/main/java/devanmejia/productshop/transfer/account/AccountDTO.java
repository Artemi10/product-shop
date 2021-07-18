package devanmejia.productshop.transfer.account;

import devanmejia.productshop.models.account.Account;
import devanmejia.productshop.models.account.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private String userLogin;
    private String userFirstName;
    private String userLastName;
    private Date userBirthDate;

    public static AccountDTO form(Account account){
        User user = account.getUser();
        return AccountDTO.builder()
                .id(account.getId())
                .userLogin(user.getLogin())
                .userFirstName(user.getFirstName())
                .userLastName(user.getLastName())
                .userBirthDate(user.getBirthDate()).build();
    }
}
