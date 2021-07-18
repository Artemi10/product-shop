package devanmejia.productshop.models.order;

import devanmejia.productshop.models.BaseEntity;
import devanmejia.productshop.models.account.Account;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "account-order",
                attributeNodes = {
                        @NamedAttributeNode(value = "account")
                }
        ),
        @NamedEntityGraph(
                name = "cart-order",
                attributeNodes = {
                        @NamedAttributeNode(value = "account"),
                        @NamedAttributeNode(value = "cart")
                }
        )
})
public class Order extends BaseEntity {
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<CartProduct> cart;
}
