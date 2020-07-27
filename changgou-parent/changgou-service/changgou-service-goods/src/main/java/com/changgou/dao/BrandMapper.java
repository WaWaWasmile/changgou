package com.changgou.dao;

import com.changgou.goods.pojo.Brand;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

/**
 *
 * 使用通用Mapper来完成增删改查
 */
@Component
public interface BrandMapper extends Mapper<Brand> {
}
