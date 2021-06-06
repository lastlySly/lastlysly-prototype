package com.lastlysly.web.view;

import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-06-06 15:18
 **/
public class CustomRequestContext {
    private String userId;
    private String token;
    private List<String> ownerIds;
    private String adminCode;

    public CustomRequestContext(String userId, String token, List<String> ownerIds, String adminCode) {
        this.userId = userId;
        this.token = token;
        this.ownerIds = ownerIds;
        this.adminCode = adminCode;
    }

    public CustomRequestContext() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getOwnerIds() {
        return ownerIds;
    }

    public void setOwnerIds(List<String> ownerIds) {
        this.ownerIds = ownerIds;
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }
}
