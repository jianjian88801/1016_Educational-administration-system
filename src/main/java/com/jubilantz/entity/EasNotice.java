package com.jubilantz.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author JubilantZ
 * 通知表
 * @Date: 2021/4/27 10:21
 */
public class EasNotice implements Serializable {
    private Integer id;
    private String title;
    private String author;
    private String content;
    private Integer type;
    private Date releasedate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(Date releasedate) {
        this.releasedate = releasedate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EasNotice easNotice = (EasNotice) o;

        if (id != null ? !id.equals(easNotice.id) : easNotice.id != null) return false;
        if (title != null ? !title.equals(easNotice.title) : easNotice.title != null) return false;
        if (author != null ? !author.equals(easNotice.author) : easNotice.author != null) return false;
        if (content != null ? !content.equals(easNotice.content) : easNotice.content != null) return false;
        if (type != null ? !type.equals(easNotice.type) : easNotice.type != null) return false;
        if (releasedate != null ? !releasedate.equals(easNotice.releasedate) : easNotice.releasedate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (releasedate != null ? releasedate.hashCode() : 0);
        return result;
    }
}
