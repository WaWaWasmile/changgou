package com.changgou.goods.service.impl;

import com.changgou.goods.dao.AlbumMapper;
import com.changgou.goods.pojo.Album;
import com.changgou.goods.service.AlbumService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumMapper albumMapper;

    @Override
    public List<Album> findAll() {
        return albumMapper.selectAll();
    }

    @Override
    public Album findById(Long id) {
        return albumMapper.selectByPrimaryKey(id);
    }

    @Override
    public void add(Album album) {
        albumMapper.insertSelective(album);
    }

    @Override
    public void update(Album album) {
        albumMapper.updateByPrimaryKeySelective(album);
    }

    @Override
    public void delete(Long id) {
        albumMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Album> findByExample(Album album) {
        Example example = createExample(album);
        List<Album> albums = albumMapper.selectByExample(example);
        return albums;
    }

    @Override
    public PageInfo<Album> findPage(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        List<Album> albums = albumMapper.selectAll();
        return new PageInfo<Album>(albums);
    }

    @Override
    public PageInfo<Album> findPageByExample(Album album, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = createExample(album);
        List<Album> albums = albumMapper.selectByExample(example);
        return new PageInfo<Album>(albums);
    }

    /**
     * 拼接搜索条件
     * @param album
     * @return
     */
    public Example createExample(Album album){
        Example example = new Example(Album.class);
        Example.Criteria criteria = example.createCriteria();
        if(album != null){
            //相册标题
            if(!StringUtil.isEmpty(album.getTitle())){
                criteria.andLike("title","%"+album.getTitle()+"%");
            }
            //相册封面
            if(StringUtil.isEmpty(album.getImage())){
                criteria.andLike("image","%"+album.getImage()+"%");
            }
            //图片列表
            if(StringUtil.isEmpty(album.getImageItems())){
                criteria.andLike("image_items","%"+album.getImageItems()+"%");
            }
        }
        return example;
    }
}
