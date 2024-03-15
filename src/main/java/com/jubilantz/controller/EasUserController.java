package com.jubilantz.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jubilantz.entity.*;
import com.jubilantz.mappers.EasUserMapper;

import com.jubilantz.services.*;
import com.jubilantz.utils.PageUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/easUser")
public class EasUserController {
    @Autowired
    private EasUserMapper easUserMapper;
    @Autowired
    private EasUserService easUserService;
    @Autowired
    private EasClassService easClassService;
    @Autowired
    private EasStudentService easStudentService;
    @Autowired
    private EasTeacherService easTeacherService;
    @Autowired
    private EasRoleService easRoleService;

    @RequestMapping("/index")
    public String index(ModelMap modelMap) throws Exception {
        modelMap.put("roleList",easRoleService.getAll());
        return "system/user/index";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Map<String,Object> list(@RequestParam(defaultValue = "1") Integer page,
                                   @RequestParam(defaultValue = "10") Integer limit,
                                   EasUser condition, String roleIds) throws Exception{
        Map<String,Object> map = new HashMap<>();

        //将传递过来的角色编号封装到角色集合中
        if(roleIds != null && !roleIds.trim().equals("")){
            String[] array = roleIds.split(",");
            List<EasRole> roles = new ArrayList<>();
            for (int i = 0; i < array.length; i++) {
                EasRole role = new EasRole();
                role.setId(Integer.parseInt(array[i]));
                roles.add(role);
            }
            condition.setRoles(roles);
        }

//        Page<EasUser> pager = PageHelper.startPage(page,limit);
        int count = easUserService.getCount();
        PageUtil pageUtil = new  PageUtil(page,limit);
        List<EasUser> list = easUserMapper.getList(condition,pageUtil);

//        map.put("count",pager.getTotal());
        map.put("count",count);
        map.put("data",list);
        map.put("code",0);
        map.put("msg","");

        return map;
    }

    @RequestMapping("/form")
    public String form(ModelMap modelMap) throws Exception {
        modelMap.put("roleList",easRoleService.getAll());
        return "system/user/form";
    }

    @RequestMapping("/basicForm")
    public String basicForm(ModelMap modelMap) throws Exception {
        modelMap.put("classList",easClassService.getAll());
        return "system/user/form2";
    }

    @RequestMapping("/batchDelete")
    @ResponseBody
    @RequiresPermissions("user:delete")
    public Map<String, Object> batchDelete(Integer[] ids) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        easUserMapper.batchDelete(ids);
        map.put("result",true);
        return map;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(EasUser user,@RequestParam(defaultValue = "7") String roleIds) throws Exception{
        Map<String,Object> map = new HashMap<>();

        String[] array = roleIds.split(",");
        Integer[] ids = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            ids[i] = Integer.parseInt(array[i]);
        }

        //对密码进行加密
        Md5Hash md5Hash = new Md5Hash(user.getPassword(),user.getSalt());
        user.setPassword(md5Hash.toString());

//        List<EasUser> list = easUserMapper.findUserName(user.getUsername());

        List<EasUser> list = easUserService.findUserName(user.getUsername());
        if (list.size() != 0){
            map.put("msg","用户名已存在");
            map.put("result",false);
        }else{
            if(user.getLocked() == null){
                user.setLocked("1");
                easUserService.addUser(user);
                easUserService.addUserRole(user,ids);

                //根据用户id查询eas_user_role表中的角色id
                int roleid = easUserService.findRoleIdByUserId(user.getId());
                //roleid = 2 为学生 3为教师 将用户名在学生表/教师表进行添加
                if(roleid == 2){
                    easStudentService.addUsername(user.getUsername());
                }else if(roleid == 3){
                    easTeacherService.addUsername(user.getUsername());
                }

            }else {
                easUserService.addUser(user);
                easUserService.addUserRole(user,ids);

                //根据用户id查询eas_user_role表中的角色id
                int roleid = easUserService.findRoleIdByUserId(user.getId());
                //roleid = 2 为学生 3为教师 将用户名在学生表/教师表进行添加
                if(roleid == 2){
                    easStudentService.addUsername(user.getUsername());
                }else if(roleid == 3){
                    easTeacherService.addUsername(user.getUsername());
                }
            }

            map.put("result",true);
        }
        return map;
    }

