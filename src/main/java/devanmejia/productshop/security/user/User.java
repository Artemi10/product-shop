package devanmejia.productshop.security.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User{
    private String login;
    private Role role;
    private State state;
}
