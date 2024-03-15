package com.jubilantz.entity;

import java.io.Serializable;

/**
 * @Author JubilantZ
 * 班级表
 * @Date: 2021/4/14 10:59
 */
public class EasClass implements Serializable {
    private Integer id;
    private String classes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EasClass easClass = (EasClass) o;

        if (id != null ? !id.equals(easClass.id) : easClass.id != null) return false;
        if (classes != null ? !classes.equals(easClass.classes) : easClass.classes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (classes != null ? classes.hashCode() : 0);
        return result;
    }
}
