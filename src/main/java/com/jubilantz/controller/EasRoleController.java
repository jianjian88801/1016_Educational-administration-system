package com.jubilantz.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jubilantz.entity.EasBaseCourse;
import com.jubilantz.entity.EasRole;
import com.jubilantz.mappers.EasRoleMapper;
import com.jubilantz.utils.PageUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/easRole")
public class EasRoleController {
    @Autowired
    private EasRoleMapper easRoleMapper;

    @RequestMapping("/search")
    @ResponseBody
    public List<EasRole> search() throws Exception{
        return easRoleMapper.getAll();
    }

    @RequestMapping("/index")
    @RequiresPermissions("role:query")
    public String index() throws Exception{
        return "system/role/index";
    }

    @RequestMapping("/rolePers")
    @ResponseBody
    public List<Long> rolePers(Integer id) throws Exception {
        return easRoleMapper.getPerIdsByRoleId(id);
    }

    @RequestMapping("/assignPers")
    @ResponseBody
    public Map<String,Object> assignPers(Integer roleId, String persIds) throws Exception{
        Map<String,Object> map = new HashMap<>();
        easRoleMapper.deleteRolePermissions(roleId);
        easRoleMapper.addRolePermissions(roleId,persIds.split(","));
        return map;
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> list(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit,
                                   EasRole easRole) throws Exception {
        Map<String,Object> map = new HashMap<>();

        int count = easRoleMapper.getCount();
        PageUtil pageUtil = new  PageUtil(page,limit);

        map.put("code",0);
        map.put("msg",null);
        map.put("data",easRoleMapper.getList(easRole,pageUtil));
        map.put("count",count);

        return map;
    }


    @RequestMapping("/roleForm")
    public String roleForm() throws Exception {
        return "system/role/roleForm";
    }

    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addRole(EasRole easRole) throws Exception{
        Map<String,Object> map = new HashMap<>();

//        System.out.println("角色名称:"+easRole.getName());
//        System.out.println("角色是否可用:"+easRole.getAvailable());

        List<EasRole> list = easRoleMapper.findRoleName(easRole.getName());

        if (list.size() != 0){
            map.put("msg","角色已存在");
            map.put("result",false);
        }else if(easRole.getName().length() <= 0){
            map.put("msg","角色名称不能为空");
            map.put("result",false);
        }else{
            //课程为null也可以添加 待完善
            easRoleMapper.addRole(easRole);
            map.put("msg","添加成功");
            map.put("result",true);
        }
        return map;
    }

    @RequestMapping("/batchDeleteRole")
    @ResponseBody
    @RequiresPermissions("role:delete")
    public Map<String, Object> batchDeleteRole(Integer[] ids) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        easRoleMapper.batchDeleteRole(ids);
        map.put("msg","删除成功");
        map.put("result",true);
        return map;
    }

    @RequestMapping(value = "/getRoleView")
    @ResponseBody
    public EasRole getRoleView(Integer id) throws Exception {
        return easRoleMapper.getRoleView(id);
    }

    @RequestMapping(value = "/editRole",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editRole(EasRole easRole) throws Exception{
        Map<String, Object> map = new HashMap<>();

        easRoleMapper.updateBaseCourse(easRole);

        map.put("result",true);
        return map;
    }
}
