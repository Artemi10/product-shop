package devanmejia.productshop.services.order;

import devanmejia.productshop.models.account.Account;
import devanmejia.productshop.models.order.Order;
import devanmejia.productshop.models.order.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    Order createNewActiveOrder(Account account);
    Order getActiveOrder(String login);
    Order getOrderById(Long id);
    int getOrdersAmount();
    int getOwnerOrdersAmount(String login);
    Order makeOrder(Long id);
    Order changeOrderStatus(Long id, Status status);
    List<Order> getOrdersInRange(int firstPosition, int lastPosition);
    List<Order> getOrdersInRangeByOwner(String login, int firstPosition, int lastPosition);
}
