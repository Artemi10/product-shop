package devanmejia.productshop.models.stock;

import devanmejia.productshop.models.BaseEntity;
import devanmejia.productshop.models.product.Product;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "stock_products")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "product-stock",
                attributeNodes = {
                        @NamedAttributeNode(value = "product")
                }
        )
})
public class StockProduct extends BaseEntity {
    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
    @Column(name = "amount")
    private int amount;
}
