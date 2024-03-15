package com.jubilantz.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author JubilantZ
 * 课程表
 * @Date: 2021/4/23 9:11
 */
//返回如果是对象，对象需要实现序列化
public class EasCourse implements Serializable {
    private Integer id;
    private Date startDate;
    private Date endDate;
    private Short classHour;
    private String testMode;
    private Integer studentNum;
    private Integer choiceNum;
    private Integer complete;

    private Integer tId;
    private Integer baseCourseId;

    private String teacherName;
    private String courseName;

    private String result;
    private Integer score;


    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Short getClassHour() {
        return classHour;
    }

    public void setClassHour(Short classHour) {
        this.classHour = classHour;
    }

    public String getTestMode() {
        return testMode;
    }

    public void setTestMode(String testMode) {
        this.testMode = testMode;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getChoiceNum() {
        return choiceNum;
    }

    public void setChoiceNum(Integer choiceNum) {
        this.choiceNum = choiceNum;
    }

    public Integer getComplete() {
        return complete;
    }

    public void setComplete(Integer complete) {
        this.complete = complete;
    }

    public Integer gettId() {
        return tId;
    }

    public void settId(Integer tId) {
        this.tId = tId;
    }

    public Integer getBaseCourseId() {
        return baseCourseId;
    }

    public void setBaseCourseId(Integer baseCourseId) {
        this.baseCourseId = baseCourseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EasCourse easCourse = (EasCourse) o;

        if (id != null ? !id.equals(easCourse.id) : easCourse.id != null) return false;
        if (startDate != null ? !startDate.equals(easCourse.startDate) : easCourse.startDate != null) return false;
        if (endDate != null ? !endDate.equals(easCourse.endDate) : easCourse.endDate != null) return false;
        if (classHour != null ? !classHour.equals(easCourse.classHour) : easCourse.classHour != null) return false;
        if (testMode != null ? !testMode.equals(easCourse.testMode) : easCourse.testMode != null) return false;
        if (studentNum != null ? !studentNum.equals(easCourse.studentNum) : easCourse.studentNum != null) return false;
        if (choiceNum != null ? !choiceNum.equals(easCourse.choiceNum) : easCourse.choiceNum != null) return false;
        if (complete != null ? !complete.equals(easCourse.complete) : easCourse.complete != null) return false;
        if (tId != null ? !tId.equals(easCourse.tId) : easCourse.tId != null) return false;
        if (baseCourseId != null ? !baseCourseId.equals(easCourse.baseCourseId) : easCourse.baseCourseId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (classHour != null ? classHour.hashCode() : 0);
        result = 31 * result + (testMode != null ? testMode.hashCode() : 0);
        result = 31 * result + (studentNum != null ? studentNum.hashCode() : 0);
        result = 31 * result + (choiceNum != null ? choiceNum.hashCode() : 0);
        result = 31 * result + (complete != null ? complete.hashCode() : 0);
        result = 31 * result + (tId != null ? tId.hashCode() : 0);
        result = 31 * result + (baseCourseId != null ? baseCourseId.hashCode() : 0);
        return result;
    }
}
