package devanmejia.productshop.controllers;

import devanmejia.productshop.models.account.Account;
import devanmejia.productshop.models.order.Order;
import devanmejia.productshop.models.order.Status;
import devanmejia.productshop.security.jwt.JWTProvider;
import devanmejia.productshop.services.account.AccountService;
import devanmejia.productshop.services.email.EmailMessageSender;
import devanmejia.productshop.services.order.OrderService;
import devanmejia.productshop.transfer.order.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class OrderController {
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private EmailMessageSender emailMessageSender;

    @PostMapping("/order/active")
    public OrderDTO createNewActiveOrder(HttpServletRequest request){
        String login = jwtProvider.getUserName(request);
        Account account = accountService.getAccountByLogin(login);
        return OrderDTO.form(orderService.createNewActiveOrder(account));
    }

    @GetMapping("/orders/amount")
    public int getUserOrdersAmount(HttpServletRequest request){
        String login = jwtProvider.getUserName(request);
        return orderService.getOwnerOrdersAmount(login);
    }

    @GetMapping("/orders/{first}/{last}")
    public List<OrderDTO> getUserOrders(HttpServletRequest request, @PathVariable int first, @PathVariable int last){
        String login = jwtProvider.getUserName(request);
        List<Order> orders = orderService.getOrdersInRangeByOwner(login, first, last);
        return OrderDTO.form(orders);
    }

    @GetMapping("/admin/orders/amount")
    public int getAllOrdersAmount(){
       return orderService.getOrdersAmount();
    }

    @GetMapping("/admin/orders/{first}/{last}")
    public List<OrderDTO> getAllOrdersInRange(@PathVariable int first, @PathVariable int last){
        List<Order> orders = orderService.getOrdersInRange(first, last);
        return OrderDTO.form(orders);
    }

    @PatchMapping("/order/{id}/status")
    public void makeOrder(@PathVariable Long id) {
        Order order = orderService.makeOrder(id);
        emailMessageSender.sendNotificationEmailMessage(order);
    }

    @PatchMapping("/admin/order/{id}/status/{status}")
    public void changeOrderStatus(@PathVariable Long id, @PathVariable Status status) {
        Order order = orderService.changeOrderStatus(id, status);
        emailMessageSender.sendNotificationEmailMessage(order);
    }
}
