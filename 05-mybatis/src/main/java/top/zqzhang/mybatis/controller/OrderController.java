package top.zqzhang.mybatis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zqzhang.mybatis.entity.Order;
import top.zqzhang.mybatis.service.OrderService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /** 挑战题：创建订单 + 扣库存（同一事务） */
    @PostMapping
    public Map<String, Object> create(@RequestParam Long userId,
                                     @RequestParam Long productId,
                                     @RequestParam(defaultValue = "1") Integer quantity) {
        return orderService.createOrder(userId, productId, quantity);
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return orderService.getOrder(id);
    }

    @GetMapping
    public List<Order> list() {
        return orderService.listOrders();
    }
}
