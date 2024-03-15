package com.jubilantz.controller;

import com.jubilantz.entity.EasClass;
import com.jubilantz.services.EasClassService;
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

/**
 * @Author JubilantZ
 * @Date: 2021/4/15 12:35
 */
@Controller
@RequestMapping("/easClass")
public class EasClassController {
    @Autowired
    private EasClassService easClassService;

    @RequestMapping("/search")
    @ResponseBody
    public List<EasClass> search() throws Exception{
        return easClassService.getAll();
    }

    @RequestMapping("/index")
    public String index() throws Exception {
        return "system/class/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> list(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit,
                                   EasClass easClass) throws Exception{
        Map<String,Object> map = new HashMap<>();
        int count = easClassService.getCount();
//        System.out.println("总行数:"+count);
        PageUtil pageUtil = new  PageUtil(page,limit);
        List<EasClass> list = easClassService.getList(easClass,pageUtil);

        map.put("count",count);
        map.put("data",list);
        map.put("code",0);
        map.put("msg","");

        return map;
    }

    @RequestMapping("/classForm")
    public String classForm() throws Exception {
        return "system/class/classForm";
    }

    @RequestMapping(value = "/addClass",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addClass(EasClass easClass) throws Exception{
        Map<String,Object> map = new HashMap<>();

//        System.out.println("我是基本课程名称:"+easBaseCourse.getCoursename());
//        System.out.println("我是基本课程简介:"+easBaseCourse.getSynopsis());
        List<EasClass> list = easClassService.findClassName(easClass.getClasses());

        if (list.size() != 0){
            map.put("msg","班级已存在");
            map.put("result",false);
        }else if(easClass.getClasses().length() <= 0){
            map.put("msg","班级不能为空");
            map.put("result",false);
        }else{
            //课程为null也可以添加 待完善
            easClassService.addClass(easClass);
            map.put("msg","添加成功");
            map.put("result",true);
        }
        return map;
    }

    @RequestMapping("/batchDeleteClass")
    @ResponseBody
    @RequiresPermissions("class:delete")
    public Map<String, Object> batchDeleteClass(Integer[] ids) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
//        System.out.println("前台传来的为:"+ids);
        easClassService.batchDeleteClass(ids);
        map.put("msg","删除成功");
        map.put("result",true);
        return map;
    }

    @RequestMapping(value = "/getClassView")
    @ResponseBody
    public EasClass getClassView(Integer id) throws Exception {
        return easClassService.getClassView(id);
    }

    @RequestMapping(value = "/editClass",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editClass(EasClass easClass) throws Exception{
        Map<String, Object> map = new HashMap<>();

        easClassService.updateClass(easClass);

        map.put("result",true);
        return map;
    }
}
