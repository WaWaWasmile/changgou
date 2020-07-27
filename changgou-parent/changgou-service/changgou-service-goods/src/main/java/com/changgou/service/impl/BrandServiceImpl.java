package com.changgou.service.impl;

import com.changgou.dao.BrandMapper;
import com.changgou.goods.pojo.Brand;
import com.changgou.service.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询所有brand集合，通用Mapper->brandMapper.selectAll()
     * @return
     */
    @Override
    public List<Brand> findAll() {
        return brandMapper.selectAll();
    }

    /**
     * 根据id查询一个，通用Mapper->brandMapper.selectByPrimaryKey(Object id)
     * @param id
     * @return
     */
    @Override
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 品牌增加
     * @param brand
     */
    @Override
    public void add(Brand brand) {
        brandMapper.insertSelective(brand);
    }

    /**
     * 根据id修改品牌信息
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

    /**
     * 条件搜索查询
     * @return
     */
    @Override
    public List<Brand> findExample(Brand brand) {
        Example example = createExample(brand);
        List<Brand> brands = brandMapper.selectByExample(example);
        return brands;
    }

    /**
     * 分页查询
     * @param page 当前页
     * @param size 一页显示多少条数据
     * @return
     */
    @Override
    public PageInfo<Brand> findPage(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        //PageHelper后必须跟集合
        List<Brand> brands = brandMapper.selectAll();
        return new PageInfo<Brand>(brands);
    }

    /**
     * 分页+条件搜索
     * @param brand 搜索条件
     * @param page 当前页
     * @param size 一页大小
     * @return
     */
    @Override
    public PageInfo<Brand> findPage(Brand brand, Integer page, Integer size) {
        //分页
        PageHelper.startPage(page,size);
        //条件搜索
        Example example = createExample(brand);
        List<Brand> brands = brandMapper.selectByExample(example);
        //封装pageInfo
        return new PageInfo<Brand>(brands);
    }

    public Example createExample(Brand brand){
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if(brand != null){
            if(!StringUtil.isEmpty(brand.getName())){
                /**
                 * 参数1：属性名
                 * 参数2：搜索条件
                 */
                criteria.andLike("name","%"+brand.getName()+"%");
            }
            if(!StringUtil.isEmpty(brand.getLetter())){
                criteria.orEqualTo("letter",brand.getLetter());
            }
        }
        return example;
    }

}
