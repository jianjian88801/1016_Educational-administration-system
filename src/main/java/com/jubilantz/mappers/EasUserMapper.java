package com.jubilantz.mappers;

import com.jubilantz.entity.EasUser;
import com.jubilantz.utils.PageUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/7 10:49
 */
@Mapper
public interface EasUserMapper{

    void addUserRole(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);

    void deleteUserRole(Integer id);

    EasUser getByUserName(String username);

    List<EasUser> getList(@Param("easUser") EasUser easUser, @Param("pageUtil") PageUtil pageUtil);

    EasUser get(Integer id);

    void add(EasUser user);

    void delete(Integer id);

    void batchDelete(Integer[] ids);

    void updateUser(EasUser user);

    String findUsernameById(Integer userid);

    List<EasUser> findUserName(String username);

    Integer findRoleIdByUserId(@Param("userid") Integer userid);

    int getCount();

    List<Integer> findRoleIdByUserId2(@Param("userid") Integer userid);

    EasUser findUserById(@Param("id")Integer id);

    int getRoleCountByUid(@Param("userid")Integer userid);
}
