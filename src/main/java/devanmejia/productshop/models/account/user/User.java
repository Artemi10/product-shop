package devanmejia.productshop.models.account.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Date;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String login;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String email;
}
