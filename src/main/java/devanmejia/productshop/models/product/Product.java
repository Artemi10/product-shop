package devanmejia.productshop.models.product;

import devanmejia.productshop.models.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity {
    @Column(name = "product_name")
    private String name;
    @Column(name = "product_price")
    private int price;
    @Column(name = "product_description")
    private String description;
    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private Type type;
}
