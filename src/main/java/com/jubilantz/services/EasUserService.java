package com.jubilantz.services;

import com.jubilantz.entity.EasUser;
import com.jubilantz.mappers.EasUserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/12 16:26
 */
public interface EasUserService {

    void addUser(EasUser user) throws Exception;

    String findUsernameById(int userid) throws Exception;

    void addUserRole(EasUser user, Integer[] ids) throws Exception;

    void deleteUserRole(Integer id) throws Exception;

    void updateUser(EasUser user) throws Exception;

    List<EasUser> findUserName(String username) throws Exception;

    Integer findRoleIdByUserId(Integer id) throws Exception;

    int getCount();

    List<Integer> findRoleIdByUserId2(Integer id);

    int getRoleCountByUid(Integer Uid)throws Exception;
}
