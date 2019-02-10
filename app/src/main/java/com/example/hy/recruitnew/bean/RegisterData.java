package com.example.hy.recruitnew.bean;

import cn.bmob.v3.BmobObject;

/**
 * 报名表
 * Created by 陈健宇 at 2018/11/9
 */
public class RegisterData extends BmobObject {

    private String name;
    private String schoolNumber;
    private String sex;
    private String academy;
    private String classes;
    private String busines;
    private String phone;
    private String qq;
    private String email;
    private String direction;
    private String spectiality;
    private String selfIntroduction;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolNumber() {
        return schoolNumber;
    }

    public void setSchoolNumber(String schoolNumber) {
        this.schoolNumber = schoolNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getBusines() {
        return busines;
    }

    public void setBusines(String busines) {
        this.busines = busines;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getSpectiality() {
        return spectiality;
    }

    public void setSpectiality(String spectiality) {
        this.spectiality = spectiality;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

}
