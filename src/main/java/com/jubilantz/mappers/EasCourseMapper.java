package com.jubilantz.mappers;

import com.jubilantz.entity.EasCourse;
import com.jubilantz.utils.PageUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/22 9:38
 */
@Mapper
public interface EasCourseMapper {

    int getCount();

    List<EasCourse> getList(@Param("easCourse") EasCourse easCourse,@Param("pageUtil") PageUtil pageUtil);

    List<EasCourse> findCourseByBaseCourseIdAndTeacherId(@Param("baseCourseId") Integer baseCourseId,@Param("tId") Integer tId);

    int addCourse(EasCourse easCourse);

    EasCourse getCourseById(Integer id);

    void updateCourseById(EasCourse easCourse);

    void updateDate(@Param("id") Integer id,@Param("startDate") Date startDate,@Param("endDate") java.sql.Date endDate);

    void batchDeleteCourse(Integer[] ids);

    int getCountBytId(Integer tId);

    List<EasCourse> getCourseListBytId(@Param("tId")Integer tId,@Param("pageUtil") PageUtil pageUtil);

    int completeCourse(EasCourse easCourse);

    int getTotalItemsCountBySid(@Param("isAll") int isAll,@Param("searchKey") String searchKey,@Param("sId") int sId);

    List<EasCourse> getCourseListBySid(@Param("isAll") int isAll, @Param("searchKey") String searchKey, @Param("sId") int sId,@Param("pageUtil") PageUtil pageUtil);


    int findCompleteByCourseId(@Param("courseId") int courseId);

    int getTotalPass(@Param("baseCourseId") Integer baseCourseId);

    int getTotalNoPass(@Param("baseCourseId")Integer baseCourseId);
}
