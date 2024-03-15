package com.jubilantz.services;

import com.jubilantz.entity.EasCourse;
import com.jubilantz.entity.EasScore;
import com.jubilantz.utils.PageUtil;

import java.util.Date;
import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/25 20:12
 */
public interface EasScoreService {
    int choiceCourse(EasScore easScore);

    int deleteScore(EasScore easScore);

    int updateScore(EasScore easScore) throws Exception;

    int updateScoreByScoreList(List<EasScore> scoreList) throws Exception;

    int getTotalItemsCount(int sId, Integer result);

    List<EasCourse> getCourseListBySid(int sId, Integer result, PageUtil pageUtil);

    Date getStartDateByCourseId(Integer courseId) throws Exception;
}
