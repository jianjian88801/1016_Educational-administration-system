package com.jubilantz.mappers;

import com.jubilantz.entity.EasClass;
import com.jubilantz.utils.PageUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/15 12:32
 */
@Mapper
public interface EasClassMapper {
    List<EasClass> getAll();

    int getCount();

    List<EasClass> getList(@Param("easClass") EasClass easClass,@Param("pageUtil") PageUtil pageUtil);

    List<EasClass> findClassName(String classes);

    void addClass(EasClass easClass);

    void batchDeleteClass(Integer[] ids);

    EasClass getClassView(Integer id);

    void updateClass(EasClass easClass);
}
