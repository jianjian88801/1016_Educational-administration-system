package com.jubilantz.mappers;

import com.jubilantz.entity.EasStudent;
import com.jubilantz.entity.EasTeacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/16 15:19
 */
@Mapper
public interface EasTeacherMapper {

    //包含两个一对一关系
    List<EasTeacher> findTeacherList(EasTeacher easTeacher);

    List<EasTeacher> findListByUsername(String username);

    EasTeacher getTeacherByUsername(String username);

    void updateTeacher(EasTeacher easTeacher);

    List<EasTeacher> getAll();

    EasTeacher findTeacherByUsername(String username);

    int getTotal();

    void addUsername(String username);

    void deleteTeacher(String username);
}
