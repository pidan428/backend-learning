package top.zqzhang.mybatis.service;

import com.baomidou.mybatisplus.spring.service.IService;
import top.zqzhang.mybatis.entity.Order;

import java.util.List;
import java.util.Map;

public interface OrderService extends IService<Order> {

    /* 挑战题核心：创建订单 + 扣库存，两个写操作在一个事务里，库存不足则双回滚 */
    Map<String, Object> createOrder(Long userId, Long productId, Integer quantity);

    /* 查 */
    Order getOrder(Long id);
    List<Order> listOrders();
}
