package devanmejia.productshop.services.email;

import devanmejia.productshop.models.order.Order;
import org.springframework.stereotype.Service;

@Service
public interface EmailMessageSender {
    void sendNotificationEmailMessage(Order order);
}
