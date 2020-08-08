package com.changgou.goods.service;

import com.changgou.goods.pojo.Template;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TemplateService {
    /**
     * 根据分类id查询摸板信息
     * @param id
     * @return
     */
    Template findByCategoryId(Integer id);
    //查询所有
    List<Template> findAll();

    //根据id查询
    Template findById(Integer id);

    //添加
    void add(Template template);

    //更新
    void update(Template template);

    //删除
    void delete(Integer id);

    //分页查询
    PageInfo<Template> pageInfo(Integer pageSize,Integer pageNum);

    //条件搜索
    List<Template> findByExample(Template template);

    //分页+条件搜索
    PageInfo<Template> findPageByExample(Template template,Integer pageSize,Integer pageNum);
}
