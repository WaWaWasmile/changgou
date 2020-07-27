package com.changgou.service;

import com.changgou.goods.pojo.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    /**
     * 查询所有；
     */
    List<Brand> findAll();
    /**
     * 根据id查询
     */
    Brand findById(Integer id);
    /**
     * 品牌增加
     */
    void add(Brand brand);
    /**
     * 根据ID修改品牌信息
     */
    void update(Brand brand);
    void delete(Integer id);

    /**
     * 条件搜索查询
     * @return
     */
    List<Brand> findExample(Brand brand);

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<Brand> findPage(Integer page,Integer size);

    /**
     * 分页+条件搜索
     * @param brand
     * @param page
     * @param size
     * @return
     */
    PageInfo<Brand> findPage(Brand brand,Integer page,Integer size);
}
