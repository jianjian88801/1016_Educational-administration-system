package com.jubilantz.controller;

import com.alibaba.fastjson.JSON;
import com.jubilantz.entity.*;
import com.jubilantz.services.EasScoreService;
import com.jubilantz.services.EasStudentService;
import com.jubilantz.services.EasTeacherService;
import com.jubilantz.utils.PageUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author JubilantZ
 * @Date: 2021/4/25 20:14
 */
@RequestMapping("/easScore")
@Controller
public class EasScoreController {
    @Autowired
    private EasScoreService easScoreService;
    @Autowired
    private EasStudentService easStudentService;
    @Autowired
    private EasTeacherService easTeacherService;

    //教师查看选我课的学生成绩列表 进行打分
    @RequestMapping("/scoreIndex")
    public String scoreIndex() throws Exception {
        return "system/score/studentScoreIndex";
    }

    //学生查看我的成绩页面
    @RequestMapping("/myScoreIndex")
    public String myScoreIndex() throws Exception {
        return "system/score/myScoreIndex";
    }

    //教师查看我的学生选课信息
    @RequestMapping("/myStudentIndex")
    public String myStudentIndex() throws Exception {
        return "system/score/myStudentIndex";
    }

    /**
     * 学生选课
     * @param courseId
     * @return
     * @throws Exception
     */
    @RequestMapping("/choiceCourse")
    @ResponseBody
    public Map<String, Object> choiceCourse(@RequestParam(defaultValue="")Integer courseId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (courseId != null) {
            //获取学生信息
            EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
            String username = easUser.getUsername();
            EasStudent easStudent = easStudentService.getStudentByUsername(username);

//            System.out.println("学生用户名为:"+username);

            if (easStudent.getUsername() == null || easStudent.getUsername().equals("")){
                map.put("result",false);
                map.put("msg","出错了！");
            }else {

                EasScore easScore = new EasScore();
                easScore.setsId(easStudent.getId());
                easScore.setcId(courseId);

                //使用Date创建日期对象
                Date nowDate = new Date();

                //获取当前课程的开始日期
                Date startDate = easScoreService.getStartDateByCourseId(courseId);

                if(nowDate.getTime() > startDate.getTime()){
                    map.put("result",false);
                    map.put("msg","已经开课，无法选课！");
                }else{
                    int res = easScoreService.choiceCourse(easScore);

                    if (res > 0) {
                        map.put("result",true);
                        map.put("msg","选课成功！");
                    }else {
                        map.put("result",false);
                        map.put("msg","人数已满，选课失败！");
                    }
                }
//
//                int res = easScoreService.choiceCourse(easScore);
//
//                if (res > 0) {
//                    map.put("result",true);
//                    map.put("msg","选课成功！");
//                }else {
//                    map.put("result",false);
//                    map.put("msg","人数已满，选课失败！");
//                }
            }
        }else {
            map.put("result",false);
            map.put("msg","选课失败，请联系管理员！");
        }

        return map;
    }

    /**
     * 学生退课
     * @param courseId
     * @return
     * @throws Exception
     */
    @RequestMapping("/outCourse")
    @ResponseBody
    public Map<String, Object> outCourse(@RequestParam(defaultValue="")Integer courseId) throws Exception {
        Map<String, Object> map = new HashMap<>();

        if (courseId != null) {

            EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
            String username = easUser.getUsername();
            EasStudent easStudent = easStudentService.getStudentByUsername(username);

            if (easStudent.getUsername() == null || easStudent.getUsername().equals("")){
                map.put("result",false);
                map.put("msg","出错了！");
            }else{
                EasScore easScore = new EasScore();
                easScore.setsId(easStudent.getId());
                easScore.setcId(courseId);

                //使用Date创建日期对象
                Date nowDate=new Date();
//                System.out.println("当前的日期是------>"+nowDate);

                /**
                 * 创建格式化时间日期类
                 *构造入参String类型就是我们想要转换成的时间形式
                 */
//                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
//                System.out.println("格式化后的时间------->"+format.format(date));
//                String ds=df.format(d);

                //获取当前课程的开始日期
                Date startDate = easScoreService.getStartDateByCourseId(courseId);

//                System.out.println("开始日期为------>"+startDate);

                if(nowDate.getTime() > startDate.getTime()){
                    map.put("result",false);
                    map.put("msg","已经开课，无法退课！");
                }else{
                    int res = easScoreService.deleteScore(easScore);

                    if (res > 0) {
                        map.put("result",true);
                        map.put("msg","退选成功！");
                    }else {
                        map.put("result",false);
                        map.put("msg","退课失败！");
                    }
                }

//                //删除成绩信息时 ，同时将课程表中该课程的人数-1 该方法在service中
//                int res = easScoreService.deleteScore(easScore);
//
//                if (res > 0) {
//                    map.put("result",true);
//                    map.put("msg","退选成功！");
//                }else {
//                    map.put("result",false);
//                    map.put("msg","退课失败！");
//                }
            }

        }else {
            map.put("result",false);
            map.put("msg","退课失败，请联系管理员！");
        }

        return map;
    }

