package com.rezaandreza.shop.Model.Database;

import java.util.ArrayList;
import java.util.Arrays;

import com.rezaandreza.shop.System.Model.Database.BaseModel;

/**
 * Created by sagor on 7/9/2018.
 */

public class Member extends BaseModel
{

    public ArrayList<String> __PRIMARYKEY = new ArrayList<String>(Arrays.asList("userID"));
    public ArrayList<String> __AUTOINCREMENT = new ArrayList<String>();
    public ArrayList<String> __UNIQUE = new ArrayList<String>();
    public ArrayList<String> __NONDBDATA = super.__NONDBDATA;
    private int userID;
    private String userNameBng;
    private String designation;
    private int designationID;
    private int areaId;
    private String phone1;
    private String phone2;
    private String officeNum;
    private String email;
    private String orgID;
    private int activeStat;
    private String lastUpdate;
    private String userNameEng;


    public int getDesignationID() {
        return designationID;
    }

    public void setDesignationID(int designationID) {
        this.designationID = designationID;
    }

    public void setUser_name_eng(String user_name_eng) {
        this.userNameEng = user_name_eng;
    }

    public String getUser_name_eng() {return userNameEng; }

    public void setUser_id(int user_id) {
        this.userID = user_id;
    }

    public void setUser_name(String user_name) {
        this.userNameBng = user_name;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setArea_id(int area_id) {
        this.areaId = area_id;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public void setOffice_num(String office_num) {
        this.officeNum = office_num;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOrg_id(String org_id) {
        this.orgID = org_id;
    }

    public void setActive_stat(int active_stat) {
        this.activeStat = active_stat;
    }

    public void setLast_update(String last_update) {
        this.lastUpdate = last_update;
    }

    public int getUser_id() {
        return userID;
    }

    public String getUser_name() {
        return userNameBng;
    }

    public String getDesignation() {
        return designation;
    }

    public int getArea_id() {
        return areaId;
    }

    public String getPhone1() {
        return phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public String getOffice_num() {
        return officeNum;
    }

    public String getEmail() {
        return email;
    }

    public String getOrg_id() {
        return orgID;
    }

    public int getActive_stat() {
        return activeStat;
    }

    public String getLast_update() {
        return lastUpdate;
    }
}
