package com.jubilantz.entity;

import java.io.Serializable;

/**
 * @Author JubilantZ
 * 基本课程表
 * @Date: 2021/4/13 16:30
 */
public class EasBaseCourse implements Serializable {
    private Integer id;
    private String coursename;
    private String synopsis;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EasBaseCourse that = (EasBaseCourse) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (coursename != null ? !coursename.equals(that.coursename) : that.coursename != null) return false;
        if (synopsis != null ? !synopsis.equals(that.synopsis) : that.synopsis != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (coursename != null ? coursename.hashCode() : 0);
        result = 31 * result + (synopsis != null ? synopsis.hashCode() : 0);
        return result;
    }
}
