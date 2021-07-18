package devanmejia.productshop.models.order;

import devanmejia.productshop.models.BaseEntity;
import devanmejia.productshop.models.product.Product;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "cart_products")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CartProduct extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    @Column(name = "amount")
    private int amount;
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
}
