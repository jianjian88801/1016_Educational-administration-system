package com.jubilantz.services.impl;

import com.jubilantz.entity.EasClass;
import com.jubilantz.mappers.EasClassMapper;
import com.jubilantz.services.EasClassService;
import com.jubilantz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/15 12:30
 */
@Service
public class EasClassServiceImpl implements EasClassService {

    @Autowired
    private EasClassMapper easClassMapper;

    @Override
    public List<EasClass> getAll() {
        return easClassMapper.getAll();
    }

    @Override
    public int getCount() {
        return easClassMapper.getCount();
    }

    @Override
    public List<EasClass> getList(EasClass easClass, PageUtil pageUtil) {
        return easClassMapper.getList(easClass,pageUtil);
    }

    @Override
    public List<EasClass> findClassName(String classes) {
        return easClassMapper.findClassName(classes);
    }

    @Override
    public void addClass(EasClass easClass) {
        easClassMapper.addClass(easClass);
    }

    @Override
    public void batchDeleteClass(Integer[] ids) {
        easClassMapper.batchDeleteClass(ids);
    }

    @Override
    public EasClass getClassView(Integer id) {
        return easClassMapper.getClassView(id);
    }

    @Override
    public void updateClass(EasClass easClass) {
        easClassMapper.updateClass(easClass);
    }
}
