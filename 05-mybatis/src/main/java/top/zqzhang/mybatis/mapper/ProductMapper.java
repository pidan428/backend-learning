package top.zqzhang.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.zqzhang.mybatis.entity.Product;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
