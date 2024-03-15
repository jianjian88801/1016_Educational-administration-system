package com.jubilantz.controller;

import com.jubilantz.entity.EasPermission;
import com.jubilantz.mappers.EasPermissionMapper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/easPermission")
public class EasPermissionController {
    @Autowired
    private EasPermissionMapper easPermissionMapper;

    @RequestMapping("/index")
    @RequiresPermissions("permission:query")
    public String index() throws Exception{
        return "system/permission/index";
    }

    @RequestMapping("/parentList")
    @ResponseBody
    public List<EasPermission> parentList() {
        return easPermissionMapper.getParentPers();
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> list() throws Exception {
        Map<String,Object> map = new HashMap<>();

        map.put("code",0);
        map.put("msg",null);
        map.put("data",easPermissionMapper.getAll());

        return map;
    }
}
