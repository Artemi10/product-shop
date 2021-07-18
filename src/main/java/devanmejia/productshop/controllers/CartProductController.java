package devanmejia.productshop.controllers;

import devanmejia.productshop.models.order.Order;
import devanmejia.productshop.models.product.Product;
import devanmejia.productshop.security.jwt.JWTProvider;
import devanmejia.productshop.services.cart.CartService;
import devanmejia.productshop.services.order.OrderService;
import devanmejia.productshop.services.products.ProductService;
import devanmejia.productshop.transfer.product.CartProductAmount;
import devanmejia.productshop.transfer.product.CartProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class CartProductController {
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @PatchMapping("/cartProduct")
    public void addProductInCart(@RequestBody CartProductAmount cartProduct, HttpServletRequest request) {
        String productName = cartProduct.getProductName();
        try{
            String login = jwtProvider.getUserName(request);
            Order order = orderService.getActiveOrder(login);
            Product product = productService.getProductByName(productName);
            cartService.addProductInCart(order, product, cartProduct.getNewAmount());
        } catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException(String.format("Not enough products in the stock (%s)", cartProduct.getProductName()));
        }
    }

    @GetMapping("/admin/order/{orderId}/cartProducts")
    public List<CartProductDTO> getCartProducts(@PathVariable Long orderId){
        Order order = orderService.getOrderById(orderId);
        return CartProductDTO.form(order.getCart());
    }

    @GetMapping("/order/{orderId}/cartProducts")
    public List<CartProductDTO> getUserCartProducts(@PathVariable Long orderId, HttpServletRequest request){
        String login = jwtProvider.getUserName(request);
        Order order = orderService.getOrderById(orderId);
        if (order.getAccount().getUser().getLogin().equals(login)){
            return CartProductDTO.form(order.getCart());
        }
        else {
            throw new IllegalArgumentException(String.format("%s does not have order №%d", login, orderId));
        }
    }

    @GetMapping("/order/active/cartProducts")
    public List<CartProductDTO> getCartProductsFromActiveOrder(HttpServletRequest request){
        String login = jwtProvider.getUserName(request);
        Order order = orderService.getActiveOrder(login);
        return CartProductDTO.form(order.getCart());
    }

    @GetMapping("/order/active/cartProducts/amount")
    public int getCartProductsAmount(HttpServletRequest request){
        String login = jwtProvider.getUserName(request);
        Order order = orderService.getActiveOrder(login);
        return cartService.getCartProductsAmount(order);
    }

    @GetMapping("/order/active/cartProducts/amount/{productName}")
    public int getCartProductsAmount(HttpServletRequest request, @PathVariable String productName){
        String login = jwtProvider.getUserName(request);
        Order order = orderService.getActiveOrder(login);
        return cartService.getCartProductAmount(order, productName);
    }
}
