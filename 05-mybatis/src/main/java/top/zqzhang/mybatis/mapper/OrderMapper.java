package top.zqzhang.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.zqzhang.mybatis.entity.Order;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
