package devanmejia.productshop.controllers;

import devanmejia.productshop.services.cart.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TestRepository repository;

    @GetMapping
    public String test(){
        return repository.test();
    }
}
