package com.jubilantz.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @Author JubilantZ
 * @Date: 2021/4/7 15:27
 */
public class EasUser implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String salt;
    private String locked;

    private Date regDate = new Date();

    private List<EasRole> roles; //用户拥有的角色列表

    private String roleString; //角色字符串

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public List<EasRole> getRoles() {
        return roles;
    }

    public void setRoles(List<EasRole> roles) {
        this.roles = roles;
    }

    public String getRoleString() {
        if(this.roles == null || this.roles.size() == 0){
            this.roleString = "-";
            return roleString;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<EasRole> it = this.roles.iterator();
        for(;;){
            sb.append(it.next().getName());
            if(!it.hasNext()){
                this.roleString = sb.toString();
                break;
            }
            sb.append(",");

        }
        return roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EasUser easUser = (EasUser) o;

        if (id != null ? !id.equals(easUser.id) : easUser.id != null) return false;
        if (username != null ? !username.equals(easUser.username) : easUser.username != null) return false;
        if (password != null ? !password.equals(easUser.password) : easUser.password != null) return false;
        if (salt != null ? !salt.equals(easUser.salt) : easUser.salt != null) return false;
        if (locked != null ? !locked.equals(easUser.locked) : easUser.locked != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (locked != null ? locked.hashCode() : 0);
        return result;
    }
}
