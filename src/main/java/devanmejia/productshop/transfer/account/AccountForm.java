package devanmejia.productshop.transfer.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountForm {
    private String userLogin;
    private String userFirstName;
    private String userLastName;
    private Date userBirthDate;
    private String userEmail;
}
