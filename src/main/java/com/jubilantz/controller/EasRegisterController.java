package com.jubilantz.controller;

import com.jubilantz.entity.EasUser;
import com.jubilantz.services.EasRegisterService;
import com.jubilantz.services.EasUserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author JubilantZ
 * @Date: 2021/4/24 17:06
 */
@RequestMapping("/easRegister")
@Controller
public class EasRegisterController {
    @Autowired
    private EasRegisterService easRegisterService;

    @Autowired
    private EasUserService easUserService;

    @RequestMapping("/registerForm")
    public String registerForm(){
        return "registerForm";
    }

    @RequestMapping(value = "/registerUser",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> registerUser(HttpServletRequest request) throws Exception{
        Map<String, Object> map = new HashMap<>();
        //获取页面输入的新旧密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        String regex = "^(?!([a-zA-Z]+|\\d+)$)[a-zA-Z\\d]{6,20}$";

        boolean matches = password.matches(regex);

//        System.out.println("页面输入的用户名为:"+username);
//        System.out.println("页面输入的密码为:"+password);

        List<EasUser> list = easUserService.findUserName(username);
        if(list.size() > 0){
            map.put("code",1);
            map.put("msg","用户名已存在，请重新输入");
        }else if(password.length() <= 0 || password2.length() <= 0 || username.length() <= 0){
            map.put("code",1);
            map.put("msg","用户名密码不能为空，请重新输入");
        }else if(!password.equals(password2)){
            map.put("code",1);
            map.put("msg","两次输入密码不一致，请重新输入");
        }else if(!matches){
            map.put("code",1);
            map.put("msg","密码必须包含字母、数字且长度为6-20位");
        }else if(matches){
            //由密码和用户名组成MD5加密  用户名为盐值
//            Md5Hash Md5Hash = new Md5Hash(password, username);
//            System.out.println("我是MD5Hash"+Md5Hash);
            String algorithmName = "MD5";//加密算法
            Object source = password;//要加密的密码
            Object salt = username;//盐值，一般都是用户名或者userid，要保证唯一
            int hashIterations = 1;//加密次数

            SimpleHash simpleHash = new SimpleHash(algorithmName,source,salt,hashIterations);
//            System.out.println("我是SimpleHash:"+simpleHash);

            EasUser easUser = new EasUser();
            easUser.setUsername(username);
            easUser.setPassword(simpleHash.toString());
            easUser.setSalt(username);
            easUser.setLocked("0");

            easUserService.addUser(easUser);

            map.put("code",0);
        }else{
            map.put("code",1);
            map.put("msg","注册失败，请联系管理员邮箱1260298750@qq.com!!!");
        }

        return map;
    }


}
