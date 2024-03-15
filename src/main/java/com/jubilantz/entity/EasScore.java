package com.jubilantz.entity;

import java.io.Serializable;

/**
 * @Author JubilantZ
 * @Date: 2021/4/23 9:17
 */
public class EasScore implements Serializable {
    private Integer id;
    private Integer score;
    private String result;
    private Integer sId;
    private Integer cId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EasScore easScore = (EasScore) o;

        if (id != null ? !id.equals(easScore.id) : easScore.id != null) return false;
        if (score != null ? !score.equals(easScore.score) : easScore.score != null) return false;
        if (result != null ? !result.equals(easScore.result) : easScore.result != null) return false;
        if (sId != null ? !sId.equals(easScore.sId) : easScore.sId != null) return false;
        if (cId != null ? !cId.equals(easScore.cId) : easScore.cId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = id != null ? id.hashCode() : 0;
        result1 = 31 * result1 + (score != null ? score.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (sId != null ? sId.hashCode() : 0);
        result1 = 31 * result1 + (cId != null ? cId.hashCode() : 0);
        return result1;
    }
}
