package devanmejia.productshop.controllers;

import devanmejia.productshop.models.account.Account;
import devanmejia.productshop.services.account.AccountService;
import devanmejia.productshop.services.order.OrderService;
import devanmejia.productshop.transfer.account.AccountDTO;
import devanmejia.productshop.transfer.account.AccountForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shop")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;

    @PostMapping("/account")
    public AccountDTO createNewAccount(@RequestBody AccountForm form){
        Account account = accountService.createNewAccount(form);
        orderService.createNewActiveOrder(account);
        return AccountDTO.form(account);
    }
}
