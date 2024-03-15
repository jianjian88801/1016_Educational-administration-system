package com.jubilantz.controller;

import com.jubilantz.entity.EasBaseCourse;
import com.jubilantz.services.EasBaseCourseService;
import com.jubilantz.services.EasCourseService;
import com.jubilantz.services.EasStudentService;
import com.jubilantz.services.EasTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author JubilantZ
 * @Date: 2021/4/29 18:29
 */
@RequestMapping("/easEchart")
@Controller
public class EasEchartController {
    @Autowired
    private EasStudentService easStudentService;
    @Autowired
    private EasTeacherService easTeacherService;
    @Autowired
    private EasCourseService easCourseService;
    @Autowired
    private EasBaseCourseService easBaseCourseService;

    @RequestMapping("/scoreEchart")
    public String scoreEchart(){
        return "echarts/ScoreEcharts";
    }

    @RequestMapping("/peopleEchart")
    public String peopleEchart(){
        return "echarts/peopleEcharts";
    }

    @RequestMapping("/getAllStuAndTea")
    @ResponseBody
    public Map<String,Object> getAllStuAndTea(){
        Map<String, Object> map = new HashMap<>();

        int totalStu = easStudentService.getTotal();
        int totalTea = easTeacherService.getTotal();

//        System.out.println("教师总行数---->"+totalTea);

        map.put("totalStu",totalStu);
        map.put("totalTea",totalTea);
        map.put("code",0);
        map.put("msg","我是返回的内容");

        return map;
    }

    @RequestMapping("/getAllSex")
    @ResponseBody
    public Map<String,Object> getAllSex(){
        Map<String, Object> map = new HashMap<>();

        int totalMan = easStudentService.getTotalSex("男");
        int totalWoman = easStudentService.getTotalSex("女");
        map.put("totalMan",totalMan);
        map.put("totalWoman",totalWoman);
        map.put("code",0);
        map.put("msg","我是返回的内容");

        return map;
    }


    @RequestMapping("/getAllClassScore")
    @ResponseBody
    public Map<String,Object> getAllClassScore(Integer baseCourseId) throws Exception {
        Map<String, Object> map = new HashMap<>();
//        System.out.println("基础课程id为:"+baseCourseId);

        //根据基本课程id 和是否结束 来获取每门课程 合格条数 和不合格条数
        EasBaseCourse easBaseCourse = easBaseCourseService.getBaseCourseById(baseCourseId);
        String coursename = easBaseCourse.getCoursename();
        int totalPass = easCourseService.getTotalPass(baseCourseId);
        int totalNoPass = easCourseService.getTotalNoPass(baseCourseId);
//        if(totalPass != 0 && totalNoPass !=0 ){
        if(totalPass != 0 || totalNoPass != 0 ){
            map.put("coursename",coursename);
            map.put("totalPass",totalPass);
            map.put("totalNoPass",totalNoPass);
//            System.out.println("通过人数:"+totalPass);
//            System.out.println("未通过人数:"+totalNoPass);
//            System.out.println("coursename:"+coursename);

        }else {
            map.put("coursename",coursename);
            map.put("totalPass",0);
            map.put("totalNoPass",0);

//            System.out.println("通过人数:"+totalPass);
//            System.out.println("未通过人数:"+totalNoPass);
        }

        return map;
    }
}
