package io.github.forezp.distributedlimitcore.entity;

/**
 * Created by forezp on 2019/5/1.
 */
public class LimitCollectData {

    private String url;
    private int access;
    private int refuse;

    @Override
    public String toString() {
        return "LimitCollectData{" +
                "url='" + url + '\'' +
                ", access=" + access +
                ", refuse=" + refuse +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public int getRefuse() {
        return refuse;
    }

    public void setRefuse(int refuse) {
        this.refuse = refuse;
    }
}
