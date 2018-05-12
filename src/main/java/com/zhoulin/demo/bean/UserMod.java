package com.zhoulin.demo.bean;

import java.io.Serializable;

/**
 * 用户模型
 */
public class UserMod implements Serializable{

    //模型id
    private Integer modId;

    //用户id
    private Integer userId;

    //互联网
    private int internet;

    //体育
    private int sports;

    //健康
    private int health;

    //军事
    private int military;

    //教育
    private int education;

    //文化
    private int culture;

    //旅游
    private int travel;

    //汽车
    private int car;

    //生活
    private int life;

    //财经
    private int business;

    public UserMod() {
    }

    public UserMod(Integer userId, int internet, int sports, int health, int military, int education, int culture, int travel, int car, int life, int business) {
        this.userId = userId;
        this.internet = internet;
        this.sports = sports;
        this.health = health;
        this.military = military;
        this.education = education;
        this.culture = culture;
        this.travel = travel;
        this.car = car;
        this.life = life;
        this.business = business;
    }

    public UserMod(Integer modId, Integer userId, int internet, int sports, int health, int military, int education, int culture, int travel, int car, int life, int business) {
        this.modId = modId;
        this.userId = userId;
        this.internet = internet;
        this.sports = sports;
        this.health = health;
        this.military = military;
        this.education = education;
        this.culture = culture;
        this.travel = travel;
        this.car = car;
        this.life = life;
        this.business = business;
    }

    public Integer getModId() {
        return modId;
    }

    public void setModId(Integer modId) {
        this.modId = modId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getInternet() {
        return internet;
    }

    public void setInternet(int internet) {
        this.internet = internet;
    }

    public int getSports() {
        return sports;
    }

    public void setSports(int sports) {
        this.sports = sports;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMilitary() {
        return military;
    }

    public void setMilitary(int military) {
        this.military = military;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getCulture() {
        return culture;
    }

    public void setCulture(int culture) {
        this.culture = culture;
    }

    public int getTravel() {
        return travel;
    }

    public void setTravel(int travel) {
        this.travel = travel;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getBusiness() {
        return business;
    }

    public void setBusiness(int business) {
        this.business = business;
    }
}
