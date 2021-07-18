package devanmejia.productshop.transfer.product;

import devanmejia.productshop.models.order.CartProduct;
import devanmejia.productshop.models.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartProductDTO {
    private Product product;
    private int amount;

    public static CartProductDTO form(CartProduct cartProduct){
        return CartProductDTO.builder()
                .product(cartProduct.getProduct())
                .amount(cartProduct.getAmount()).build();
    }

    public static List<CartProductDTO> form(List<CartProduct> cart){
        return cart.stream().map(CartProductDTO::form).collect(Collectors.toList());
    }
}
