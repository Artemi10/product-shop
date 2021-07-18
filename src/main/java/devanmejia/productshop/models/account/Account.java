package devanmejia.productshop.models.account;

import devanmejia.productshop.models.BaseEntity;
import devanmejia.productshop.models.account.user.User;
import devanmejia.productshop.models.order.Order;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseEntity {
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "login", column = @Column(name = "login")),
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name")),
            @AttributeOverride(name = "birthDate", column = @Column(name = "birth_date")),
            @AttributeOverride(name = "email", column = @Column(name = "email"))
    })
    private User user;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Order> orders;
}
