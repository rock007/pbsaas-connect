package com.pbsaas.connect.model.dto;

import java.io.Serializable;

public class RegistAccountDTO  implements Serializable {

    private  String userName;

    private String password;

    private String mobile;

    private String vaildCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVaildCode() {
        return vaildCode;
    }

    public void setVaildCode(String vaildCode) {
        this.vaildCode = vaildCode;
    }
}
