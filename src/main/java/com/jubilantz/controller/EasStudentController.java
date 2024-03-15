package com.jubilantz.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jubilantz.entity.EasClass;
import com.jubilantz.entity.EasRole;
import com.jubilantz.entity.EasStudent;
import com.jubilantz.entity.EasUser;
import com.jubilantz.services.EasClassService;
import com.jubilantz.services.EasStudentService;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.ibatis.annotations.Param;
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
 * @Date: 2021/4/14 10:11
 */
@Controller
@RequestMapping("/easStudent")
public class EasStudentController {
    @Autowired
    private EasStudentService easStudentService;


    @RequestMapping("/index")
    public String index() throws Exception{
        return "system/student/index";
    }



    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> list(@RequestParam(defaultValue = "1") Integer page,
                                    @RequestParam(defaultValue = "10") Integer limit,
                                    EasStudent easStudent) throws Exception{
        Map<String, Object> map = new HashMap<>();

//        System.out.println("我是:" + easStudent.getClass_id());

        Page<EasStudent> pager = PageHelper.startPage(page,limit);
//        List<EasStudent> list = easStudentService.getList(easStudent);
        List<EasStudent> list = easStudentService.findList(easStudent);
//        System.out.println("获取信息总条数为:" + list.size());
//        for (EasStudent e:list
//             ) {
//            System.out.println(e.getUser().getUsername());
//            System.out.println(e.getName());
//            System.out.println(e.getClass_id());
//        }
        map.put("count",pager.getTotal());
        map.put("data",list);
        map.put("code",0);
        map.put("msg","");

        return map;
    }

}
