package com.jubilantz.utils;

import com.jubilantz.controller.Constants;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //在这里进行验证码校验
//        System.out.println("进行验证码校验");
        //从session中获取正确验证码
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        //取出session中的验证码
        String validateCode = (String) session.getAttribute(Constants.VALIDATE_CODE);
//        System.out.println("我是正确验证码："+validateCode);
        //取出页面的验证码
        String randomCode = httpServletRequest.getParameter(Constants.RANDOM_CODE);
//        System.out.println("我是输入验证码："+randomCode);

        //比较
        if(randomCode != null && validateCode !=null && !randomCode.equals(validateCode)){
            //如果验证失败 将验证码错误信息通过shiroLoginFailure设这到request中
            httpServletRequest.setAttribute("shiroLoginFailure",Constants.CODE_ERROR);
            //拒绝访问 不再校验用户名密码 return true
            return true;
        }

        //认证通过调用父类的认证方法
        return super.onAccessDenied(request, response);
    }

    @Override
    protected void issueSuccessRedirect(ServletRequest request, ServletResponse response) throws Exception {
//        System.out.println("这是哪");
        WebUtils.issueRedirect(	request, response, getSuccessUrl(),null,true);
    }
}
