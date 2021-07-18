package devanmejia.productshop.services.email;

import devanmejia.productshop.models.order.Order;
import devanmejia.productshop.transfer.email.EmailOrderParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmailMessageSenderImpl implements EmailMessageSender {
    @Autowired
    @Qualifier("jwtToken")
    private RestTemplate restTemplate;
    @Value("${email.sender.service}")
    private String emailSenderAPI;

    @Override
    public void sendNotificationEmailMessage(Order order) {
        EmailOrderParams params = EmailOrderParams.form(order);
        sendEmailMessage(params);
    }

    private void sendEmailMessage(EmailOrderParams params){
        try{
            HttpEntity<EmailOrderParams> entity = new HttpEntity<>(params);
            restTemplate.exchange(emailSenderAPI, HttpMethod.POST, entity, Void.class);
        } catch (Exception e){
            String message = String.format("Can not send email to %s.", params.getEmail());
            e.printStackTrace();
            throw new IllegalArgumentException(message);
        }
    }
}
