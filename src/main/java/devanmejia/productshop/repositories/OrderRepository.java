package devanmejia.productshop.repositories;

import devanmejia.productshop.models.order.Order;
import devanmejia.productshop.models.order.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = {"account", "cart"})
    List<Order> getAllBy();

    @Query(value = "SELECT count(orders.id) FROM orders", nativeQuery = true)
    int getOrderAmount();

    @Query(value = "SELECT count(orders.id) FROM orders\n" +
            "LEFT JOIN accounts a on a.id = orders.account_id\n" +
            "WHERE a.login = :login", nativeQuery = true)
    int getOwnerOrderAmount(@Param("login") String login);

    @EntityGraph(value = "account-order")
    @Query(value = "SELECT o FROM Order o ORDER BY o.id DESC")
    List<Order> getAllOrdersInRage(Pageable pageable);

    @EntityGraph(value = "account-order")
    @Query(value = "SELECT o FROM Order o " +
            "WHERE o.account.user.login = :login ORDER BY o.id DESC")
    List<Order> getAllOrdersInRageByOwner(Pageable pageable, @Param("login") String login);

    @EntityGraph(value = "cart-order")
    @Query(value = "SELECT o FROM Order o " +
            "WHERE o.account.user.login = :login AND o.status = :status ")
    Optional<Order> getActiveOrderByOwner(@Param("login") String login, @Param("status") Status status);

    @EntityGraph(value = "cart-order")
    @Query(value = "SELECT order FROM Order order WHERE order.id = :id")
    Optional<Order> getOrderById(@Param("id") long id);
}