    @RequestMapping(value = "/view")
    @ResponseBody
    public EasUser view(Integer id) throws Exception {
        EasUser easUser =easUserMapper.get(id);
        //将密码解密 放入对象中
//        System.out.println("解密一次后密码为:"+convertMD5(easUser.getPassword()));
        return easUser;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> edit(EasUser user, String roleIds) throws Exception{
        Map<String, Object> map = new HashMap<>();

        String[] array = roleIds.split(",");
        Integer[] ids= new Integer[array.length];
        for (int i=0 ;i < array.length; i++){
            ids[i] = Integer.parseInt(array[i]);
        }
        //获取原先未修改的对象
        EasUser easUser = easUserMapper.findUserById(user.getId());

        if (easUser.getPassword().equals(user.getPassword())) {
//            EasUser easUser = easUserMapper.findUserById(user.getId());
//            System.out.println("我是easUser密码"+easUser.getPassword());
//            System.out.println("我是user密码"+user.getPassword());
            user.setPassword(user.getPassword());
        }else{
            Md5Hash md5Hash = new Md5Hash(user.getPassword(),user.getSalt());
            user.setPassword(md5Hash.toString());
        }

        easUserService.updateUser(user);
        easUserService.deleteUserRole(user.getId());
        easUserService.addUserRole(user,ids);

        //根据用户id获取eas_user_role表中的数据行数
        int roleCount  = easUserService.getRoleCountByUid(user.getId());
        //判断用户是否只有一个角色
        if(roleCount == 1){
            //根据用户id查询eas_user_role表中的角色id
            int roleid = easUserService.findRoleIdByUserId(user.getId());
            //roleid = 2 为学生 3为教师 将用户名在学生表/教师表进行添加
            List list1 = easStudentService.findListByUsername(user.getUsername());
            List list2 = easTeacherService.findListByUsername(user.getUsername());

            if(roleid == 2){
                if(list1.size() == 0){
                    if(list2.size() > 0){
                        easTeacherService.deleteTeacher(user.getUsername());
                    }
                    easStudentService.addUsername(user.getUsername());
                }
            }else if(roleid == 3){
                if(list2.size() == 0){
                    if(list1.size() > 0){
                        easStudentService.deleteStudent(user.getUsername());
                    }
                    easTeacherService.addUsername(user.getUsername());
                }
            }
        }

//        //根据用户id查询eas_user_role表中的角色id
//        int roleid = easUserService.findRoleIdByUserId(user.getId());
//        //roleid = 2 为学生 3为教师 将用户名在学生表/教师表进行添加
//        List list1 = easStudentService.findListByUsername(user.getUsername());
//        List list2 = easTeacherService.findListByUsername(user.getUsername());
//
//        if(roleid == 2){
//            if(list1.size() == 0){
//                if(list2.size() > 0){
//                    easTeacherService.deleteTeacher(user.getUsername());
//                }
//                easStudentService.addUsername(user.getUsername());
//            }
//        }else if(roleid == 3){
//            if(list2.size() == 0){
//                if(list1.size() > 0){
//                    easStudentService.deleteStudent(user.getUsername());
//                }
//                easTeacherService.addUsername(user.getUsername());
//            }
//        }

        map.put("result",true);
        return map;
    }

    @RequestMapping("/passwordRestIndex")
    public String passwordRestIndex(){
        return "system/user/changePwd";
    }

    @RequestMapping(value = "/passwordRest",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> passwordRest(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();

        //获取页面输入的新旧密码
        String oldPassword = request.getParameter("oldPassword");
        String newPassword1 = request.getParameter("newPassword1");
        String newPassword2 = request.getParameter("newPassword2");

        String regex = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,20}$";

        boolean matches = newPassword1.matches(regex);

        //获取用户信息
        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象

        //将页面输入的密码进行加密 存入oldMd5Hash
        Md5Hash oldMd5Hash = new Md5Hash(oldPassword, easUser.getSalt());
        Md5Hash newMd5Hash = new Md5Hash(newPassword1, easUser.getSalt());

        //进行密码的判断
        if(oldPassword.length() <= 0 || newPassword1.length() <= 0 || newPassword2.length() <= 0){
//        if(oldPassword.equals("") || newPassword1.equals("") || newPassword2.equals("")){
            map.put("code",4);
            map.put("msg","密码不能为空");
        }else if(!easUser.getPassword().equals(oldMd5Hash.toString())){
            map.put("code",2);
            map.put("msg","输入的旧密码不正确");
        }else if(!newPassword1.equals(newPassword2)){
            map.put("code",1);
            map.put("msg","输入的新密码不一致");
        }else if(!matches){
            map.put("code",3);
            map.put("msg","密码必须包含字母、数字且长度为6-20位");
        }
//        else if(newPassword1.length() < 6 || newPassword1.length() >16){
//            map.put("code",3);
//            map.put("msg","密码长度为6～16位");
//        }
        else if(easUser.getPassword().equals(oldMd5Hash.toString())){
            easUser.setPassword(newMd5Hash.toString());
            easUserService.updateUser(easUser);
            map.put("code",0);
            map.put("msg","修改成功");
        }

        return map;
    }

    @RequestMapping("/basicInformationIndex")
    public String basicInformationIndex(Model model) throws Exception {
        //获取身份信息 判断是哪个角色
        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
        Integer roleid = easUserService.findRoleIdByUserId(easUser.getId());
        String rolename = easRoleService.findRoleNameByRoleId(roleid);
//        System.out.println("角色id为:"+roleid);
//        System.out.println("角色名称为:"+rolename);
        if(roleid == 1000 || !(rolename.length() > 0)){
            model.addAttribute("code",1);
            model.addAttribute("msg","该用户还没有个人信息,请稍后再来查看和修改个人信息！");

        }else if(rolename.equals(Constants.STUDENT)){
            List<EasStudent> list = easStudentService.findListByUsername(easUser.getUsername());
//            System.out.println("我是学生信息:"+list);
            model.addAttribute("data",list);
            model.addAttribute("code",2);
            model.addAttribute("msg","学生信息成功");

        }else if(rolename.equals(Constants.TEACHER)){
            List<EasTeacher> list = easTeacherService.findListByUsername(easUser.getUsername());
//            System.out.println("我是教师信息:"+list.size());
            model.addAttribute("data",list);
            model.addAttribute("code",3);
            model.addAttribute("msg","教师信息成功");
        }else{
            model.addAttribute("code",4);
            model.addAttribute("msg","管理员还没有个人信息，请静候功能开放！");
        }

        return "system/user/basicInformation";
    }

    /**
     * 获取修改个人资料时用户已有的信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getBasicInformation")
    @ResponseBody
    public Object getBasicInformation() throws Exception {
        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
        Integer roleid = easUserService.findRoleIdByUserId(easUser.getId());
        String rolename = easRoleService.findRoleNameByRoleId(roleid);
        if(roleid == 1000 || !(rolename.length() > 0)){
            System.out.println("我怎么变成null了！");
            return null;
        }else if(rolename.equals(Constants.STUDENT)){
            EasStudent easStudent = easStudentService.getStudentByUsername(easUser.getUsername());
            return easStudent;
        }else if(rolename.equals(Constants.TEACHER)){
            EasTeacher easTeacher = easTeacherService.getTeacherByUsername(easUser.getUsername());
            return easTeacher;
        }else{
            System.out.println("哪里出错了！");
            return null;
        }

    }

    @RequestMapping(value = "/modifyInformation",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modifyInformation(EasStudent easStudent,EasTeacher easTeacher,Integer classes) throws Exception{
        Map<String, Object> map = new HashMap<>();
        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
        Integer roleid = easUserService.findRoleIdByUserId(easUser.getId());
        String rolename = easRoleService.findRoleNameByRoleId(roleid);
        if(roleid == 1000 || !(rolename.length() > 0)){
            System.out.println("我怎么变成null了！");
            map.put("result",false);
            return map;
        }else if(rolename.equals(Constants.STUDENT)){
            //名称不符合必须重新set课程id
            easStudent.setClass_id(classes);
            easStudentService.updateStudent(easStudent);

            map.put("result",true);
            return map;
        }else if(rolename.equals(Constants.TEACHER)){
            easTeacherService.updateTeacher(easTeacher);

            map.put("result",true);
            return map;
        }else{
            /**
             * 可以扩充。。。
             */
            System.out.println("哪里出错了！");
            map.put("result",false);
            return map;
        }
    }


//    //url : /kan/5.html
//    @RequestMapping("/kan/{id}.shtml")
//    @ResponseBody
//    public EasUser kan(@PathVariable Integer id) throws Exception {
//        return easUserMapper.get(id);
//    }


}
