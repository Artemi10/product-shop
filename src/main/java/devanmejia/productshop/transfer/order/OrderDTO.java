package devanmejia.productshop.transfer.order;


import devanmejia.productshop.models.account.Account;
import devanmejia.productshop.models.order.Order;
import devanmejia.productshop.models.order.Status;
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
public class OrderDTO {
    private Long id;
    private Status status;
    private Long accountId;
    private String ownerLogin;

    public static OrderDTO form(Order order){
        Account account = order.getAccount();
        return OrderDTO.builder()
                .id(order.getId())
                .status(order.getStatus())
                .ownerLogin(account.getUser().getLogin())
                .accountId(account.getId()).build();
    }

    public static List<OrderDTO> form(List<Order> orders){
        return orders.stream().map(OrderDTO::form)
                .collect(Collectors.toList());
    }
}
