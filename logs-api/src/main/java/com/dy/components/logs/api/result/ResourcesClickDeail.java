package com.dy.components.logs.api.result;

import java.util.Date;

public class ResourcesClickDeail  {
    String userId;
    String params;
    String url;
    Date startTime;
    long castTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getCastTime() {
        return castTime;
    }

    public void setCastTime(long castTime) {
        this.castTime = castTime;
    }
}
