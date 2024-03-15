package com.jubilantz.services.impl;

import com.jubilantz.entity.EasCourse;
import com.jubilantz.entity.EasNotice;
import com.jubilantz.mappers.EasNoticeMapper;
import com.jubilantz.services.EasNoticeService;
import com.jubilantz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/28 12:09
 */
@Service
public class EasNoticeServiceImpl implements EasNoticeService{
    @Autowired
    private EasNoticeMapper easNoticeMapper;

    @Override
    public int getCountByType(int type,String searchKey) throws Exception{
        return easNoticeMapper.getCountByType(type,searchKey);
    }

    @Override
    public List<EasNotice> getNoticeListByType(int type, String searchKey, PageUtil pageUtil) throws Exception {
        return easNoticeMapper.getNoticeListByType(type,searchKey,pageUtil);
    }

    @Override
    public int addNotice(EasNotice easNotice) throws Exception {
        return easNoticeMapper.addNotice(easNotice);
    }

    @Override
    public int updateNotice(EasNotice easNotice) throws Exception {
        return easNoticeMapper.updateNotice(easNotice);
    }

    @Override
    public int deleteNotice(EasNotice easNotice) {
        return easNoticeMapper.deleteNotice(easNotice);
    }

    @Override
    public int deleteNoticeList(List<Integer> list) {
        return easNoticeMapper.deleteNoticeList(list);
    }

    @Override
    public int getCountByTypeAndEasNotice(int type, EasNotice easNotice) {
        return easNoticeMapper.getCountByTypeAndEasNotice(type,easNotice);
    }

    @Override
    public List<EasNotice> getNoticeListByTypeAndEasNotice(int type, EasNotice easNotice, PageUtil pageUtil) {
        return easNoticeMapper.getNoticeListByTypeAndEasNotice(type,easNotice,pageUtil);
    }

    @Override
    public List<EasNotice> getNoticeById(Integer id) {
        return easNoticeMapper.getNoticeById(id);
    }
}
