package com.changgou.goods.controller;

import com.changgou.goods.pojo.Template;
import com.changgou.goods.service.TemplateService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/template")
@CrossOrigin
public class TemplateController {
    @Autowired
    private TemplateService templateService;

    /**
     * 根据分类查询模板成功
     * @param id
     * @return
     */
    @GetMapping(value = "/category/{id}")
    public Result<Template> findByCategoryId(@PathVariable (value = "id") Integer id){
        Template template = templateService.findByCategoryId(id);
        return new Result<Template>(true, StatusCode.OK,"根据分类查询模板成功",template);
    }

    //查询所有
    @GetMapping
    public Result<List<Template>> findAll(){
        List<Template> templates = templateService.findAll();
        return new Result<List<Template>>(true, StatusCode.OK,"查询集合成功",templates);
    }

    //根据id查询
    @GetMapping(value = "/{id}")
    public Result<Template> findById(@PathVariable(value = "id") Integer id){
        Template template = templateService.findById(id);
        return new Result<Template>(true, StatusCode.OK,"根据id查询成功",template);
    }

    //添加
    @PostMapping
    public Result add(@RequestBody Template template){
        templateService.add(template);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    //更新
    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id")Integer id, @RequestBody Template template){
        template.setId(id);
        templateService.update(template);
        return new Result(true, StatusCode.OK,"更新成功");
    }

    //删除
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id") Integer id){
        templateService.delete(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    //分页查询
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Template>> findPageInfo(@PathVariable(value = "size") Integer pageSize,
                                           @PathVariable(value = "page") Integer pageNum){
        PageInfo<Template> templatePageInfo = templateService.pageInfo(pageSize, pageNum);
        return new Result<PageInfo<Template>>(true, StatusCode.OK,"分页查询成功",templatePageInfo);
    }

    //条件搜索
    @PostMapping(value = "/search")
    public Result<List<Template>> findByExample(@RequestBody Template template){
        List<Template> templates = templateService.findByExample(template);
        return new Result<List<Template>>(true, StatusCode.OK,"条件搜索成功",templates);
    }

    //分页+条件搜索
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Template>> findPageByExample(@RequestBody Template template,
                                                @PathVariable(value = "size") Integer pageSize,
                                                @PathVariable(value = "page") Integer pageNum){
        PageInfo<Template> pageByExample = templateService.findPageByExample(template, pageSize, pageNum);
        return new Result<PageInfo<Template>>(true, StatusCode.OK,"分页+条件查询成功",pageByExample);
    }
}
