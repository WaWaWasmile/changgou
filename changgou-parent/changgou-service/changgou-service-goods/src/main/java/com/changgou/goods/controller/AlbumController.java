package com.changgou.goods.controller;

import com.changgou.goods.pojo.Album;
import com.changgou.goods.service.AlbumService;
import com.github.pagehelper.PageInfo;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/album")
@CrossOrigin
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    /**
     * 查询集合
     * @return
     */
    @GetMapping
    public Result<List<Album>> findAll(){
        List<Album> albums = albumService.findAll();
        return new Result<List<Album>>(true, StatusCode.OK,"查询相册集合",albums);
    }

    @GetMapping(value = "/{id}")
    public Result<Album> findById(@PathVariable(value = "id") Long id){
        Album album = albumService.findById(id);
        return new Result<Album>(true, StatusCode.OK,"根据id查询相册",album);
    }

    @PostMapping
    public Result add(@RequestBody Album album){
        albumService.add(album);
        return new Result(true, StatusCode.OK,"相册添加成功");
    }

    @PutMapping(value = "/{id}")
    public Result update(@PathVariable(value = "id") Long id,@RequestBody Album album){
        album.setId(id);
        albumService.update(album);
        return new Result(true, StatusCode.OK,"相册更新成功");
    }

    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable(value = "id") Long id){
        albumService.delete(id);
        return new Result(true, StatusCode.OK,"相册删除成功");
    }

    @PostMapping(value = "/search")
    public Result<List<Album>> findByExample(@RequestBody Album album){
        List<Album> albums = albumService.findByExample(album);
        return new Result<List<Album>>(true, StatusCode.OK,"根据条件查询相册成功",albums);
    }

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Album>> findPage(@PathVariable(value = "page") Integer pageNum,
                                        @PathVariable(value = "size") Integer pageSize){
        PageInfo<Album> page = albumService.findPage(pageSize, pageNum);
        return new Result<PageInfo<Album>>(true, StatusCode.OK,"分页查询相册成功",page);
    }

    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo<Album>> findPageByExample(@RequestBody Album album,
                                                 @PathVariable(value = "page") Integer pageNum,
                                                 @PathVariable(value = "size") Integer pageSize){
        PageInfo<Album> pageByExample = albumService.findPageByExample(album, pageSize, pageNum);
        return new Result<PageInfo<Album>>(true, StatusCode.OK,"分页+条件查询相册成功",pageByExample);
    }
}
