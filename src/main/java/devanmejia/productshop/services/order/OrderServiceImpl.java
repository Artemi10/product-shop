package devanmejia.productshop.services.order;

import devanmejia.productshop.models.account.Account;
import devanmejia.productshop.models.order.Order;
import devanmejia.productshop.models.order.Status;
import devanmejia.productshop.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createNewActiveOrder(Account account){
        if (!hasActiveOrder(account)){
            Order order = Order.builder()
                    .account(account)
                    .cart(new ArrayList<>())
                    .status(Status.ACTIVE).build();
            return orderRepository.save(order);
        }
        else{
            throw new IllegalArgumentException("Active order has already been created");
        }
    }

    private boolean hasActiveOrder(Account account){
        Optional<Order> orderOptional = account.getOrders().stream()
                .filter(order -> order.getStatus().equals(Status.ACTIVE))
                .findFirst();
        return orderOptional.isPresent();
    }

    @Override
    public Order getActiveOrder(String login){
        return orderRepository.getActiveOrderByOwner(login, Status.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException("There is not active order"));
    }

    @Override
    public Order getOrderById(Long id){
        return orderRepository.getOrderById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is not active order"));
    }

    @Override
    public int getOrdersAmount(){
        return orderRepository.getOrderAmount();
    }
    @Override
    public int getOwnerOrdersAmount(String login){
        return orderRepository.getOwnerOrderAmount(login);
    }

    @Override
    public Order makeOrder(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such order"));
        if (order.getStatus().equals(Status.ACTIVE)){
            order.setStatus(Status.ORDERED);
            return orderRepository.save(order);
        }
        else {
            String msg = String.format("Order №%d is not ACTIVE", id);
            throw new IllegalArgumentException(msg);
        }
    }

    @Override
    public Order changeOrderStatus(Long id, Status status){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such order"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersInRange(int firstPosition, int lastPosition){
        if(firstPosition < lastPosition){
            int amount = lastPosition - firstPosition;
            return orderRepository.getAllOrdersInRage(PageRequest.of(firstPosition, amount));
        }
        throw new IllegalArgumentException("Range is invalid");
    }

    @Override
    public List<Order> getOrdersInRangeByOwner(String login, int firstPosition, int lastPosition){
        if(firstPosition < lastPosition){
            int size = lastPosition - firstPosition;
            int page = firstPosition / size;
            PageRequest pageRequest = PageRequest.of(page, size);
            return orderRepository.getAllOrdersInRageByOwner(pageRequest, login);
        }
        throw new IllegalArgumentException("Range is invalid");
    }
}
