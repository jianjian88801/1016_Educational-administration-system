package com.jubilantz.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Author JubilantZ
 * 权限表
 * @Date: 2021/4/7 9:47
 */
public class EasPermission implements Serializable {
    private Integer id;
    private String text;
    private String type;
    private String url;
    private String percode;
    private Integer parentid;
    private Integer sortstring;
    private Integer available;

    private boolean open = true;

    private List<EasPermission> children; //子权限集合

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public List<EasPermission> getChildren() {
        return children;
    }

    public void setChildren(List<EasPermission> children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPercode() {
        return percode;
    }

    public void setPercode(String percode) {
        this.percode = percode;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public Integer getSortstring() {
        return sortstring;
    }

    public void setSortstring(Integer sortstring) {
        this.sortstring = sortstring;
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

        EasPermission that = (EasPermission) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (percode != null ? !percode.equals(that.percode) : that.percode != null) return false;
        if (parentid != null ? !parentid.equals(that.parentid) : that.parentid != null) return false;
        if (sortstring != null ? !sortstring.equals(that.sortstring) : that.sortstring != null) return false;
        if (available != null ? !available.equals(that.available) : that.available != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (percode != null ? percode.hashCode() : 0);
        result = 31 * result + (parentid != null ? parentid.hashCode() : 0);
        result = 31 * result + (sortstring != null ? sortstring.hashCode() : 0);
        result = 31 * result + (available != null ? available.hashCode() : 0);
        return result;
    }
}
