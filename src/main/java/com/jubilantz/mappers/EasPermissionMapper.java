package com.jubilantz.mappers;

import com.jubilantz.entity.EasPermission;
import com.jubilantz.entity.EasRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/7 10:51
 */
@Mapper
public interface EasPermissionMapper {
    /**
     * 查询所有的父权限，子权限要放到父权限的children集合属性中返回
     * @return
     */
    List<EasPermission> getParentPers();

    /**
     * 根据登录用户编号查询拥有的权限信息，
     * @param userId
     * @return
     */
    List<EasPermission> getPersByUserId(Integer userId);

    List<EasPermission> getAll();

}
