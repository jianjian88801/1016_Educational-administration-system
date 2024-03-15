package com.jubilantz.services;

import com.jubilantz.entity.EasRole;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/13 17:08
 */
public interface EasRoleService {
    List<EasRole> getAll();

    String findRoleNameByRoleId(Integer roleid) throws Exception;
}
