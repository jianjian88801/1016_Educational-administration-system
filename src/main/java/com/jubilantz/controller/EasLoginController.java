package com.jubilantz.controller;

import com.jubilantz.entity.EasPermission;
import com.jubilantz.entity.EasUser;
import com.jubilantz.mappers.EasPermissionMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author JubilantZ
 * @Date: 2021/4/8 15:40
 */
@Controller
@RequestMapping("/easLogin")
public class EasLoginController {
    @Autowired
    private EasPermissionMapper easPermissionMapper;

    @RequestMapping("/main")
    public String main() throws Exception{
        return "main";
    }
//    @RequestMapping("/home")
//    public String home() throws Exception{
//        return "system/home/homePage";
//    }

    @RequestMapping("/success")
    @ResponseBody
    public Map<String,Object> success(HttpSession session) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("code",0);
        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();
        session.setAttribute(Constants.LOGIN_USER,easUser);

        List<EasPermission> list = easPermissionMapper.getPersByUserId(easUser.getId());
        session.setAttribute(Constants.LOGIN_USER_PERS,list);

        return map;
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login() throws Exception{
        return "login";
    }

    /**
     * post方式的login方式什么时候调用？
     * 身份认证失败的时候会自动调用
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request) throws Exception{
        Map<String,Object> map = new HashMap<>();
//        System.out.println("认证失败了吧！来我这了吧");
        String exceptionName = request.getAttribute("shiroLoginFailure").toString();

        if (exceptionName.equals(UnknownAccountException.class.getName())){
            map.put("code",1);
            map.put("msg","用户名不正确");
            return map;
        }else if(exceptionName.equals(IncorrectCredentialsException.class.getName())){
            map.put("code",2);
            map.put("msg","密码不正确");
            return map;
        }else if (exceptionName.equals("randomCodeError")){
            map.put("code",3);
            map.put("msg","验证码不正确");
            return map;
        }
        return null;
    }


}
