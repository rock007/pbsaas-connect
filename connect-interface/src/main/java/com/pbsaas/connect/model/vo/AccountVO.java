package com.pbsaas.connect.model.vo;

import java.io.Serializable;

public class AccountVO implements Serializable {

    private  String uid;

    private  String userName;

    private  String mobile;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
