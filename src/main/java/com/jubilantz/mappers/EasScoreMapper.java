package com.jubilantz.mappers;

import com.jubilantz.entity.EasCourse;
import com.jubilantz.entity.EasScore;
import com.jubilantz.utils.PageUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/25 20:13
 */
@Mapper
public interface EasScoreMapper {
    int insertSelective(EasScore easScore);

    int deleteScore(EasScore easScore);

    int updateScore(EasScore easScore);

    int updateScoreByScoreList(List<EasScore> scoreList);

    int getTotalItemsCount(@Param("sId") int sId,@Param("result") Integer result);

    List<EasCourse> getCourseListBySid(@Param("sId") int sId,@Param("result") Integer result,@Param("pageUtil") PageUtil pageUtil);

    EasScore getcIdById(int id);


    Date getStartDateByCourseId(Integer courseId);
}
