package com.jubilantz.controller;

import com.jubilantz.entity.EasNotice;
import com.jubilantz.entity.EasUser;
import com.jubilantz.services.EasNoticeService;
import com.jubilantz.services.EasUserService;
import com.jubilantz.utils.PageUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author JubilantZ
 * @Date: 2021/4/28 20:25
 */
@RequestMapping("/main")
@Controller
public class EasMainController {
    @Autowired
    private EasNoticeService easNoticeService;

    @Autowired
    private EasUserService easUserService;

    @RequestMapping("/homePage")
    public String homePage() throws Exception{
        return "system/home/homePage";
    }

//    @RequestMapping(value="/getNotice",method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String,Object> getNotice(@RequestParam(defaultValue = "1") Integer page,
//                                        @RequestParam(defaultValue = "2") Integer limit,
//                                        EasNotice easNotice) throws Exception {
//        Map<String,Object> map = new HashMap<>();
//
////        System.out.println("模糊查询的内容为:"+easNotice.getContent());
//
//        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
//
//        //判断用户有没有 角色 有就返回角色id 没有就返回1000
//
//        Integer roleId = easUserService.findRoleIdByUserId(easUser.getId());
//
//
//        String strRoleId =roleId +"";
////        System.out.println("roleId:"+roleId);
////        System.out.println("strRoleId:"+strRoleId);
//        PageUtil pageUtil = new  PageUtil(page,limit);
//
//        //没有角色
//        if(roleId == null || !(strRoleId.length() >0 || roleId == 2)){//全体可见的部分公告，没要求
//            //type = 1 全员可见 type = 2 教师可见  type = 3 草稿  管理员可见
//            int type = 1;
//            int count = easNoticeService.getCountByTypeAndEasNotice(type,easNotice);
//            pageUtil.setTotal(count);
//            pageUtil.setCount(limit);
//            int totalPage = pageUtil.getTotalPage();
////            System.out.println("总页数为"+totalPage);
//
//            List<EasNotice> list = easNoticeService.getNoticeListByTypeAndEasNotice(type,easNotice,pageUtil);
//
//            map.put("totalPage",totalPage);
//            map.put("count",count);
//            map.put("data",list);
//            map.put("code",0);
//            map.put("msg","");
//        }else if(roleId == 3){//增加教师公告可见
//            int type = 2;
//            int count = easNoticeService.getCountByTypeAndEasNotice(type,easNotice);
//            List<EasNotice> list = easNoticeService.getNoticeListByTypeAndEasNotice(type,easNotice,pageUtil);
//            pageUtil.setTotal(count);
//            pageUtil.setCount(limit);
//            int totalPage = pageUtil.getTotalPage();
////            System.out.println("总页数为"+totalPage);
//
//            map.put("totalPage",totalPage);
//            map.put("count",count);
//            map.put("data",list);
//            map.put("code",0);
//            map.put("msg","");
//        }else{//管理员可见全部
//            int type = 3;
//            int count = easNoticeService.getCountByTypeAndEasNotice(type,easNotice);
//            List<EasNotice> list = easNoticeService.getNoticeListByTypeAndEasNotice(type,easNotice,pageUtil);
//
//            pageUtil.setTotal(count);
//            pageUtil.setCount(limit);
//            int totalPage = pageUtil.getTotalPage();
//
//            map.put("totalPage",totalPage);
//
//            map.put("count",count);
//            map.put("data",list);
//            map.put("code",0);
//            map.put("msg","");
//        }
//
//        return map;
//    }

    @RequestMapping(value="/getNotice",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getNotice(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "2") Integer limit,
                                        EasNotice easNotice) throws Exception {
        Map<String,Object> map = new HashMap<>();
//        System.out.println("模糊查询的内容为:"+easNotice.getContent());
        EasUser easUser = (EasUser) SecurityUtils.getSubject().getPrincipal();//获取EasUser对象
        //判断用户有没有 角色 有就返回角色id 没有就返回1000

        List<Integer> rolelist = easUserService.findRoleIdByUserId2(easUser.getId());


        PageUtil pageUtil = new  PageUtil(page,limit);
        if(rolelist.size() >= 2){
            int type = 3;
            int count = easNoticeService.getCountByTypeAndEasNotice(type,easNotice);
            List<EasNotice> list = easNoticeService.getNoticeListByTypeAndEasNotice(type,easNotice,pageUtil);

            pageUtil.setTotal(count);
            pageUtil.setCount(limit);
            int totalPage = pageUtil.getTotalPage();

            map.put("totalPage",totalPage);

            map.put("count",count);
            map.put("data",list);
            map.put("code",0);
            map.put("msg","");
        }else {
            if(rolelist.size() == 0 || rolelist.get(0) == 2){
                //type = 1 全员可见 type = 2 教师可见  type = 3 草稿  管理员可见
                int type = 1;
                int count = easNoticeService.getCountByTypeAndEasNotice(type,easNotice);
                pageUtil.setTotal(count);
                pageUtil.setCount(limit);
                int totalPage = pageUtil.getTotalPage();
//            System.out.println("总页数为"+totalPage);

                List<EasNotice> list = easNoticeService.getNoticeListByTypeAndEasNotice(type,easNotice,pageUtil);

                map.put("totalPage",totalPage);
                map.put("count",count);
                map.put("data",list);
                map.put("code",0);
                map.put("msg","");
            }else if(rolelist.get(0) == 3) {
                int type = 2;
                int count = easNoticeService.getCountByTypeAndEasNotice(type,easNotice);
                List<EasNotice> list = easNoticeService.getNoticeListByTypeAndEasNotice(type,easNotice,pageUtil);
                pageUtil.setTotal(count);
                pageUtil.setCount(limit);
                int totalPage = pageUtil.getTotalPage();
//            System.out.println("总页数为"+totalPage);

                map.put("totalPage",totalPage);
                map.put("count",count);
                map.put("data",list);
                map.put("code",0);
                map.put("msg","");
            }else{
                int type = 3;
                int count = easNoticeService.getCountByTypeAndEasNotice(type,easNotice);
                List<EasNotice> list = easNoticeService.getNoticeListByTypeAndEasNotice(type,easNotice,pageUtil);

                pageUtil.setTotal(count);
                pageUtil.setCount(limit);
                int totalPage = pageUtil.getTotalPage();

                map.put("totalPage",totalPage);

                map.put("count",count);
                map.put("data",list);
                map.put("code",0);
                map.put("msg","");
            }
        }

        return map;
    }


    //点击查看具体通知
    @RequestMapping(value="/lookNotice")
    public ModelAndView look(Integer id){
        ModelAndView modelAndView = new ModelAndView();
//        System.out.println("我是通知id:"+id);

        List<EasNotice> list = easNoticeService.getNoticeById(id);
        modelAndView.addObject("noticeList",list);
        modelAndView.setViewName("system/notice/homeNotice");

        return modelAndView;
    }


}
