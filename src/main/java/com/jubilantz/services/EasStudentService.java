package com.jubilantz.services;

import com.jubilantz.entity.EasStudent;
import com.jubilantz.utils.PageUtil;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/14 10:05
 */
public interface EasStudentService {
    List<EasStudent> getList(EasStudent easStudent) throws Exception;

    List<EasStudent> findList(EasStudent easStudent) throws Exception;

    List<EasStudent> findListByUsername(String username) throws Exception;

    EasStudent getStudentByUsername(String username) throws Exception;

    void updateStudent(EasStudent easStudent) throws Exception;

    int getCountBytIdandcId(Integer tId, Integer baseCourseId,Integer classId) throws Exception;

    int getEndingCountBytIdandcId(Integer tId, Integer baseCourseId, Integer classId);

    List<EasStudent> getStudentScoreListByTid(Integer tId, Integer baseCourseId,Integer classId, PageUtil pageUtil);

    List<EasStudent> getStudentSelectCourseListByTid(Integer tId, Integer baseCourseId, Integer classId, PageUtil pageUtil);


    int getTotal();

    int getTotalSex(String sex);

    void addUsername(String username)  throws Exception;

    void deleteStudent(String username) throws Exception;
}
