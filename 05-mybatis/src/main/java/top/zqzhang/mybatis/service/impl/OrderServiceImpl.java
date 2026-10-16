package top.zqzhang.mybatis.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.zqzhang.mybatis.entity.Order;
import top.zqzhang.mybatis.entity.Product;
import top.zqzhang.mybatis.mapper.OrderMapper;
import top.zqzhang.mybatis.mapper.ProductMapper;
import top.zqzhang.mybatis.service.OrderService;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    private final ProductMapper productMapper;

    public OrderServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    /**
     * 挑战题：创建订单 + 扣库存，两个写操作在同一个事务里。
     *
     * <p>任一步失败，两边的写操作一起回滚，保证原子性。
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> createOrder(Long userId, Long productId, Integer quantity) {
        if (userId == null || productId == null) {
            throw new IllegalArgumentException("userId 和 productId 不能为空");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("购买数量必须大于 0");
        }

        // 第一步：查商品，校验库存
        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new IllegalArgumentException("商品不存在：" + productId);
        }
        if (product.getStock() < quantity) {
            throw new IllegalStateException("库存不足，当前库存 " + product.getStock());
        }

        // 第二步：写订单
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setAmount(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        order.setStatus(1);
        save(order);

        // 第三步：扣库存（同一事务内）
        product.setStock(product.getStock() - quantity);
        productMapper.updateById(product);

        // 后续意图失败，抛异常观察前面两步是否一起回滚
        if (userId == 999L) {
            throw new IllegalStateException("模拟下单后续失败，两个写操作应一起回滚");
        }

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("orderId", order.getId());
        result.put("amount", order.getAmount());
        result.put("remainStock", product.getStock());
        return result;
    }

    @Override
    public Order getOrder(Long id) {
        return getById(id);
    }

    @Override
    public List<Order> listOrders() {
        return list();
    }
}
