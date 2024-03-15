package com.jubilantz.test;

import com.jubilantz.entity.EasPermission;
import com.jubilantz.mappers.EasPermissionMapper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/7 14:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TestEasUserDao {
    @Autowired
    private EasPermissionMapper easPermissionMapper;

    @Test
    public void testGetPermission(){
        List<EasPermission> list = easPermissionMapper.getParentPers();
        for (EasPermission first :list) {
            System.out.println(first.getText());
            for (EasPermission second :first.getChildren()) {
                System.out.println("\t"+second.getText());
                for (EasPermission third :
                        second.getChildren()) {
                    System.out.println("\t\t"+third.getText());
                }
            }
        }
    }

    /**
     * 加密
     */
    @Test
    public void MD5(){
        String password = "123456";//明码
        String algorithmName = "MD5";//加密算法
        Object source = password;//要加密的密码

        Object salt = "admin";//盐值，一般都是用户名或者userid，要保证唯一
        int hashIterations = 1;//加密次数

        SimpleHash simpleHash = new SimpleHash(algorithmName,source,salt,hashIterations);
        System.out.println(simpleHash);

    }
}
