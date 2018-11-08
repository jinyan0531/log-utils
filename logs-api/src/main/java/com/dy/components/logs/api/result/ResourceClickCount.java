package com.dy.components.logs.api.result;

public class ResourceClickCount {
    String url;

    long count;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ResourceClickCount{" +
                "url='" + url + '\'' +
                ", count=" + count +
                '}';
    }
}
