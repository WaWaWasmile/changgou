package com.changgou.goods.dao;

import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *
 * 使用通用Mapper来完成增删改查
 */
@Component
public interface BrandMapper extends Mapper<Brand> {

    @Select("select tb.* from tb_brand tb,tb_category_brand tcb where tb.id=tcb.brand_id AND tcb.category = #{id}")
    List<Brand> findListByCategoryId(Integer id);
}
