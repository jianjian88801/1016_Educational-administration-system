package com.jubilantz.controller;

import com.jubilantz.entity.EasCourse;
import com.jubilantz.entity.EasStudent;
import com.jubilantz.entity.EasTeacher;
import com.jubilantz.entity.EasUser;
import com.jubilantz.mappers.EasCourseMapper;
import com.jubilantz.services.EasCourseService;
import com.jubilantz.services.EasStudentService;
import com.jubilantz.services.EasTeacherService;
import com.jubilantz.utils.PageUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author JubilantZ
 * @Date: 2021/4/21 21:40
 */
@Controller
@RequestMapping("/easCourse")
public class EasCourseController {
    @Autowired
    private EasCourseService easCourseService;
    @Autowired
    private EasTeacherService easTeacherService;
    @Autowired
    private EasStudentService easStudentService;


    @RequestMapping("/adminIndex")
    @RequiresPermissions("course:adminIndex")
    public String adminIndex() throws Exception {
        return "system/course/adminCourseIndex";
    }
    @RequestMapping("/studentIndex")
    @RequiresPermissions("course:studentIndex")
    public String studentIndex() throws Exception {
        return "system/course/studentCourseIndex";
    }
    @RequestMapping("/teacherIndex")
    @RequiresPermissions("course:teacherIndex")
    public String teacherIndex() throws Exception {
        return "system/course/teacherCourseIndex";
    }

    @RequestMapping("/courseAddForm")
    public String courseAddForm() throws Exception{
        return "system/course/courseAddForm";
    }

    @RequestMapping("/courseEditForm")
    public String courseEditForm() throws Exception{
        return "system/course/courseEditForm";
    }

    @RequestMapping("/getCourseList")
    @ResponseBody
    public Map<String,Object> getCourseList(@RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer limit,
                                            EasCourse easCourse) throws Exception{
        Map<String,Object> map = new HashMap<>();
        int count = easCourseService.getCount();
//        System.out.println("总行数:"+count);
        PageUtil pageUtil = new  PageUtil(page,limit);
        List<EasCourse> list = easCourseService.getList(easCourse,pageUtil);

        map.put("count",count);
        map.put("data",list);
        map.put("code",0);
        map.put("msg","");

        return map;
    }


    @RequestMapping("/baseCourseAddForm")
    public String baseCourseAddForm() throws Exception {
        return "system/baseCourse/baseCourseAddForm";
    }

