package com.jubilantz.services.impl;

import com.jubilantz.entity.EasCourse;
import com.jubilantz.mappers.EasCourseMapper;

import com.jubilantz.services.EasCourseService;

import com.jubilantz.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/22 8:37
 */
@Service
public class EasCourseServiceImpl implements EasCourseService {
    @Autowired
    private EasCourseMapper easCourseMapper;


    @Override
    public int getCount() {
        return easCourseMapper.getCount();
    }

    @Override
    public List<EasCourse> getList(EasCourse easCourse, PageUtil pageUtil) throws Exception{
        return easCourseMapper.getList(easCourse,pageUtil);
    }

    @Override
    public List<EasCourse> findCourseByBaseCourseIdAndTeacherId(Integer baseCourseId, Integer tId) {
        return easCourseMapper.findCourseByBaseCourseIdAndTeacherId(baseCourseId,tId);
    }

    @Override
    public int addCourse(EasCourse easCourse) {
        if (easCourse.getStartDate() != null && easCourse.getStartDate().equals("")) {
            easCourse.setStartDate(null);
        }
        if (easCourse.getEndDate() != null && easCourse.getEndDate().equals("")) {
            easCourse.setEndDate(null);
        }
        return easCourseMapper.addCourse(easCourse);
    }

    @Override
    public EasCourse getCourseById(Integer id) throws Exception{
        return easCourseMapper.getCourseById(id);
    }

    @Override
    public void updateCourseById(EasCourse easCourse) {
        easCourseMapper.updateCourseById(easCourse);
    }

    @Override
    public void batchDeleteCourse(Integer[] ids) {
        easCourseMapper.batchDeleteCourse(ids);
    }

    @Override
    public int getCountBytId(Integer tId) {
        return easCourseMapper.getCountBytId(tId);
    }

//    @Override
//    public int getCountBytId(Integer tId) {
//        return easCourseMapper.getCountByUsername(tId);
//    }

    @Override
    public List<EasCourse> getCourseListBytId(Integer tId, PageUtil pageUtil) {
        return easCourseMapper.getCourseListBytId(tId,pageUtil);
    }

    @Override
    public int completeCourse(EasCourse easCourse) {
        EasCourse c = new EasCourse();
        c.setId(easCourse.getId());
        c.setComplete(1);
        return easCourseMapper.completeCourse(c);
    }

    @Override
    public int getTotalItemsCountBySid(int isAll, String searchKey, int sId) {
        return easCourseMapper.getTotalItemsCountBySid(isAll,searchKey,sId);
    }

    @Override
    public List<EasCourse> getCourseListBySid(int isAll, String searchKey, int sId, PageUtil pageUtil) {
        return easCourseMapper.getCourseListBySid(isAll,searchKey,sId,pageUtil);
    }

    @Override
    public int getTotalPass(Integer baseCourseId) {
        return easCourseMapper.getTotalPass(baseCourseId);
    }

    @Override
    public int getTotalNoPass(Integer baseCourseId) {
        return easCourseMapper.getTotalNoPass(baseCourseId);
    }

}
