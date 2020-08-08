package com.changgou.goods.service;

import com.changgou.goods.pojo.Album;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AlbumService {
    //查询所有相册
    List<Album> findAll();
    //根据id查询
    Album findById(Long id);
    //相册增加
    void add(Album album);
    //根据id修改相册信息
    void update(Album album);
    //删除相册
    void delete(Long id);
    //条件搜索
    List<Album> findByExample(Album album);
    //分页查询
    PageInfo<Album> findPage(Integer pageSize, Integer pageNum);
    //分页+条件查询
    PageInfo<Album> findPageByExample(Album album, Integer pageSize, Integer pageNum);
}
