package com.changgou.goods.controller;

import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/brand")
/**
 * 可跨域注解
 */
@CrossOrigin
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 根据分类id查询品牌集合--分类id
     * @param id
     * @return
     */
    @GetMapping(value = "/category/{id}")
    public Result<List<Brand>> findBrandByCategory(@PathVariable(value = "id") Integer id){
        List<Brand> brands = brandService.findListByCategoryId(id);
        return new Result<List<Brand>>(true,StatusCode.OK,"根据分类id查询品牌集合成功",brands);
    }

    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@RequestBody Brand brand,@PathVariable(value = "page")Integer page,
                                            @PathVariable(value = "size")Integer size){
        PageInfo<Brand> pageInfo = brandService.findPage(brand,page, size);
        return new Result<PageInfo<Brand>>(true, StatusCode.OK,"分页查询成功！",pageInfo);
    }

    /**
     * 分页搜索
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Brand>> findPage(@PathVariable(value = "page")Integer page,
                             @PathVariable(value = "size")Integer size){
        PageInfo<Brand> pageInfo = brandService.findPage(page, size);
        return new Result<PageInfo<Brand>>(true, StatusCode.OK,"分页查询成功！",pageInfo);
    }

    @PostMapping(value = "/search")
    public Result<List<Brand>> findExample(@RequestBody Brand brand){
        List<Brand> brands = brandService.findExample(brand);
        return new Result<List<Brand>>(true, StatusCode.OK,"根据条件查询成功！",brands);
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id")Integer id){
        brandService.delete(id);
        return  new Result(true, StatusCode.OK,"删除品牌成功！");
    }

    @PutMapping("/{id}")
    public Result updateById(@PathVariable(value = "id")Integer id,@RequestBody Brand brand){
        //将id给brand
        brand.setId(id);
        brandService.update(brand);
        return  new Result(true, StatusCode.OK,"修改品牌成功！");
    }

    @GetMapping
    public Result<List<Brand>> findAll(){
        List<Brand> brands = brandService.findAll();
        //boolean flag, Integer code, String message, Object dat
        //响应结果封装
        return new Result<List<Brand>>(true, StatusCode.OK,"查询品牌集合成功！",brands);
    }

    @GetMapping(value = "/{id}")
    public Result<Brand> findById(@PathVariable(value = "id") Integer id){
        Brand brand = brandService.findById(id);
        return new Result<Brand>(true, StatusCode.OK,"根据ID查询成功！",brand);
    }

    @PostMapping
    public Result add(@RequestBody Brand brand){
        brandService.add(brand);
        return new Result(true, StatusCode.OK,"添加品牌成功！");
    }
}
