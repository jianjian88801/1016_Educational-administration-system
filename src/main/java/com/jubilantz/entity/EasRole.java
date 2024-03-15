package com.jubilantz.entity;

import java.io.Serializable;

/**
 * @Author JubilantZ
 * 角色表
 * @Date: 2021/4/7 9:48
 */
public class EasRole implements Serializable {
    private Integer id;
    private String name;
    private Integer available;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EasRole easRole = (EasRole) o;

        if (id != null ? !id.equals(easRole.id) : easRole.id != null) return false;
        if (name != null ? !name.equals(easRole.name) : easRole.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