    @RequestMapping(value = "/addCourse",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addCourse(EasCourse easCourse) throws Exception{
        Map<String,Object> map = new HashMap<>();

//        System.out.println("课程id:"+easCourse.getBaseCourseId());
//        System.out.println("教师id:"+easCourse.gettId());

        int res = 0;
        //查询是否有课程对应老师
        List<EasCourse> list = easCourseService.findCourseByBaseCourseIdAndTeacherId(easCourse.getBaseCourseId(),easCourse.gettId());

        if(easCourse.gettId() == null || easCourse.getBaseCourseId() == null){
            map.put("msg","课程名称和教师姓名不能为空");
            map.put("result",false);;
        } else if (list.size() != 0){
            //判断老师是否已经选择该课程
            map.put("msg","课程已存在");
            map.put("result",false);;
        }
        else if(easCourse.getId() == null || easCourse.getId().equals("")){
            try {
                res = easCourseService.addCourse(easCourse);
            } catch (Exception e) {
                e.printStackTrace();
//                System.out.println("添加失败！");
                map.put("msg","添加失败");
                map.put("result",false);;
            }
            if (res > 0){
                map.put("msg","添加成功");
                map.put("result",true);
            }else {
                map.put("msg", "添加失败");
                map.put("result", false);
            }
        }else{
            map.put("msg","添加失败,ID已经存在,请联系管理员");
            map.put("result",false);
        }

        return map;
    }

    @RequestMapping(value = "/getCourseById")
    @ResponseBody
    public EasCourse getCourseById(Integer id) throws Exception {
        EasCourse easCourse = easCourseService.getCourseById(id);

//        System.out.println("课程id为:"+id);
//        System.out.println("根据id查到的课程名称为:"+easCourse.getCourseName());

        return easCourse;
    }

    @RequestMapping(value = "/editCourse",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> editCourse(EasCourse easCourse) throws Exception{
        Map<String, Object> map = new HashMap<>();

//        System.out.println("id到底是不是null:"+easCourse.getId());
//        System.out.println("开始日期:"+easCourse.getStartDate());
//        System.out.println("结束日期:"+easCourse.getEndDate());

        if(easCourse.getId() != null){
            easCourseService.updateCourseById(easCourse);
//                easCourseMapper.updateDate(easCourse.getId(),startDate,endDate);

            map.put("result",true);
        }else{
            map.put("result",false);
        }

        return map;
    }


    @RequestMapping("/batchDeleteCourse")
    @ResponseBody
    @RequiresPermissions("course:adminDelete")
    public Map<String, Object> batchDeleteCourse(Integer[] ids) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            easCourseService.batchDeleteCourse(ids);
            map.put("msg","删除成功");
            map.put("result",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result",false);
        }

        return map;
    }

    /**
     * 返回教师自己教的课程列表
     */
    @ResponseBody
    @RequestMapping(value="/getMyCourseList")
    public Map<String, Object> getMyCourseList(@RequestParam(defaultValue = "1") Integer page,
                                               @RequestParam(defaultValue = "10") Integer limit) {
        Map<String,Object> map = new HashMap<>();
        //获取用户信息
        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
        String username = easUser.getUsername();
//        System.out.println("教师用户名为:"+username);
        EasTeacher easTeacher = easTeacherService.findTeacherByUsername(username);
        if (easTeacher.getUsername() == null || easTeacher.getUsername().equals("")){
            map.put("code",1);
            map.put("msg","您还没有教授课程");
        }else{
//            System.out.println("教师id为:"+easTeacher.getId());
            int count = easCourseService.getCountBytId(easTeacher.getId());

            PageUtil pageUtil = new  PageUtil(page,limit);
            List<EasCourse> list = easCourseService.getCourseListBytId(easTeacher.getId(),pageUtil);

            map.put("count",count);
            map.put("data",list);
            map.put("code",0);
            map.put("msg","");
        }

        return map;
    }


    @RequestMapping(value="/complete")
    @ResponseBody
    public Map<String, Object> complete(EasCourse easCourse) {
        Map<String, Object> map = new HashMap<>();
        if(easCourse.getComplete() == 1){
            map.put("code",1);
            map.put("msg","课程已结束,无需再结束");

        }else{
            int res = easCourseService.completeCourse(easCourse);
            if (res > 0) {
                map.put("code",0);
            }else{
                map.put("code",2);
                map.put("msg","出错了");
            }
        }

        return map;
    }

    /**
     * 返回可选课程列表（可选：人数未满、课程开始时间在当前时间之后）
     * @param page
     * @param limit
     * @param isAll
     * @param searchKey
     * @return
     */
    @RequestMapping(value="/choiceList")
    @ResponseBody
    public Map<String, Object> getCourseChoiceList(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer limit,
                                      @RequestParam(defaultValue="1") int isAll,
                                      @RequestParam(defaultValue="")String searchKey) throws Exception {
        Map<String, Object> map = new HashMap<>();

        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
        String username = easUser.getUsername();
//        System.out.println("教师用户名为:"+username);
        EasStudent easStudent = easStudentService.getStudentByUsername(username);
        if (easStudent.getUsername() == null || easStudent.getUsername().equals("")){
            map.put("code",1);
            map.put("msg","目前还没有选课信息");
        }else{
            PageUtil pageUtil = new PageUtil(page,limit);
            int sId = easStudent.getId();
            int count = easCourseService.getTotalItemsCountBySid(isAll, searchKey, sId);

            List<EasCourse> list = easCourseService.getCourseListBySid(isAll, searchKey, sId,pageUtil);

            map.put("count",count);
            map.put("data",list);
            map.put("code",0);
            map.put("msg","");
        }

        return map;
    }

}
