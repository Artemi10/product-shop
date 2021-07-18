package devanmejia.productshop.transfer.email;

import devanmejia.productshop.models.order.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailOrderParams {
    private Long id;
    private String email;
    private String orderStatus;

    public static EmailOrderParams form(Order order){
        return EmailOrderParams.builder()
                .id(order.getId())
                .email(order.getAccount().getUser().getEmail())
                .orderStatus(order.getStatus().name()).build();
    }
}
