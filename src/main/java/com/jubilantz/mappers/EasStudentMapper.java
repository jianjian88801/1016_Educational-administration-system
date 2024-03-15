package com.jubilantz.mappers;

import com.jubilantz.entity.EasStudent;
import com.jubilantz.utils.PageUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/14 9:48
 */
@Mapper
public interface EasStudentMapper {
//    EasStudent getByUsername(String username);
    //直接查找
    List<EasStudent> getList (EasStudent easStudent);

    //包含两个一对一关系
    List<EasStudent> findList(EasStudent easStudent);

    List<EasStudent> findListByUsername(String username);

    EasStudent getStudentByUsername(String username);

    void updateStudent(EasStudent easStudent);

    int getCountBytIdandcId(@Param("tId") Integer tId,@Param("baseCourseId") Integer baseCourseId,@Param("classId") Integer classId);

    int getEndingCountBytIdandcId(@Param("tId") Integer tId,@Param("baseCourseId") Integer baseCourseId,@Param("classId") Integer classId);

    List<EasStudent> getStudentScoreListByTid(@Param("tId") Integer tId,@Param("baseCourseId") Integer baseCourseId,
                                              @Param("classId") Integer classId,@Param("pageUtil") PageUtil pageUtil);

    List<EasStudent> getStudentSelectCourseListByTid(@Param("tId") Integer tId,@Param("baseCourseId") Integer baseCourseId,
                                                     @Param("classId") Integer classId,@Param("pageUtil") PageUtil pageUtil);


    int getTotal();

    int getTotalSex(String sex);

    void addUsername(String username);

    void deleteStudent(String username);
}
