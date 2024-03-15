package com.jubilantz.mappers;

import com.jubilantz.entity.EasRole;
import com.jubilantz.utils.PageUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EasRoleMapper{
    /**
     * 根据用户编号查询对应的角色信息
     * @param userId
     * @return
     */
    List<EasRole> getByUserId(Integer userId);

    /**
     * 根据角色编号查询对应的权限编号集合
     * @param roleId
     * @return
     */
    List<Long> getPerIdsByRoleId(Integer roleId);

    void deleteRolePermissions(Integer roleId);

    void addRolePermissions(@Param("roleId") Integer roleId, @Param("perIds") String[] perIds);

    List<EasRole> getAll();

    List<EasRole> getList(@Param("easRole") EasRole easRole,@Param("pageUtil") PageUtil pageUtil);

    String findRoleNameByRoleId(Integer roleid);

    int getCount();

    List<EasRole> findRoleName(String name);

    void addRole(EasRole easRole);

    void batchDeleteRole(Integer[] ids);

    EasRole getRoleView(Integer id);

    void updateBaseCourse(EasRole easRole);
}
