package com.jubilantz.realms;

import com.jubilantz.entity.EasPermission;
import com.jubilantz.entity.EasUser;
import com.jubilantz.mappers.EasPermissionMapper;
import com.jubilantz.mappers.EasUserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private EasUserMapper easUserMapper;

    @Autowired
    private EasPermissionMapper easPermissionMapper;

    public void setEasUserMapper(EasUserMapper easUserMapper) {
        this.easUserMapper = easUserMapper; }

    public void setEasPermissionMapper(EasPermissionMapper easPermissionMapper) {
        this.easPermissionMapper = easPermissionMapper;
    }

    //用户授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        EasUser easUser = (EasUser)principalCollection.getPrimaryPrincipal();

        //根据用户id获取相应权限
        List<EasPermission> list = easPermissionMapper.getPersByUserId(easUser.getId());
        Set<String> set = new HashSet<>();
        for (EasPermission per :
                list) {
            set.add(per.getPercode());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;
    }

    /**
     * 用户认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取subject（主体），其实就是登陆用户 的 身份信息，也就是用户名、手机号、邮箱
        String username = (String) token.getPrincipal();
        EasUser easUser = easUserMapper.getByUserName(username);

        //如果身份信息不正确，返回null，shiro会自动抛出UnkonwAccountExcpetion
        if (easUser == null){
            return null;
        }


        AuthenticationInfo aInfo = new SimpleAuthenticationInfo(easUser,easUser.getPassword(),ByteSource.Util.bytes(easUser.getSalt()),"user");
//        return new SimpleAuthenticationInfo(easUser,easUser.getPassword(),ByteSource.Util.bytes(easUser.getSalt()),"user");

        return aInfo;
    }
}
