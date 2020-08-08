package com.changgou.goods.service.impl;

import com.changgou.goods.dao.CategoryMapper;
import com.changgou.goods.dao.TemplateMapper;
import com.changgou.goods.pojo.Category;
import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class TemplateServiceImpl implements TemplateService {
    @Autowired
    TemplateMapper templateMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public Template findByCategoryId(Integer id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        return templateMapper.selectByPrimaryKey(category.getTemplateId());
    }

    //查询所有
    @Override
    public List<Template> findAll() {
        return templateMapper.selectAll();
    }

    //根据id查询
    @Override
    public Template findById(Integer id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Template template) {
        templateMapper.insertSelective(template);
    }

    @Override
    public void update(Template template) {
        templateMapper.updateByPrimaryKeySelective(template);
    }

    @Override
    public void delete(Integer id) {
        templateMapper.deleteByPrimaryKey(id);
    }

    //分页查询
    @Override
    public PageInfo<Template> pageInfo(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<Template> templates = templateMapper.selectAll();
        return new PageInfo<Template>(templates);
    }

    //条件搜索
    @Override
    public List<Template> findByExample(Template template) {
        Example example = createExample(template);
        List<Template> templates = templateMapper.selectByExample(example);
        return templates;
    }

    //分页+条件搜索
    @Override
    public PageInfo<Template> findPageByExample(Template template, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        Example example = createExample(template);
        List<Template> templates = templateMapper.selectByExample(example);
        return new PageInfo<Template>(templates);
    }

    //拼接模糊查询sql
    public Example createExample(Template template){
        Example example = new Example(Template.class);
        Example.Criteria criteria = example.createCriteria();
        if(template != null){
            //模板名称
            if (!StringUtil.isEmpty(template.getName())){
                criteria.andLike("name","%"+template.getName()+"%");
            }
            //规格数量
            if (template.getSpecNum()!= null){
                criteria.orEqualTo("spec_num",template.getSpecNum());
            }
            //参数数量
            if(template.getParaNum()!= null){
                criteria.orEqualTo("para_num",template.getParaNum());
            }
        }
        return example;
    }
}