    /**
     * 返回选修了我(教师)已结束课程的学生列表
     * @param page
     * @param limit
     * @param baseCourseId
     * @param classId
     * @return
     * @throws Exception
     */
    @RequestMapping("/stuScoreList")
    @ResponseBody
    public Map<String, Object> stuScoreList(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer limit,
                                            @RequestParam(required=false) Integer baseCourseId,
                                            @RequestParam(required=false) Integer classId) throws Exception {
    //    @requestparam(required = false)不传值后台也不会报错

        Map<String, Object> map = new HashMap<>();
        //获取当前教师id
        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
        String username = easUser.getUsername();
        EasTeacher easTeacher = easTeacherService.findTeacherByUsername(username);

        //获取行数
        int count = easStudentService.getEndingCountBytIdandcId(easTeacher.getId(),baseCourseId,classId);
        PageUtil pageUtil = new  PageUtil(page,limit);


        List<EasStudent> list = easStudentService.getStudentScoreListByTid(easTeacher.getId(),baseCourseId,classId,pageUtil);

        map.put("count",count);
        map.put("data",list);
        map.put("code",0);
        map.put("msg","");

        return map;
    }

    /**
     * 获取我的学生选课信息
     * @param page
     * @param limit
     * @param baseCourseId
     * @param classId
     * @return
     * @throws Exception
     */
    @RequestMapping("/stuSelectCourseList")
    @ResponseBody
    public Map<String, Object> stuSelectCourseList(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer limit,
                                            @RequestParam(required=false) Integer baseCourseId,
                                            @RequestParam(required=false) Integer classId) throws Exception {
        //    @requestparam(required = false)不传值后台也不会报错

        Map<String, Object> map = new HashMap<>();
        //获取当前教师id
        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
        String username = easUser.getUsername();
        EasTeacher easTeacher = easTeacherService.findTeacherByUsername(username);

        //获取行数
        int count = easStudentService.getCountBytIdandcId(easTeacher.getId(),baseCourseId,classId);
        PageUtil pageUtil = new  PageUtil(page,limit);


        List<EasStudent> list = easStudentService.getStudentSelectCourseListByTid(easTeacher.getId(),baseCourseId,classId,pageUtil);

        map.put("count",count);
        map.put("data",list);
        map.put("code",0);
        map.put("msg","");

        return map;
    }


    @RequestMapping("/updateScore")
    @ResponseBody
    public Map<String, Object> updateScore(EasScore easScore) throws Exception {
        Map<String, Object> map = new HashMap<>();

        int res = easScoreService.updateScore(easScore);
        if (res > 0){
            map.put("result",true);
            map.put("msg","评分成功");
        }else {
            map.put("result",false);
            map.put("msg","课程还未结束,评分失败");
        }

        return map;
    }

    @RequestMapping("/updateScoreList")
    @ResponseBody
    public Map<String, Object> updateScoreList(String scoreListStr) throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<EasScore> scoreList = JSON.parseArray(scoreListStr,EasScore.class);
//        System.out.println("我是scoreList"+scoreList);

        int res = easScoreService.updateScoreByScoreList(scoreList);
        if (res > 0){
            map.put("result",true);
            map.put("msg","批量评分成功");
        }else {
            map.put("result",false);
            map.put("msg","批量评分失败，请联系管理员！");
        }


        return map;
    }

    @RequestMapping("/myScore")
    @ResponseBody
    public Map<String, Object> myScore(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer limit,
                                        Integer result) throws Exception {
        Map<String, Object> map = new HashMap<>();

        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
        String username = easUser.getUsername();
        EasStudent easStudent = easStudentService.getStudentByUsername(username);

        PageUtil pageUtil = new PageUtil(page,limit);
        int sId = easStudent.getId();
        int count = easScoreService.getTotalItemsCount(sId,result);

        List<EasCourse> list = easScoreService.getCourseListBySid(sId,result,pageUtil);

        map.put("count",count);
        map.put("data",list);
        map.put("code",0);
        map.put("msg","");

        return map;
    }


}
